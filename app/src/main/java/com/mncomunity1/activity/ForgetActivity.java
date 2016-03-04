package com.mncomunity1.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.mncomunity1.AlertDialogManager;
import com.mncomunity1.ConnectionDetector;
import com.mncomunity1.IsmartApp;
import com.mncomunity1.MainActivity;
import com.mncomunity1.PrefManager;
import com.mncomunity1.app.Config;
import com.mncomunity1.gcm.GcmIntentService;
import com.mncomunity1.gcm.NotificationUtils;
import com.mncomunity1.model.ChatRoom;
import com.mncomunity1.model.Message;
import com.mncomunity1.model.User;
import com.mncomunity1t.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForgetActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private EditText editText4;
    AQuery  aq;
    Button button4;
    Dialog loadingDialog;
    String email;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }
    PrefManager pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        pref = IsmartApp.getPrefManagerPaty();
        aq = new AQuery(getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        editText4 = (EditText) findViewById(R.id.editText4);
        button4 = (Button) findViewById(R.id.button4);

        loadingDialog = new Dialog(ForgetActivity.this, R.style.FullHeightDialog);
        loadingDialog.setContentView(R.layout.dialog_loading);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("ForgetPassword");
            toolbar.setTitleTextColor(Color.BLACK);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginButtonClick();
            }
        });
    }


    private void onLoginButtonClick() {
        email = editText4.getText().toString();
        loadingDialog.show();
        if (TextUtils.isEmpty(email)) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "กรุณาใส่อีเมล์", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://mn-community.com/web/resetmail1.php";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mail", email);


        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
        cb.url(url).type(JSONObject.class).params(params).weakHandler(this, "loginCallback");
        cb.header("Content-Type", "application/x-www-form-urlencoded");
        aq.ajax(cb);

    }

    public void loginCallback(String url, JSONObject json, AjaxStatus status) throws JSONException {
        Log.e("return", json.toString(4));
//
        int success = json.getInt("status");
//        String chat_id = json.optString("chat_id");
//        String username = json.getString("username");
//        Log.e("qqqq", json.getString("username"));
//        Log.e("ddd", success + "");
//        Log.e("sss",chat_id);
//
//
//        // storing user in shared preferences
//
        if (success == 0) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "อีเมล์นี้ไม่มีในระบบ", Toast.LENGTH_SHORT).show();
        }
        if (success == 1) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "กรุณาตรวจสอบอีเมล์", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intentMain);
            finish();

        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }




}
