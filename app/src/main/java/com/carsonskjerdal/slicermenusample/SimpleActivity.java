package com.carsonskjerdal.slicermenusample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.carsonskjerdal.slicermenu.animation.SlicerAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;

//Activity for basic set up of the menu

public class SimpleActivity extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 250;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.menu_button)
    View menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View slicerMenu = LayoutInflater.from(this).inflate(R.layout.slicer_simple, null);
        root.addView(slicerMenu);

        new SlicerAnimation.GuillotineBuilder(slicerMenu, slicerMenu.findViewById(R.id.slicer_button), menuButton)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

    }

    }

