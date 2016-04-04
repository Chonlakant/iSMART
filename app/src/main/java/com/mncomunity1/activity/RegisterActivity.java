package com.mncomunity1.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mncomunity1.AlertDialogManager;
import com.mncomunity1.ConnectionDetector;
import com.mncomunity1.IsmartApp;
import com.mncomunity1.MainActivity;
import com.mncomunity1.PrefManager;

import com.mncomunity1.model.ChatRoom;
import com.mncomunity1.model.Message;
import com.mncomunity1.model.User;
import com.mncomunity1.R;

public class RegisterActivity extends AppCompatActivity {
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private Toolbar toolbar;
    private EditText edit_email, edit_pass, con_password, edit_name, edit_phone, edit_company;
    String email, pass, name, phone, company;
    private ArrayList<ChatRoom> chatRoomArrayList;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private AQuery aq;
    String regId;
    Button btn_register;
    Dialog loadingDialog;
    Dialog loadingDialogPass;
    AlertDialogManager alert = new AlertDialogManager();
    // Internet detector
    ConnectionDetector cd;
    String con;
    String edcondeStringName;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        pref = IsmartApp.getPrefManagerPaty();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_pass = (EditText) findViewById(R.id.edit_pass);
        con_password = (EditText) findViewById(R.id.con_password);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_company = (EditText) findViewById(R.id.edit_company);
        btn_register = (Button) findViewById(R.id.btn_register);
        cd = new ConnectionDetector(getApplicationContext());
        aq = new AQuery(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(RegisterActivity.this, "Internet Connection Error", "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);


//        if(tMgr != null){
//            String mPhoneNumber = tMgr.getLine1Number();
//            Log.e("aaaa",mPhoneNumber);
//        }else{
//            Log.e("aaaa","ddd");
//        }

        loadingDialog = new Dialog(RegisterActivity.this, R.style.FullHeightDialog);
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialogPass = new Dialog(RegisterActivity.this, R.style.FullHeightDialog);
        loadingDialogPass.setContentView(R.layout.dialog_pass);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Register");
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


        Log.e("TAG", "GCM Registration RegisterActivity!!!: " + pref.token().getOr("NONO"));
        regId = pref.token().getOr("aaaa");

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Enter Password");
//        builder.setView(layout);

        con_password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String strPass1 = edit_pass.getText().toString();
                String strPass2 = con_password.getText().toString();
                if (strPass1.equals(strPass2)) {
                    //loadingDialogPass.dismiss();
                } else {
//                    Toast.makeText(getApplicationContext(), "ใส่พาสเวิร์ดไม่ตรงกัน", Toast.LENGTH_SHORT).show();
                    //loadingDialogPass.show();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    onLoginButtonClick();

            }
        });

    }


    private void onLoginButtonClick() {
        con = con_password.getText().toString();
        email = edit_email.getText().toString();
        pass = edit_pass.getText().toString();
        name = edit_name.getText().toString();


        try {
            edcondeStringName = URLDecoder.decode("", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        company = edit_company.getText().toString();
        phone = edit_phone.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Log.e("qqq", pass);
        Log.e("www", pass);
        loadingDialog.show();

        if (TextUtils.isEmpty(email) && email.matches(emailPattern)) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "กรุณาใส่อีเมล์", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "กรุณาใส่พาสเวิร์ด", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(name)) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "กรุณาใส่ชื่อ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(company)) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "กรุณาใส่บริษัท", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "กรุณาใส่เบอร์โทรศัพท์", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "http://mn-community.com/web/register_short.php";


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nameth", name);
        params.put("company", company);
        params.put("email", email);
        params.put("password", pass);
        params.put("tel", phone);
        params.put("regId", regId);


        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
        cb.url(url).type(JSONObject.class).params(params).weakHandler(this, "loginCallback");
        cb.header("Content-Type", "application/x-www-form-urlencoded");
        //cb.header("Content-Type", "application/json; charset=utf-8");

        if (!pass.equals(con)) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "พาสเวิร์ด ไม่ตรง", Toast.LENGTH_LONG).show();
            return;
        } else {
            aq.ajax(cb);
        }
    }

    public void loginCallback(String url, JSONObject json, AjaxStatus status) throws JSONException {
        Log.e("return", json.toString(4));

        int success = json.getInt("success");
        String chat_id = json.optString("chat_id");
        String username = json.getString("username");
        Log.e("qqqq", json.getString("username"));
        Log.e("ddd", success + "");
        Log.e("sss", chat_id);


        // storing user in shared preferences

        if (success == 0) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "ลงทะเบียนไม่สำเร็จ/อีเมล์ซ้ำ", Toast.LENGTH_SHORT).show();
        }
        if (success == 1) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "ลงทะเบียนสำเร็จ", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentMain);
            finish();
            User user = new User(chat_id, username, "");
            IsmartApp.getInstance().getPrefManager().storeUser(user);

            pref.isLogin().put(true);
            pref.userName().put(username);
            pref.id().put(chat_id);
            pref.commit();

        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }





    @Override
    protected void onResume() {
        super.onResume();

    }




}
