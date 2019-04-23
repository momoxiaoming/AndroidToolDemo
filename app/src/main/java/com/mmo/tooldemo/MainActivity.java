package com.mmo.tooldemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint("MissingPermission") String imei = ((TelephonyManager) this.getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        Log.d("allen", "imei211:");

        Log.d("allen", "imei:" + imei);


        @SuppressLint("MissingPermission") String android_imsi = ((TelephonyManager) this.getSystemService(TELEPHONY_SERVICE)).getSubscriberId();
        Log.d("allen", "imsi:" + android_imsi);

    }
}
