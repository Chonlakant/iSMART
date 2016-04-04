package com.mncomunity1.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gcm.GCMRegistrar;
import com.madx.updatechecker.lib.UpdateRunnable;
import com.mncomunity1.AlertDialogManager;
import com.mncomunity1.Config;
import com.mncomunity1.ConnectionDetector;
import com.mncomunity1.IsmartApp;
import com.mncomunity1.MainActivity;
import com.mncomunity1.PrefManager;
import com.mncomunity1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivityTest extends AppCompatActivity {
    AlertDialogManager alert = new AlertDialogManager();
    // Internet detector
    ConnectionDetector cd;
    private EditText inputPass, inputEmail;
    private Button btnEnter;
    private TextView txt_register, textView13;
    PrefManager pref;
    Dialog loadingDialog;
    private AQuery aq;
    String email;
    String password;
    String REGID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (IsmartApp.getInstance().getPrefManagerPaty().isLogin().getOr(false) != false) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        cd = new ConnectionDetector(getApplicationContext());
        aq = new AQuery(this);
        pref = IsmartApp.getPrefManagerPaty();


        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(LoginActivityTest.this, "Internet Connection Error", "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        final IsmartApp aController = (IsmartApp) getApplicationContext();

        loadingDialog = new Dialog(LoginActivityTest.this, R.style.FullHeightDialog);
        textView13 = (TextView) findViewById(R.id.textView13);
        loadingDialog.setContentView(R.layout.dialog_loading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_register = (TextView) findViewById(R.id.txt_register);
        setSupportActionBar(toolbar);
//        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
//        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputPass = (EditText) findViewById(R.id.input_pass);
        inputEmail = (EditText) findViewById(R.id.input_email);
        btnEnter = (Button) findViewById(R.id.btn_enter);

        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);


        //GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);
        String regIdNew = "";
        if (GCMRegistrar.isRegistered(this)) {
            regIdNew = GCMRegistrar.getRegistrationId(this);
            Log.e("regIdNew1", regIdNew);
        } else {
            GCMRegistrar.register(this, "423097242723");
            Log.e("regIdNew2", regIdNew);
        }
        if (regIdNew.equals("")) {
            GCMRegistrar.register(this, "423097242723");
            Log.e("regIdNew3", regIdNew);
        } else {

        }


        String regId1 = regIdNew;
        REGID = regId1;
        Log.e("REGID", regId1);
        Toast.makeText(getApplicationContext(),regId1,Toast.LENGTH_SHORT).show();

        // Check if GCM configuration is set
        if (Config.YOUR_SERVER_URL == null || Config.GOOGLE_SENDER_ID == null || Config.YOUR_SERVER_URL.length() == 0
                || Config.GOOGLE_SENDER_ID.length() == 0) {

            // GCM sernder id / server url is missing
            aController.showAlertDialog(LoginActivityTest.this, "Configuration Error!",
                    "Please set your Server URL and GCM Sender ID", false);

            // stop executing code by return
            return;
        }
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // login();
                onLoginButtonClick();
            }
        });
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              //  Is_Valid_Email(inputEmail);
            }

            public void Is_Valid_Email(EditText edt) {
                if (edt.getText().toString() == null) {
                    edt.setError("Invalid Email Address");
                    email = null;
                } else if (isEmailValid(edt.getText().toString()) == false) {
                    edt.setError("Invalid Email Address");
                    email = null;
                } else {
                    email = edt.getText().toString();
                }
            }

            boolean isEmailValid(CharSequence email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches();
            } // end of TextWatcher (email)
        });

        textView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ForgetActivity.class);
                startActivity(i);
                finish();
            }
        });

        new UpdateRunnable(this, new Handler()).start();
    }

    /**
     * logging in user. Will make http post request with name, email
     * as parameters
     */


    private void onLoginButtonClick() {
        email = inputEmail.getText().toString();
        password = inputPass.getText().toString();

        Log.e("ttt", REGID);
        Log.e("email", email);
        Log.e("password", password);
        loadingDialog.show();
        if (TextUtils.isEmpty(email)) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "กรุณาใส่อีเมล์", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "กรุณาใส่พาสเวิร์ด", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "http://mn-community.com/web/login_all.php";
        //String url =  "http://192.168.1.100:8080/gcm_server_php/register.php";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", email);
        params.put("name", password);
        params.put("regId", REGID);


        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
        cb.url(url).type(JSONObject.class).params(params).weakHandler(this, "loginCallback");
        cb.header("Content-Type", "application/x-www-form-urlencoded");
        aq.ajax(cb);

    }

    public void loginCallback(String url, JSONObject json, AjaxStatus status) throws JSONException {
        Log.e("return", json.toString(4));
        Toast.makeText(getApplicationContext(), "เข้าสู่ระบบสำเร็จ", Toast.LENGTH_SHORT).show();
        Intent intentMain = new Intent(getApplicationContext().getApplicationContext(), MainActivity.class);
        startActivity(intentMain);
        finish();
    }

