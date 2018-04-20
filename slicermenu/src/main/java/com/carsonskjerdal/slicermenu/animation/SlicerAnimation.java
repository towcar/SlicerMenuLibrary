package com.carsonskjerdal.slicermenu.animation;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.ViewTreeObserver;

import com.carsonskjerdal.slicermenu.interfaces.SlicerListener;
import com.carsonskjerdal.slicermenu.util.ActionBarInterpolator;
import com.carsonskjerdal.slicermenu.util.SlicerInterpolator;


/**
 * Created by Dmytro Denysenko on 5/6/15.
 * Updated by Carson Skjerdal on 4/20/18.
 */
public class SlicerAnimation {
    private static final String ROTATION = "rotation";
    private static final float SLICER_CLOSED_ANGLE = -90f;
    private static final float SLICER_OPENED_ANGLE = 0f;
    private static final int DEFAULT_DURATION = 625;
    private static final float ACTION_BAR_ROTATION_ANGLE = 3f;

    private final View mSlicerView;
    private final long mDuration;
    private final ObjectAnimator mOpeningAnimation;
    private final ObjectAnimator mClosingAnimation;
    private final SlicerListener mListener;
    private final boolean mIsRightToLeftLayout;
    private final TimeInterpolator mInterpolator;
    private final View mActionBarView;
    private final long mDelay;

    private boolean isOpening;
    private boolean isClosing;

    private SlicerAnimation(GuillotineBuilder builder) {
        this.mActionBarView = builder.actionBarView;
        this.mListener = builder.slicerListener;
        this.mSlicerView = builder.slicerView;
        this.mDuration = builder.duration > 0 ? builder.duration : DEFAULT_DURATION;
        this.mDelay = builder.startDelay;
        this.mIsRightToLeftLayout = builder.isRightToLeftLayout;
        this.mInterpolator = builder.interpolator == null ? new SlicerInterpolator() : builder.interpolator;
        setUpOpeningView(builder.openingView);
        setUpClosingView(builder.closingView);
        this.mOpeningAnimation = buildOpeningAnimation();
        this.mClosingAnimation = buildClosingAnimation();
        if (builder.isClosedOnStart) {
            mSlicerView.setRotation(SLICER_CLOSED_ANGLE);
            mSlicerView.setVisibility(View.INVISIBLE);
        }
        //TODO handle right-to-left layouts
        //TODO handle landscape orientation
    }

    public void open() {
        if (!isOpening) {
            mOpeningAnimation.start();
        }
    }

    public void close() {
        if (!isClosing) {
            mClosingAnimation.start();
        }

    }

    private void setUpOpeningView(final View openingView) {
        if (mActionBarView != null) {
            mActionBarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mActionBarView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mActionBarView.setPivotX(calculatePivotX(openingView));
                    mActionBarView.setPivotY(calculatePivotY(openingView));
                }
            });
        }
        openingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });
    }

    private void setUpClosingView(final View closingView) {
        mSlicerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mSlicerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mSlicerView.setPivotX(calculatePivotX(closingView));
                mSlicerView.setPivotY(calculatePivotY(closingView));
            }
        });

        closingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
    }

    private ObjectAnimator buildOpeningAnimation() {
        ObjectAnimator rotationAnimator = initAnimator(ObjectAnimator.ofFloat(mSlicerView, ROTATION, SLICER_CLOSED_ANGLE, SLICER_OPENED_ANGLE));
        rotationAnimator.setInterpolator(mInterpolator);
        rotationAnimator.setDuration(mDuration);
        rotationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mSlicerView.setVisibility(View.VISIBLE);
                isOpening = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isOpening = false;
                if (mListener != null) {
                    mListener.onSlicerOpened();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return rotationAnimator;
    }

    private ObjectAnimator buildClosingAnimation() {
        ObjectAnimator rotationAnimator = initAnimator(ObjectAnimator.ofFloat(mSlicerView, ROTATION, SLICER_OPENED_ANGLE, SLICER_CLOSED_ANGLE));
        rotationAnimator.setDuration((long) (mDuration * SlicerInterpolator.ROTATION_TIME));
        rotationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isClosing = true;
                mSlicerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isClosing = false;
                mSlicerView.setVisibility(View.GONE);
                startActionBarAnimation();

                if (mListener != null) {
                    mListener.onSlicerClosed();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return rotationAnimator;
    }

    private void startActionBarAnimation() {
        ObjectAnimator actionBarAnimation = ObjectAnimator.ofFloat(mActionBarView, ROTATION, SLICER_OPENED_ANGLE, ACTION_BAR_ROTATION_ANGLE);
        actionBarAnimation.setDuration((long) (mDuration * (SlicerInterpolator.FIRST_BOUNCE_TIME + SlicerInterpolator.SECOND_BOUNCE_TIME)));
        actionBarAnimation.setInterpolator(new ActionBarInterpolator());
        actionBarAnimation.start();
    }

    private ObjectAnimator initAnimator(ObjectAnimator animator) {
        animator.setStartDelay(mDelay);
        return animator;
    }

    private float calculatePivotY(View burger) {
        return burger.getTop() + burger.getHeight() / 2;
    }

    private float calculatePivotX(View burger) {
        return burger.getLeft() + burger.getWidth() / 2;
    }

    public static class GuillotineBuilder {
        private final View slicerView;
        private final View openingView;
        private final View closingView;
        private View actionBarView;
        private SlicerListener slicerListener;
        private long duration;
        private long startDelay;
        private boolean isRightToLeftLayout;
        private TimeInterpolator interpolator;
        private boolean isClosedOnStart;

        public GuillotineBuilder(View slicerView, View closingView, View openingView) {
            this.slicerView = slicerView;
            this.openingView = openingView;
            this.closingView = closingView;
        }

        public GuillotineBuilder setActionBarViewForAnimation(View view) {
            this.actionBarView = view;
            return this;
        }

        public GuillotineBuilder setGuillotineListener(SlicerListener guillotineListener) {
            this.slicerListener = guillotineListener;
            return this;
        }

        public GuillotineBuilder setDuration(long duration) {
            this.duration = duration;
            return this;
        }

        public GuillotineBuilder setStartDelay(long startDelay) {
            this.startDelay = startDelay;
            return this;
        }

        public GuillotineBuilder setRightToLeftLayout(boolean isRightToLeftLayout) {
            this.isRightToLeftLayout = isRightToLeftLayout;
            return this;
        }

        public GuillotineBuilder setInterpolator(TimeInterpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        public GuillotineBuilder setClosedOnStart(boolean isClosedOnStart) {
            this.isClosedOnStart = isClosedOnStart;
            return this;
        }

        public SlicerAnimation build() {
            return new SlicerAnimation(this);
        }
    }

}
