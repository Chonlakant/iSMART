package com.mncomunity1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import com.google.android.gcm.GCMRegistrar;
import com.mncomunity1.activity.ListWebViewActivity;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import com.mncomunity1.activity.AboutWebViewActivity;
import com.mncomunity1.activity.ListActivity;
import com.mncomunity1.activity.LoginActivity;
import com.mncomunity1.activity.PhotoActivityMian;
import com.mncomunity1.activity.TrainCourseActivity;
import com.mncomunity1.adapter.MyRecyclerAdapter;
import com.mncomunity1.event.ActivityResultBus;
import com.mncomunity1.event.ApiBus;
import com.mncomunity1.event.FeedReceivedEvent;
import com.mncomunity1.event.FeedRequestedEvent;
import com.mncomunity1.event.PhotoReceivedEvent;
import com.mncomunity1.event.PhotoRequestedEvent;
import com.mncomunity1.model.ChatRoom;
import com.mncomunity1.model.Message;
import com.mncomunity1.model.Post;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    IsmartApp aController;
    AlertDialogManager alert = new AlertDialogManager();
    ConnectionDetector cd;
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
    AsyncTask<Void, Void, Void> mRegisterTask;
    String photo1;
    public static String name;
    public static String email;

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

        aController = (IsmartApp) getApplicationContext();
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


        setupViews();
        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);



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
        Intent i = getIntent();

        name = i.getStringExtra("name");
        email = i.getStringExtra("email");
        GCMRegistrar.checkDevice(this);


        // Make sure the manifest permissions was properly set
        GCMRegistrar.checkManifest(this);

        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                Config.DISPLAY_MESSAGE_ACTION));


        // Get GCM registration id
        final String regId = GCMRegistrar.getRegistrationId(this);


        // Check if regid already presents
        if (regId.equals("")) {

            // Register with GCM

            //Toast.makeText(getApplicationContext(), "regId "+regId , Toast.LENGTH_LONG).show();

            GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);

        } else {

            // Device is already registered on GCM Server
            if (GCMRegistrar.isRegisteredOnServer(this)) {

                // Skips registration.
                //Toast.makeText(getApplicationContext(), "Already registered with GCM Server", Toast.LENGTH_LONG).show();

            } else {

                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.

                final Context context = this;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {

                        // Register on our server
                        // On server creates a new user
                        //aController.register(context, name, email, regId);

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };

                // execute AsyncTask
                mRegisterTask.execute(null, null, null);
            }
        }
        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                Config.DISPLAY_MESSAGE_ACTION));

    }

    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String newMessage = intent.getExtras().getString(Config.EXTRA_MESSAGE);

            // Waking up mobile if it is sleeping
            aController.acquireWakeLock(getApplicationContext());

            // Display message on the screen
            //lblMessage.append(newMessage + "\n");

            Toast.makeText(getApplicationContext(), "Got Message: " + newMessage, Toast.LENGTH_LONG).show();


		/*	webWiew1.getSettings().setJavaScriptEnabled(true);
			webWiew1.loadUrl(newMessage.toString());

			webWiew1.setWebViewClient(new WebViewClient(){

			    @Override
			    public boolean shouldOverrideUrlLoading(WebView view, String url){
			      view.loadUrl(url);
			      return true;
			    }
			});*/


            // Releasing wake lock
            aController.releaseWakeLock();
        }
    };

    private void setupViews() {
        // navigationView.addHeaderView(new DrawerHeaderView(this));
        ImageView user_image = (ImageView) navigationView.findViewById(R.id.user_image);
        LinearLayout News = (LinearLayout) navigationView.findViewById(R.id.News);
        LinearLayout Articles = (LinearLayout) navigationView.findViewById(R.id.Articles);
        LinearLayout Training = (LinearLayout) navigationView.findViewById(R.id.Training);
        LinearLayout Tip = (LinearLayout) navigationView.findViewById(R.id.Tip);
        LinearLayout Energy = (LinearLayout) navigationView.findViewById(R.id.Energy);
        LinearLayout Success = (LinearLayout) navigationView.findViewById(R.id.Success);
        LinearLayout vdo = (LinearLayout) navigationView.findViewById(R.id.vdo);
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
                intent2.putExtra("key","About");
                startActivity(intent2);
                drawerLayout.closeDrawers();
            }
        });
        vdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),TrainCourseActivity.class);
                startActivity(i);


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
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityResultBus.getInstance().unregister(this);
        ApiBus.getInstance().unregister(this);
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
                    share.putExtra(Intent.EXTRA_SUBJECT, "ข่าวสารจาก Maintenance Community");
                    share.putExtra(Intent.EXTRA_TEXT, link);

                    startActivity(Intent.createChooser(share, "Share link!"));
                }
            });

            myRecyclerAdapter.SetOnItemClickListenerRead(new MyRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = list.get(position).getPost().get(position).getLink();
                    String title = list.get(position).getPost().get(position).getTitle();
                    Intent intent2 = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    intent2.putExtra("link", link);
                    intent2.putExtra("title",title);
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

        }

    }
}
