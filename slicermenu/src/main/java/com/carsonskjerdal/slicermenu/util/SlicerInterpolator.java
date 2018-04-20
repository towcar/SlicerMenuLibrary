package com.carsonskjerdal.slicermenu.util;

import android.animation.TimeInterpolator;

/**
 * Created by Carson on 4/19/2018.
 * <p>
 * Feel free to use code just give credit please :)
 */
public class SlicerInterpolator implements TimeInterpolator {

    public static final float ROTATION_TIME = 0.46667f;
    public static final float FIRST_BOUNCE_TIME = 0.26666f;
    public static final float SECOND_BOUNCE_TIME = 0.26667f;


    public SlicerInterpolator() {
    }

    public float getInterpolation(float t) {
        if (t < ROTATION_TIME) return rotation(t);
        else if (t < ROTATION_TIME + FIRST_BOUNCE_TIME) return firstBounce(t);
        else return secondBounce(t);
    }

    private float rotation(float t) {
        return 4.592f * t * t;
    }

    private float firstBounce(float t) {
        return 2.5f * t * t - 3f * t + 1.85556f;
    }

    private float secondBounce(float t) {
        return 0.625f * t * t - 1.083f * t + 1.458f;
    }
}