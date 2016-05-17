package com.mncomunity1.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mncomunity1.PrefManager;

import com.mncomunity1.R;
import com.mncomunity1.gcm.GcmIntentService;

import java.io.IOException;
import java.util.regex.Pattern;


public class Splash extends Activity   {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    SharedPreferences prefs ;
    boolean isRegister;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        isRegister = prefs.getBoolean("islogin", false);
        Log.e("isRegister",isRegister+"");
        if(isRegister!=false){

        }else{
           // Toast.makeText(getApplicationContext(),"OK Google",Toast.LENGTH_SHORT).show();
            if (checkPlayServices()) {
                registerGCM();
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isRegister", true);
                editor.commit();
                Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    // starting the service to register with GCM
    private void registerGCM() {
        Intent intent = new Intent(this, GcmIntentService.class);
        intent.putExtra("key", "register");
        startService(intent);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("", "This device is not supported. Google Play Services not installed!");
                Toast.makeText(getApplicationContext(), "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

}