//    private void login() {
//        if (!validateName()) {
//            return;
//        }
//
//        if (!validateEmail()) {
//            return;
//        }
//
//        final String name = inputName.getText().toString();
//        final String email = inputEmail.getText().toString();
//
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                "http://mn-community.com/web/login_all.php", new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.e(TAG, "response_Login: " + response);
//
//                try {
//                    JSONObject obj = new JSONObject(response);
//
//                    // check for error flag
//                    if (obj.getBoolean("error") == false) {
//                        // user successfully logged in
//
//                        JSONObject userObj = obj.getJSONObject("user");
//                        User user = new User(userObj.getString("user_id"),
//                                userObj.getString("name"),
//                                userObj.getString("email"));
//
//                        // storing user in shared preferences
//                        IsmartApp.getInstance().getPrefManager().storeUser(user);
//
//                        // start main activity
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        finish();
//
//                    } else {
//                        // login error - simply toast the message
//                        Toast.makeText(getApplicationContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
//                    }
//
//                } catch (JSONException e) {
//                    Log.e(TAG, "json parsing error: " + e.getMessage());
//                    Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                NetworkResponse networkResponse = error.networkResponse;
//                Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
//                Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("email", name);
//                params.put("password", email);
//                params.put("regId", regId);
//
//                Log.e(TAG, "params: " + params.toString());
//                return params;
//            }
//        };
//
//        //Adding request to request queue
//        IsmartApp.getInstance().addToRequestQueue(strReq);
//    }

//    private void requestFocus(View view) {
//        if (view.requestFocus()) {
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//        }
//    }
//
//    // Validating name
//    private boolean validateName() {
//        if (inputEmail.getText().toString().trim().isEmpty()) {
//            inputLayoutName.setError(getString(R.string.err_msg_name));
//            requestFocus(inputName);
//            return false;
//        } else {
//            inputLayoutName.setErrorEnabled(false);
//        }
//
//        return true;
//    }
//
//    // Validating email
//    private boolean validateEmail() {
//        String email = inputEmail.getText().toString().trim();
//
//        if (email.isEmpty() || !isValidEmail(email)) {
//            inputLayoutEmail.setError(getString(R.string.err_msg_email));
//            requestFocus(inputEmail);
//            return false;
//        } else {
//            inputLayoutEmail.setErrorEnabled(false);
//        }
//
//        return true;
//    }
//
//    private static boolean isValidEmail(String email) {
//        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
//    }
//
//    private class MyTextWatcher implements TextWatcher {
//
//        private View view;
//
//        private MyTextWatcher(View view) {
//            this.view = view;
//        }
//
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        }
//
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        }
//
//        public void afterTextChanged(Editable editable) {
//            switch (view.getId()) {
//                case R.id.input_name:
//                    validateName();
//                    break;
//                case R.id.input_email:
//                    validateEmail();
//                    break;
//            }
//        }
//    }






}
