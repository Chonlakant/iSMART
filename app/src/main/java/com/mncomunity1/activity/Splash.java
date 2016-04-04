package com.mncomunity1.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.android.gcm.GCMRegistrar;
import com.mncomunity1.Config;
import com.mncomunity1.Constant;
import com.mncomunity1.IsmartApp;
import com.mncomunity1.PrefManager;

import com.mncomunity1.R;

import java.io.IOException;
import java.util.regex.Pattern;


public class Splash extends Activity   {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    PrefManager pref;
    String REGID = "";
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }




}