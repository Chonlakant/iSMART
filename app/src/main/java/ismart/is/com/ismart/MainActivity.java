package ismart.is.com.ismart;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import ismart.is.com.ismart.activity.AllCourseActivity;
import ismart.is.com.ismart.activity.MyCourseActivity;
import ismart.is.com.ismart.activity.MainActivityTap;
import ismart.is.com.ismart.activity.SettingActivity;
import ismart.is.com.ismart.adapter.MyRecyclerAdapter;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private SwipeRefreshLayout swipeContainer;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    RelativeLayout content_frame;
    ProgressBar progressBar2;
    MyRecyclerAdapter myRecyclerAdapter;
    private SliderLayout mDemoSlider;
    String[] title = {"สิ่งที่วิศวกรซ่อมบำรุงมืออาชีพ ควรรู้ (4วัน)", "การวางแผนการผลิตในภาพรวมและตารางการวางแผนการผลิตหลัก", "ระบบบำรุงรักษาอัตโนมัติและต่อเนื่อง", "การวิเคราห์มูลค่า (VA)"};
    String[] imagUrl = {"http://blog.wonderme.co/wp-content/uploads/2014/04/006-1.jpg", "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRqz7aVQ7t2A50fdbNu_aBqcXJ7V-ZqM9zMj-94WnYvKeniXak7"
            , "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTe6oap23BTWlnhXyGsKqFpySh11ORy3T53PCJeymrDLkY23tg", "http://blog.wonderme.co/wp-content/uploads/2014/04/006-1.jpg"};

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

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Test",R.drawable.abc_ab_share_pack_mtrl_alpha);
        file_maps.put("Test",R.drawable.abc_ab_share_pack_mtrl_alpha);
        file_maps.put("Test",R.drawable.abc_ab_share_pack_mtrl_alpha);
        file_maps.put("Test", R.drawable.abc_ab_share_pack_mtrl_alpha);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
        setupViews();
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

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


    }

    private void setupViews() {
        navigationView.addHeaderView(new DrawerHeaderView(this));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {

                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(), MainActivityTap.class);
                        startActivity(intent);
                        break;
                    case R.id.course:
                        Intent i = new Intent(getApplicationContext(), AllCourseActivity.class);
                        startActivity(i);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.my_course:
                        Intent intent1 = new Intent(getApplicationContext(), MyCourseActivity.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.profile:
                        Intent intent2 = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent2);
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
}
