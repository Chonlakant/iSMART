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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.mncomunity1.Constant;
import com.mncomunity1.IsmartApp;
import com.mncomunity1.PrefManager;
import com.mncomunity1.app.Config;
import com.mncomunity1.gcm.GcmIntentService;
import com.mncomunity1t.R;

import java.io.IOException;
import java.util.regex.Pattern;


public class Splash extends Activity   {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 3000;
    private String regId;
    PrefManager pref;
    String token;
    GoogleCloudMessaging gcm;
    InstanceID instanceID;
    String authorizedEntity;
    String scope;
    /** Called when the activity
     *  is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);


        pref = IsmartApp.getPrefManagerPaty();
        regId = pref.token().getOr("Token cannot");
        Log.i("ssssss",  Constant.token + "");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    private void registerGCM() {

        Intent intent = new Intent(this, GcmIntentService.class);
        intent.putExtra("key", "register");
        startService(intent);

    }
    @Override
    protected void onResume() {
        super.onResume();
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
                Log.e("TAG", "This device is not supported. Google Play Services not installed!");
                Toast.makeText(getApplicationContext(), "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }


}