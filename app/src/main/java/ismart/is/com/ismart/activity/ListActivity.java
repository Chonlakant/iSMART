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
import ismart.is.com.ismart.event.ActivityResultBus;
import ismart.is.com.ismart.event.ApiBus;
import ismart.is.com.ismart.event.IsoReceivedEvent;
import ismart.is.com.ismart.event.IsoRequestedEvent;
import ismart.is.com.ismart.event.LogisiticsRequestedEvent;
import ismart.is.com.ismart.event.LogisticsReceivedEvent;
import ismart.is.com.ismart.event.MaintenanceReceivedEvent;
import ismart.is.com.ismart.event.MaintenanceRequestedEvent;
import ismart.is.com.ismart.event.ManagemantReceivedEvent;
import ismart.is.com.ismart.event.ManagementRequestedEvent;
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
import ismart.is.com.ismart.model.Post;

public class ListActivity extends AppCompatActivity {

    CourseListviewAdapter courseListviewAdapter;
    private Toolbar toolbar;
    AllCourseRecyclerAdapter myCourseRecyclerAdapter;

    ArrayList<Post> list = new ArrayList<>();
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
        if(cat.equals("2")){
            ApiBus.getInstance().postQueue(new LogisiticsRequestedEvent());
            ApiBus.getInstance().postQueue(new QualityRequestedEvent());
            ApiBus.getInstance().postQueue(new SafetyRequestedEvent());
            ApiBus.getInstance().postQueue(new ProductionRequestedEvent());
            ApiBus.getInstance().postQueue(new MaintenanceRequestedEvent());
            ApiBus.getInstance().postQueue(new ManagementRequestedEvent());
            ApiBus.getInstance().postQueue(new IsoRequestedEvent());
            ApiBus.getInstance().postQueue(new PurchaseRequestedEvent());
            ApiBus.getInstance().postQueue(new SafetyRequestedEvent());
            ApiBus.getInstance().postQueue(new SaleRequestedEvent());
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recList.setLayoutManager(layoutManager);


        myCourseRecyclerAdapter = new AllCourseRecyclerAdapter(getApplicationContext(), list);


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
    public void GetLogistics(final LogisticsReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {

            }

            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new AllCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });

        }

    }



    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
