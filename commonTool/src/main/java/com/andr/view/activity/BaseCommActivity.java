package com.andr.view.activity;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * <pre>
 *     author:
 *     time  : 2019-10-21
 *     desc  : new class
 * </pre>
 */
public abstract class BaseCommActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {


        //添加屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //实现沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //注意要清除 FLAG_TRANSLUCENT_STATUS flag
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        setCustomDensity(this, getApplication());
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        initView();
        initViewData();
    }

    public abstract int layoutId();

    public abstract void initView();

    public abstract void initViewData();

    /*今日头条适配屏幕适配方案*/
    private static float swidth = 360;  //设计图屏幕宽 dp
    private static float sNdy = 0;
    private static float sNsdy = 0;

    public void setCustomDensity(Activity activity, final Application application)
    {
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (sNdy == 0)
        {
            sNdy = displayMetrics.density;
            sNsdy = displayMetrics.scaledDensity;

            application.registerComponentCallbacks(new ComponentCallbacks()
            {
                @Override
                public void onConfigurationChanged(Configuration newConfig)
                {
                    if (newConfig != null && newConfig.fontScale > 0)
                    {
                        sNsdy = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory()
                {

                }
            });


        }


        final float tagerDensity = displayMetrics.widthPixels / swidth;
        final float tagerScaleDensity = tagerDensity * (sNdy / sNsdy);
        final int tagerDpi = (int) (160 * tagerDensity);

        displayMetrics.density = tagerDensity;
        displayMetrics.scaledDensity = tagerScaleDensity;
        displayMetrics.densityDpi = tagerDpi;

        final DisplayMetrics activityDisplay = activity.getResources().getDisplayMetrics();

        activityDisplay.density = tagerDensity;
        activityDisplay.scaledDensity = tagerScaleDensity;
        activityDisplay.densityDpi = tagerDpi;

    }
}
