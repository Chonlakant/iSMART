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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import ismart.is.com.ismart.activity.AllCourseActivity;
import ismart.is.com.ismart.activity.MyCourseActivity;
import ismart.is.com.ismart.activity.MainActivityTap;
import ismart.is.com.ismart.adapter.MyRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    RelativeLayout content_frame;
    ProgressBar progressBar2;
    MyRecyclerAdapter myRecyclerAdapter;

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

                    case R.id.enterprise:

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
}
