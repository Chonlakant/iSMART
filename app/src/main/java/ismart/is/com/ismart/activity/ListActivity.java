package ismart.is.com.ismart.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import java.util.ArrayList;

import ismart.is.com.ismart.R;
import ismart.is.com.ismart.adapter.AllCourseRecyclerAdapter;
import ismart.is.com.ismart.adapter.CourseListviewAdapter;
import ismart.is.com.ismart.adapter.ListCourseRecyclerAdapter;
import ismart.is.com.ismart.event.ActivityResultBus;
import ismart.is.com.ismart.event.ApiBus;
import ismart.is.com.ismart.event.ArticlesReceivedEvent;
import ismart.is.com.ismart.event.ArticlesRequestedEvent;
import ismart.is.com.ismart.event.EnnigyReceivedEvent;
import ismart.is.com.ismart.event.EnningRequestedEvent;
import ismart.is.com.ismart.event.IsoReceivedEvent;
import ismart.is.com.ismart.event.IsoRequestedEvent;
import ismart.is.com.ismart.event.LogisiticsRequestedEvent;
import ismart.is.com.ismart.event.LogisticsReceivedEvent;
import ismart.is.com.ismart.event.MaintenanceReceivedEvent;
import ismart.is.com.ismart.event.MaintenanceRequestedEvent;
import ismart.is.com.ismart.event.ManagemantReceivedEvent;
import ismart.is.com.ismart.event.ManagementRequestedEvent;
import ismart.is.com.ismart.event.NewsReceivedEvent;
import ismart.is.com.ismart.event.NewsRequestedEvent;
import ismart.is.com.ismart.event.ProductionReceivedEvent;
import ismart.is.com.ismart.event.ProductionRequestedEvent;
import ismart.is.com.ismart.event.PurchaseReceivedEvent;
import ismart.is.com.ismart.event.PurchaseRequestedEvent;
import ismart.is.com.ismart.event.QualityReceivedEvent;
import ismart.is.com.ismart.event.QualityRequestedEvent;
import ismart.is.com.ismart.event.SafetyReceivedEvent;
import ismart.is.com.ismart.event.SafetyRequestedEvent;
import ismart.is.com.ismart.event.SaleReceivedEvent;
import ismart.is.com.ismart.event.SaleRequestedEvent;
import ismart.is.com.ismart.event.SuccessReceivedEvent;
import ismart.is.com.ismart.event.SuccessRequestedEvent;
import ismart.is.com.ismart.model.Post;

public class ListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ListCourseRecyclerAdapter myCourseRecyclerAdapter;
    ListCourseRecyclerAdapter newsRecyclerAdapter;
    ListCourseRecyclerAdapter enningyRecyclerAdapter;
    ListCourseRecyclerAdapter successRecyclerAdapter;

    ArrayList<Post> list = new ArrayList<>();
    ArrayList<Post> listNews = new ArrayList<>();
    ArrayList<Post> listEnningy = new ArrayList<>();
    ArrayList<Post> listSuccess = new ArrayList<>();
    RecyclerView recList;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cat = getIntent().getStringExtra("cat");
        if (cat.equals("0")) {
            Log.e("cat", cat);
            ApiBus.getInstance().postQueue(new NewsRequestedEvent("aa"));
        }
        if (cat.equals("1")) {
            Log.e("cat", cat);
            ApiBus.getInstance().postQueue(new ArticlesRequestedEvent("dd"));
        }
        if (cat.equals("3")) {
            Log.e("cat", cat);
            ApiBus.getInstance().postQueue(new EnningRequestedEvent("dd"));
        }

        if (cat.equals("4")) {
            Log.e("cat", cat);
            ApiBus.getInstance().postQueue(new SuccessRequestedEvent("aa"));
        }


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("All Course");
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
        recList = (RecyclerView) findViewById(R.id.cardList_main);

        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


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
    public void GetLogistics(final ArticlesReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                list.add(event.getPost());
                myCourseRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), list);
                recList.setAdapter(myCourseRecyclerAdapter);
            }

            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent i = new Intent(getApplicationContext(), ListLayer1Activity.class);
                    i.putExtra("link", list.get(position).getPost().get(position).getLink());
                    i.putExtra("cat","0");
                    startActivity(i);
                }
            });

        }

    }

    @Subscribe
    public void GetNews(final NewsReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listNews.add(event.getPost());
                newsRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listNews);
                recList.setAdapter(newsRecyclerAdapter);

                newsRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String link = listNews.get(position).getPost().get(position).getLink();
                        Log.e("qqqq", link);
                        Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                        i.putExtra("link", link);
                        startActivity(i);
                    }
                });
            }


        }

    }

    @Subscribe
    public void GeEnningy(final EnnigyReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listEnningy.add(event.getPost());
                enningyRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listEnningy);
                recList.setAdapter(enningyRecyclerAdapter);
            }

            enningyRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = listEnningy.get(position).getPost().get(position).getLink();
                    Log.e("qqqq", link);
                    Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    i.putExtra("link", link);
                    startActivity(i);
                }
            });

        }

    }

    @Subscribe
    public void GeSuccess(final SuccessReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listSuccess.add(event.getPost());
                successRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listSuccess);
                recList.setAdapter(successRecyclerAdapter);
            }

            successRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = listSuccess.get(position).getPost().get(position).getLink();
                    Log.e("qqqq", link);
                    Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    i.putExtra("link", link);
                    startActivity(i);
                }
            });

        }

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
