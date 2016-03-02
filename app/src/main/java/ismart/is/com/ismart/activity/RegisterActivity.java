package ismart.is.com.ismart.activity;

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
import android.text.TextUtils;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ismart.is.com.ismart.IsmartApp;
import ismart.is.com.ismart.MainActivity;
import ismart.is.com.ismart.PrefManager;
import ismart.is.com.ismart.R;
import ismart.is.com.ismart.app.Config;

import ismart.is.com.ismart.gcm.GcmIntentService;
import ismart.is.com.ismart.gcm.NotificationUtils;
import ismart.is.com.ismart.model.ChatRoom;
import ismart.is.com.ismart.model.Message;
import ismart.is.com.ismart.model.User;

public class RegisterActivity extends AppCompatActivity{
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private Toolbar toolbar;
    private EditText edit_email, edit_pass, con_password, edit_name, edit_phone, edit_company;
    String email, pass, con_pass, name, phone, company;
    private ArrayList<ChatRoom> chatRoomArrayList;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private AQuery aq;
    String regId;
    Button btn_register;
    Dialog loadingDialog;
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
        aq   = new AQuery(getApplicationContext());
        TelephonyManager tMgr =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);


//        if(tMgr != null){
//            String mPhoneNumber = tMgr.getLine1Number();
//            Log.e("aaaa",mPhoneNumber);
//        }else{
//            Log.e("aaaa","ddd");
//        }

        loadingDialog = new Dialog(RegisterActivity.this, R.style.FullHeightDialog);
        loadingDialog.setContentView(R.layout.dialog_loading);
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

        InstanceID instanceID = InstanceID.getInstance(this);
        String token = null;
        try {
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Log.e("TAG", "GCM Registration RegisterActivity!!!: " + pref.token().getOr("NONO"));
        regId = pref.token().getOr("aaaa");

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    subscribeToGlobalTopic();

                } else if (intent.getAction().equals(Config.SENT_TOKEN_TO_SERVER)) {
                    // gcm registration id is stored in our server's MySQL
                    Log.e("TAG", "GCM registration id is sent to our server");

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    handlePushNotification(intent);
                }
            }
        };

        if (checkPlayServices()) {
            registerGCM();
            //fetchChatRooms();
        }

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginButtonClick();
            }
        });

    }
    private void subscribeToGlobalTopic() {
        Intent intent = new Intent(this, GcmIntentService.class);
        intent.putExtra(GcmIntentService.KEY, GcmIntentService.SUBSCRIBE);
        intent.putExtra(GcmIntentService.TOPIC, Config.TOPIC_GLOBAL);
        startService(intent);
    }
    private void onLoginButtonClick() {
        email = edit_email.getText().toString();
        pass = edit_pass.getText().toString();
        name = edit_name.getText().toString();
        company = edit_company.getText().toString();
        phone = edit_phone.getText().toString();
        Log.e("qqq", pass);
        Log.e("www", pass);
        loadingDialog.show();
        if (TextUtils.isEmpty(email)) {
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
        aq.ajax(cb);

    }

    public void loginCallback(String url, JSONObject json, AjaxStatus status) throws JSONException {
        Log.e("return", json.toString(4));

        int success = json.getInt("success");
        String chat_id = json.optString("chat_id");
        String username = json.getString("username");
        Log.e("qqqq", json.getString("username"));
        Log.e("ddd", success + "");
        Log.e("sss",chat_id);


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

    private void handlePushNotification(Intent intent) {
        int type = intent.getIntExtra("type", -1);

        // if the push is of chat room message
        // simply update the UI unread messages count
        if (type == Config.PUSH_TYPE_CHATROOM) {
            Message message = (Message) intent.getSerializableExtra("message");
            String chatRoomId = intent.getStringExtra("chat_room_id");

            if (message != null && chatRoomId != null) {
                updateRow(chatRoomId, message);
            }
        } else if (type == Config.PUSH_TYPE_USER) {
            // push belongs to user alone
            // just showing the message in a toast
            Message message = (Message) intent.getSerializableExtra("message");
            Toast.makeText(getApplicationContext(), "New push: " + message.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    /**
     * Updates the chat list unread count and the last message
     */
    private void updateRow(String chatRoomId, Message message) {
        for (ChatRoom cr : chatRoomArrayList) {
            if (cr.getId().equals(chatRoomId)) {
                int index = chatRoomArrayList.indexOf(cr);
                cr.setLastMessage(message.getMessage());
                cr.setUnreadCount(cr.getUnreadCount() + 1);
                chatRoomArrayList.remove(index);
                chatRoomArrayList.add(index, cr);
                break;
            }
        }
        // mAdapter.notifyDataSetChanged();
    }






    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clearing the notification tray
        NotificationUtils.clearNotifications();
    }

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
                Log.e("TAG", "This device is not supported. Google Play Services not installed!");
                Toast.makeText(getApplicationContext(), "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }


}
