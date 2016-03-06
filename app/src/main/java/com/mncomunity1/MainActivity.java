package com.mncomunity1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.mncomunity1.activity.AllCourseActivity;
import com.mncomunity1t.R;
import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import com.mncomunity1.activity.AboutWebViewActivity;
import com.mncomunity1.activity.ListActivity;
import com.mncomunity1.activity.LoginActivity;
import com.mncomunity1.activity.PhotoActivityMian;
import com.mncomunity1.activity.TrainCourseActivity;
import com.mncomunity1.adapter.MyRecyclerAdapter;
import com.mncomunity1.app.Config;
import com.mncomunity1.app.EndPoints;
import com.mncomunity1.event.ActivityResultBus;
import com.mncomunity1.event.ApiBus;
import com.mncomunity1.event.FeedReceivedEvent;
import com.mncomunity1.event.FeedRequestedEvent;
import com.mncomunity1.event.PhotoReceivedEvent;
import com.mncomunity1.event.PhotoRequestedEvent;
import com.mncomunity1.gcm.GcmIntentService;
import com.mncomunity1.gcm.NotificationUtils;
import com.mncomunity1.model.ChatRoom;
import com.mncomunity1.model.Message;
import com.mncomunity1.model.Post;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    AlertDialogManager alert = new AlertDialogManager();
    ConnectionDetector cd;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    RelativeLayout content_frame;
    ProgressBar progressBar2;
    private ArrayList<ChatRoom> chatRoomArrayList;
    MyRecyclerAdapter myRecyclerAdapter;
    private SliderLayout mDemoSlider;
    ArrayList<Post> list = new ArrayList<>();
    private String TAG = MainActivity.class.getSimpleName();
    RecyclerView recList;
    ImageView image_view;

    String photo1;
    String photo2;
    String photo3;

    String title1;
    String title2;
    String title3;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        image_view = (ImageView) findViewById(R.id.image_view);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        content_frame = (RelativeLayout) findViewById(R.id.content_frame);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        progressBar2.setVisibility(View.VISIBLE);
        ApiBus.getInstance().postQueue(new FeedRequestedEvent());
        ApiBus.getInstance().postQueue(new PhotoRequestedEvent());



        //String selfUserId = IsmartApp.getInstance().getPrefManager().getUser().getId();
        // String selfUserId =  IsmartApp.getInstance().getPrefManagerPaty().id().getOr("");
        String selfUserId = IsmartApp.getInstance().getPrefManagerPaty().vendeName().getOr("");
        Log.e("ssss", selfUserId);
        cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(MainActivity.this, "Internet Connection Error", "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        if (IsmartApp.getInstance().getPrefManagerPaty().userName().getOr("") == null) {
            launchLoginActivity();
        }

        String email = IsmartApp.getInstance().getPrefManagerPaty().email().getOr("");
        String name = IsmartApp.getInstance().getPrefManagerPaty().userName().getOr("");
