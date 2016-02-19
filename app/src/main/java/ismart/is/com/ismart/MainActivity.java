package ismart.is.com.ismart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ismart.is.com.ismart.activity.AllCourseActivity;
import ismart.is.com.ismart.activity.ChatRoomActivity;
import ismart.is.com.ismart.activity.ListActivity;
import ismart.is.com.ismart.activity.LoginActivity;
import ismart.is.com.ismart.activity.MyCourseActivity;
import ismart.is.com.ismart.activity.MainActivityTap;
import ismart.is.com.ismart.activity.SettingActivity;
import ismart.is.com.ismart.adapter.MyRecyclerAdapter;
import ismart.is.com.ismart.adapter.TransformerAdapter;
import ismart.is.com.ismart.app.Config;
import ismart.is.com.ismart.app.EndPoints;
import ismart.is.com.ismart.event.ApiBus;
import ismart.is.com.ismart.event.SaleRequestedEvent;
import ismart.is.com.ismart.gcm.GcmIntentService;
import ismart.is.com.ismart.gcm.NotificationUtils;
import ismart.is.com.ismart.model.ChatRoom;
import ismart.is.com.ismart.model.Message;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private SwipeRefreshLayout swipeContainer;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    RelativeLayout content_frame;
    ProgressBar progressBar2;
    private ArrayList<ChatRoom> chatRoomArrayList;
    MyRecyclerAdapter myRecyclerAdapter;
    private SliderLayout mDemoSlider;
    String[] title = {"สิ่งที่วิศวกรซ่อมบำรุงมืออาชีพ ควรรู้ (4วัน)", "การวางแผนการผลิตในภาพรวมและตารางการวางแผนการผลิตหลัก", "ระบบบำรุงรักษาอัตโนมัติและต่อเนื่อง", "การวิเคราห์มูลค่า (VA)"};
    String[] imagUrl = {"http://blog.wonderme.co/wp-content/uploads/2014/04/006-1.jpg", "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRqz7aVQ7t2A50fdbNu_aBqcXJ7V-ZqM9zMj-94WnYvKeniXak7"
            , "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTe6oap23BTWlnhXyGsKqFpySh11ORy3T53PCJeymrDLkY23tg", "http://blog.wonderme.co/wp-content/uploads/2014/04/006-1.jpg"};
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        content_frame = (RelativeLayout) findViewById(R.id.content_frame);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        if (IsmartApp.getInstance().getPrefManager().getUser() == null) {
            launchLoginActivity();
        }
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
                    Log.e(TAG, "GCM registration id is sent to our server");

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    handlePushNotification(intent);
                }
            }
        };

        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("สิ่งที่วิศวกรซ่อมบำรุงมืออาชีพ ควรรู้ (4วัน)", "http://blog.wonderme.co/wp-content/uploads/2014/04/006-1.jpg");
        url_maps.put("การวางแผนการผลิตในภาพรวมและตารางการวางแผนการผลิตหลัก", "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRqz7aVQ7t2A50fdbNu_aBqcXJ7V-ZqM9zMj-94WnYvKeniXak7");
        url_maps.put("การวิเคราห์มูลค่า (VA)", "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTe6oap23BTWlnhXyGsKqFpySh11ORy3T53PCJeymrDLkY23tg");
        url_maps.put("ระบบบำรุงรักษาอัตโนมัติและต่อเนื่อง", "http://blog.wonderme.co/wp-content/uploads/2014/04/006-1.jpg");


        setupViews();
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        chatRoomArrayList = new ArrayList<>();
        myRecyclerAdapter = new MyRecyclerAdapter(getApplicationContext(), title, imagUrl);
        recList.setAdapter(myRecyclerAdapter);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("iSMART");
            toolbar.setTitleTextColor(Color.BLACK);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                    this,
                    drawerLayout,
                    toolbar,
                    R.string.drawer_open,
                    R.string.drawer_close);
            drawerLayout.setDrawerListener(drawerToggle);
            drawerToggle.setDrawerIndicatorEnabled(true);

            drawerToggle.syncState();
        }
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            Log.e("aaaaa", url_maps.get(name));
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);


            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(10000);
        mDemoSlider.addOnPageChangeListener(this);
        if (checkPlayServices()) {
            registerGCM();
            fetchChatRooms();
        }

    }

    private void setupViews() {
        navigationView.addHeaderView(new DrawerHeaderView(this));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {

                    case R.id.home:
                        Intent intent0 = new Intent(getApplicationContext(), ListActivity.class);
                        intent0.putExtra("cat", "0");
                        startActivity(intent0);
                        break;
                    case R.id.course:
                        Intent intent1 = new Intent(getApplicationContext(), ListActivity.class);
                        intent1.putExtra("cat", "1");
                        startActivity(intent1);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.my_course:
//                        Intent intent1 = new Intent(getApplicationContext(), MyCourseActivity.class);
//                        startActivity(intent1);
                        Intent i = new Intent(getApplicationContext(), AllCourseActivity.class);
                        i.putExtra("cat", "2");
                        startActivity(i);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.enterprise:
//                        Intent intent1 = new Intent(getApplicationContext(), MyCourseActivity.class);
//                        startActivity(intent1);
                        Intent intent4 = new Intent(getApplicationContext(), ListActivity.class);
                        intent4.putExtra("cat", "3");
                        startActivity(intent4);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.success:
//                        Intent intent1 = new Intent(getApplicationContext(), MyCourseActivity.class);
//                        startActivity(intent1);
                        Intent intent5 = new Intent(getApplicationContext(), ListActivity.class);
                        intent5.putExtra("cat", "4");
                        startActivity(intent5);
                        drawerLayout.closeDrawers();
                        break;


//                    case R.id.profile:
//                        Intent intent2 = new Intent(getApplicationContext(), SettingActivity.class);
//                        startActivity(intent2);
//                        drawerLayout.closeDrawers();
//                        break;
                    case R.id.call:
                        Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
                        intent.putExtra("chat_room_id", "8");
                        intent.putExtra("name", "Realtime Chat App");
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.logout:
                        IsmartApp.getInstance().logout(getApplicationContext());
                        drawerLayout.closeDrawers();
                        break;

                    default:
                        drawerLayout.closeDrawers();
                }

                Log.d("MENU ITEM", menuItem.getTitle().toString());
                return false;
            }
        });


    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    /**
     * Handles new push notification
     */
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


    /**
     * fetching the chat rooms by making http call
     */
    private void fetchChatRooms() {
        StringRequest strReq = new StringRequest(Request.Method.GET,
                EndPoints.CHAT_ROOMS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);

                    // check for error flag
                    if (obj.getBoolean("error") == false) {
                        JSONArray chatRoomsArray = obj.getJSONArray("chat_rooms");
                        for (int i = 0; i < chatRoomsArray.length(); i++) {
                            JSONObject chatRoomsObj = (JSONObject) chatRoomsArray.get(i);
                            ChatRoom cr = new ChatRoom();
                            cr.setId(chatRoomsObj.getString("chat_room_id"));
                            cr.setName(chatRoomsObj.getString("name"));
                            cr.setLastMessage("");
                            cr.setUnreadCount(0);
                            cr.setTimestamp(chatRoomsObj.getString("created_at"));

                            chatRoomArrayList.add(cr);
                        }

                    } else {
                        // error in fetching chat rooms
                        Toast.makeText(getApplicationContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                // mAdapter.notifyDataSetChanged();

                // subscribing to all chat room topics
                subscribeToAllTopics();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Adding request to request queue
        IsmartApp.getInstance().addToRequestQueue(strReq);
    }

    // subscribing to global topic
    private void subscribeToGlobalTopic() {
        Intent intent = new Intent(this, GcmIntentService.class);
        intent.putExtra(GcmIntentService.KEY, GcmIntentService.SUBSCRIBE);
        intent.putExtra(GcmIntentService.TOPIC, Config.TOPIC_GLOBAL);
        startService(intent);
    }

    // Subscribing to all chat room topics
    // each topic name starts with `topic_` followed by the ID of the chat room
    // Ex: topic_1, topic_2
    private void subscribeToAllTopics() {
        for (ChatRoom cr : chatRoomArrayList) {

            Intent intent = new Intent(this, GcmIntentService.class);
            intent.putExtra(GcmIntentService.KEY, GcmIntentService.SUBSCRIBE);
            intent.putExtra(GcmIntentService.TOPIC, "topic_" + cr.getId());
            startService(intent);
        }
    }

    private void launchLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
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
                Log.i(TAG, "This device is not supported. Google Play Services not installed!");
                Toast.makeText(getApplicationContext(), "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }
}
