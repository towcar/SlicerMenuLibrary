package com.carsonskjerdal.slicermenusample;

/**
 * Created by Carson on 4/20/2018.
 * <p>
 * Feel free to use code just give credit please :)
 */
import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by Dmytro Denysenko on 5/6/15.
 */
public class App extends Application {
    private static final String CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf";
    public static Typeface canaroExtraBold;

    @Override
    public void onCreate() {
        super.onCreate();
        initTypeface();
    }

    private void initTypeface() {
        canaroExtraBold = Typeface.createFromAsset(getAssets(), CANARO_EXTRA_BOLD_PATH);

    }
}