//        User user = new User(selfUserId, name, email);
//        IsmartApp.getInstance().getPrefManager().storeUser(user);

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


        setupViews();
        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        chatRoomArrayList = new ArrayList<>();


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Maintenance Community");
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


        if (checkPlayServices()) {
            registerGCM();
            fetchChatRooms();
        }

    }


    private void setupViews() {
        // navigationView.addHeaderView(new DrawerHeaderView(this));
        ImageView user_image = (ImageView) navigationView.findViewById(R.id.user_image);
        LinearLayout News = (LinearLayout) navigationView.findViewById(R.id.News);
        LinearLayout Articles = (LinearLayout) navigationView.findViewById(R.id.Articles);
        LinearLayout Training = (LinearLayout) navigationView.findViewById(R.id.Training);
        LinearLayout Tip = (LinearLayout) navigationView.findViewById(R.id.Tip);
        LinearLayout Energy = (LinearLayout) navigationView.findViewById(R.id.Energy);
        LinearLayout Success = (LinearLayout) navigationView.findViewById(R.id.Success);
        LinearLayout About = (LinearLayout) navigationView.findViewById(R.id.About);
        LinearLayout Log = (LinearLayout) navigationView.findViewById(R.id.Log);

        Picasso.with(getApplicationContext())
                .load("http://www.mx7.com/i/9e5/TRzJwU.png")
                .transform(new RoundedTransformation(100, 4))
                .into(user_image);
        News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent0 = new Intent(getApplicationContext(), ListActivity.class);
                intent0.putExtra("cat", "0");
                startActivity(intent0);
                drawerLayout.closeDrawers();
            }
        });
        Articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ListActivity.class);
                intent1.putExtra("cat", "1");
                startActivity(intent1);
                drawerLayout.closeDrawers();
            }
        });
        Training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TrainCourseActivity.class);
                i.putExtra("cat", "2");
                startActivity(i);
                drawerLayout.closeDrawers();
            }
        });
        Tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent my_tip = new Intent(getApplicationContext(), ListActivity.class);
                my_tip.putExtra("cat", "5");
                startActivity(my_tip);
                drawerLayout.closeDrawers();
            }
        });
        Success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getApplicationContext(), ListActivity.class);
                intent5.putExtra("cat", "4");
                startActivity(intent5);
                drawerLayout.closeDrawers();
            }
        });

        Energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getApplicationContext(), ListActivity.class);
                intent4.putExtra("cat", "3");
                startActivity(intent4);
            }
        });
        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), AboutWebViewActivity.class);
                intent2.putExtra("link", "http://mn-community.com/community_service/about_us.php");
                startActivity(intent2);
                drawerLayout.closeDrawers();
            }
        });
        Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                        IsmartApp.getInstance().logout(getApplicationContext());
                IsmartApp.getInstance().getPrefManagerPaty().clear();
                IsmartApp.getInstance().getPrefManagerPaty().commit();
                Intent intenLogout = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intenLogout);
                drawerLayout.closeDrawers();
            }
        });
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                int id = menuItem.getItemId();
//                switch (id) {
//
//                    case R.id.home:
//                        Intent intent0 = new Intent(getApplicationContext(), ListActivity.class);
//                        intent0.putExtra("cat", "0");
//                        startActivity(intent0);
//                        break;
//                    case R.id.course:
//                        Intent intent1 = new Intent(getApplicationContext(), ListActivity.class);
//                        intent1.putExtra("cat", "1");
//                        startActivity(intent1);
//                        drawerLayout.closeDrawers();
//                        break;
//                    case R.id.my_course:
//                        Intent i = new Intent(getApplicationContext(), TrainCourseActivity.class);
//                        i.putExtra("cat", "2");
//                        startActivity(i);
////                        Intent i = new Intent(getApplicationContext(), AllCourseActivity.class);
////                        i.putExtra("cat", "2");
////                        startActivity(i);
//                        drawerLayout.closeDrawers();
//                        break;
//
//
//                    case R.id.my_tip:
////                        Intent intent1 = new Intent(getApplicationContext(), MyCourseActivity.class);
////                        startActivity(intent1);
//                        Intent my_tip = new Intent(getApplicationContext(), ListActivity.class);
//                        my_tip.putExtra("cat", "5");
//                        startActivity(my_tip);
//                        drawerLayout.closeDrawers();
//                        break;
//
//
//
//                    case R.id.enterprise:
////                        Intent intent1 = new Intent(getApplicationContext(), MyCourseActivity.class);
////                        startActivity(intent1);
//                        Intent intent4 = new Intent(getApplicationContext(), ListActivity.class);
//                        intent4.putExtra("cat", "3");
//                        startActivity(intent4);
//                        drawerLayout.closeDrawers();
//                        break;
//                    case R.id.success:
////                        Intent intent1 = new Intent(getApplicationContext(), MyCourseActivity.class);
////                        startActivity(intent1);
//                        Intent intent5 = new Intent(getApplicationContext(), ListActivity.class);
//                        intent5.putExtra("cat", "4");
//                        startActivity(intent5);
//                        drawerLayout.closeDrawers();
//                        break;
//                    case R.id.abount:
//                        Intent intent2 = new Intent(getApplicationContext(), AboutWebViewActivity.class);
//                        intent2.putExtra("link", "http://mn-community.com/community_service/about_us.php");
//                        startActivity(intent2);
//                        drawerLayout.closeDrawers();
//                        break;
////                    case R.id.call:
////                        Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
////                        intent.putExtra("chat_room_id", "8");
////                        intent.putExtra("name", "Realtime Chat App");
////                        startActivity(intent);
////                        drawerLayout.closeDrawers();
////                        break;
//
////                    case R.id.setting:
////
////                        drawerLayout.closeDrawers();
////                        break;
//
//
//                    case R.id.logout:
////                        IsmartApp.getInstance().logout(getApplicationContext());
//                        IsmartApp.getInstance().getPrefManagerPaty().clear();
//                        IsmartApp.getInstance().getPrefManagerPaty().commit();
//                        Intent intenLogout = new Intent(getApplicationContext(),LoginActivity.class);
//                        startActivity(intenLogout);
//                        drawerLayout.closeDrawers();
//                        break;
//
//                    default:
//                        drawerLayout.closeDrawers();
//                }
//
//                Log.d("MENU ITEM", menuItem.getTitle().toString());
//                return false;
//            }
//        });


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
                //Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                // Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
        ActivityResultBus.getInstance().register(this);
        ApiBus.getInstance().register(this);
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
        ActivityResultBus.getInstance().unregister(this);
        ApiBus.getInstance().unregister(this);
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

    @Subscribe
    public void GetFeed(final FeedReceivedEvent event) {
        if (event != null) {
            progressBar2.setVisibility(View.GONE);
            list.add(event.getPost());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                Log.e("sssss", event.getPost().getPost().get(i).getFile_img());
            }

            myRecyclerAdapter = new MyRecyclerAdapter(getApplicationContext(), list);
            recList.setAdapter(myRecyclerAdapter);

            myRecyclerAdapter.SetOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String urlPhoto = list.get(position).getPost().get(position).getFile_img();
                    Log.e("urlPhoto", urlPhoto);
                    Intent intent = new Intent(getApplicationContext(), PhotoActivityMian.class);
                    intent.putExtra("photos", urlPhoto);
                    startActivity(intent);
                }
            });

            myRecyclerAdapter.SetOnItemClickListenerShare(new MyRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String image = list.get(position).getPost().get(position).getFile_img();
                    String title = list.get(position).getPost().get(position).getTitle();
                    String link = list.get(position).getPost().get(position).getLink();
                    Uri myUri = Uri.parse(image);

//                    Intent shareIntent = new Intent();
//                    shareIntent.setAction(Intent.ACTION_SEND);
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, title);
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, link);
//                    shareIntent.putExtra(Intent.EXTRA_STREAM, myUri);
//                    shareIntent.setType("image/*");
//                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    startActivity(Intent.createChooser(shareIntent, "Share images..."));

                    Intent share = new Intent(android.content.Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                    // Add data to the intent, the receiving app will decide
                    // what to do with it.
                    share.putExtra(Intent.EXTRA_SUBJECT, title);
                    share.putExtra(Intent.EXTRA_TEXT, link);

                    startActivity(Intent.createChooser(share, "Share link!"));
                }
            });

            myRecyclerAdapter.SetOnItemClickListenerRead(new MyRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = list.get(position).getPost().get(position).getLink();
                    Intent intent2 = new Intent(getApplicationContext(), AboutWebViewActivity.class);
                    intent2.putExtra("link", link);
                    startActivity(intent2);
                }
            });
        }

    }

    @Subscribe
    public void GetPhoto(final PhotoReceivedEvent event) {
        if (event != null) {

            photo1 = event.getPost().getPost().get(0).getFile_img();
//            photo2 = event.getPost().getPost().get(1).getFile_img();
//            photo3 = event.getPost().getPost().get(2).getFile_img();
            Picasso.with(getApplicationContext())
                    .load(photo1)
                    .into(image_view);
//
//            Log.e("photo1", photo1);
//            Log.e("photo2", photo2);
//            Log.e("photo3", photo3);
//
//            title1 = event.getPost().getPost().get(0).getTitle();
//            title2 = event.getPost().getPost().get(1).getTitle();
//            title3 = event.getPost().getPost().get(2).getTitle();
//
//            HashMap<String, String> url_maps = new HashMap<String, String>();
//            url_maps.put("รูปที่ 1", "http://ipro-training.com/images/pro1.jpg");
////                url_maps.put("รูปที่ 2", "http://ipro-training.com/images/pro2.jpg");
////                url_maps.put("รูปที่ 3", "http://ipro-training.com/images/pro3.jpg");
//
//
//            for (String name : url_maps.keySet()) {
//                TextSliderView textSliderView = new TextSliderView(this);
//                // initialize a SliderLayout
//
//                textSliderView
//                        .description(name)
//                        .image(url_maps.get(name))
//                        .setScaleType(BaseSliderView.ScaleType.Fit)
//                        .setOnSliderClickListener(this);
//
//                //add your extra information
//                textSliderView.bundle(new Bundle());
//                textSliderView.getBundle()
//                        .putString("extra", name);
//
//
//                mDemoSlider.addSlider(textSliderView);
//            }
//            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.DepthPage);
//            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
//            mDemoSlider.setDuration(10000);
//            mDemoSlider.addOnPageChangeListener(this);


        }

    }
}
