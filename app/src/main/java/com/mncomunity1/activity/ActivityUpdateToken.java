package com.mncomunity1.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mncomunity1.R;

public class ActivityUpdateToken extends AppCompatActivity {
    SharedPreferences prefs;
    String REGID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_token);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        REGID = prefs.getString("token", "");

    }

}
