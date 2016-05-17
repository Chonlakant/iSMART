package com.mncomunity1.activity;

        import android.app.Dialog;
        import android.content.BroadcastReceiver;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.os.Handler;
        import android.preference.PreferenceManager;
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


        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.Map;


        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.GoogleApiAvailability;
        import com.madx.updatechecker.lib.UpdateRunnable;
        import com.mncomunity1.AlertDialogManager;
        import com.mncomunity1.Config;
        import com.mncomunity1.ConnectionDetector;
        import com.mncomunity1.IsmartApp;
        import com.mncomunity1.MainActivity;
        import com.mncomunity1.PrefManager;
        import com.mncomunity1.gcm.GcmIntentService;
        import com.mncomunity1.model.User;
        import com.mncomunity1.R;


public class LoginActivity extends AppCompatActivity {
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
    SharedPreferences prefs ;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    boolean islogin;
    boolean isRegister;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
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
        final IsmartApp aController = (IsmartApp) getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Log.e("islogin",islogin+"");
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(LoginActivity.this, "Internet Connection Error", "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        isRegister = prefs.getBoolean("islogin", false);
        Log.e("isRegister",isRegister+"");
        if(isRegister!=false){

        }else{
            Toast.makeText(getApplicationContext(),"OK Google",Toast.LENGTH_SHORT).show();
            if (checkPlayServices()) {
                registerGCM();
            }
        }



        islogin = prefs.getBoolean("islogin", false);
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
        REGID = prefs.getString("token", "");
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
        params.put("password", password);
        params.put("regId", REGID);
        IsmartApp.getInstance().getPrefManagerPaty().isLogin().put(true).commit();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("islogin", true);
        editor.commit();

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

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("chatId", id);
            editor.putString("name", name);
            editor.putBoolean("islogin", true);
            editor.commit();

            // storing user in shared preferences
            IsmartApp.getInstance().getPrefManager().storeUser(user);

            pref.isLogin().put(true);
            pref.userName().put(json.getString("username"));
            pref.vendeName().put(nameHeader);
            pref.id().put(id);
            pref.commit();


        }
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
