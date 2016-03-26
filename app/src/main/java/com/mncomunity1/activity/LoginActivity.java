package com.mncomunity1.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.TagManager;
import com.madx.updatechecker.lib.UpdateRunnable;
import com.mncomunity1.AlertDialogManager;
import com.mncomunity1.ConnectionDetector;
import com.mncomunity1.IsmartApp;
import com.mncomunity1.MainActivity;
import com.mncomunity1.PrefManager;
import com.mncomunity1.gcm.GcmIntentService;
import com.mncomunity1.model.User;
import com.mncomunity1t.R;


public class LoginActivity extends AppCompatActivity {
    AlertDialogManager alert = new AlertDialogManager();
    // Internet detector
    ConnectionDetector cd;
    private EditText inputPass, inputEmail;
    private Button btnEnter;
    private TextView txt_register, textView13;
    private String regId;
    PrefManager pref;
    Dialog loadingDialog;
    private AQuery aq;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Check for login session. It user is already logged in
         * redirect him to main activity
         * */
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
            alert.showAlertDialog(LoginActivity.this, "Internet Connection Error", "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }



        loadingDialog = new Dialog(LoginActivity.this, R.style.FullHeightDialog);
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


//        inputName.addTextChangedListener(new MyTextWatcher(inputName));
//        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));

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
        regId = pref.token().getOr("Token cannot");
        Log.e("cccc", regId);
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

        Log.e("ttt", regId);
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

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", email);
        params.put("password", password);
        params.put("regId", regId);


        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
        cb.url(url).type(JSONObject.class).params(params).weakHandler(this, "loginCallback");
        cb.header("Content-Type", "application/x-www-form-urlencoded");
        aq.ajax(cb);

    }

    public void loginCallback(String url, JSONObject json, AjaxStatus status) throws JSONException {
        Log.e("return", json.toString(4));

        int success = json.getInt("success");
        Log.e("qqqq", json.optInt("success") + "");
        String id = json.getString("chat_id");
        String nameHeader = json.getString("name");
        Log.e("ddd", id);
        Log.e("aaaaa", nameHeader);
        if (success == 0) {
            Toast.makeText(getApplicationContext(), "กรอก Email หรือ Password ผิด", Toast.LENGTH_SHORT).show();
            loadingDialog.dismiss();
        }
        if (success == 1) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "เข้าสู่ระบบสำเร็จ", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(getApplicationContext().getApplicationContext(), MainActivity.class);
            startActivity(intentMain);
            finish();

            String email = json.getString("email");
            String name = json.getString("name");
            User user = new User(id, name, email);

            // storing user in shared preferences
            IsmartApp.getInstance().getPrefManager().storeUser(user);

            pref.isLogin().put(true);
            pref.userName().put(json.getString("username"));
            pref.vendeName().put(nameHeader);
            pref.id().put(id);
            pref.commit();


        }
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
