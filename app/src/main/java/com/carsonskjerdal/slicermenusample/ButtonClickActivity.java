package com.carsonskjerdal.slicermenusample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.carsonskjerdal.slicermenu.animation.SlicerAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Carson on 4/29/2018.
 * <p>
 * Feel free to use code just give credit please :)
 */
public class ButtonClickActivity extends AppCompatActivity implements View.OnClickListener {
    private static final long RIPPLE_DURATION = 250;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.menu_button)
    View menuButton;

    Button homeButton;
    Button aroundButton;
    Button settingsButton;
    Button aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View slicerMenu = LayoutInflater.from(this).inflate(R.layout.slicer_button_click, null);
        root.addView(slicerMenu);

        new SlicerAnimation.GuillotineBuilder(slicerMenu, slicerMenu.findViewById(R.id.slicer_button), menuButton)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();


        Log.e("On Click Listener", "test");

        homeButton = root.findViewById(R.id.button1);
        aroundButton = root.findViewById(R.id.button2);
        settingsButton = root.findViewById(R.id.button3);
        aboutButton = root.findViewById(R.id.button4);
        homeButton.setOnClickListener(this);
        aroundButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //handle multiple view click events
            case (R.id.button1): {
                Log.e("On Click Listener", "Button 1");
                break;
            }
            case (R.id.button2): {
                Log.e("On Click Listener", "Button2");
                break;
            }
            case (R.id.button3): {
                Log.e("On Click Listener", "Button3");
                break;
            }

            case (R.id.button4): {
                Log.e("On Click Listener", "Button4");
                break;
            }
        }
    }
}
