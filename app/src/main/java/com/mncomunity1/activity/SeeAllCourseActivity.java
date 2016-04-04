package com.mncomunity1.activity;

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
import android.widget.Toast;

import com.mncomunity1.R;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import com.mncomunity1.adapter.MyCourseRecyclerAdapter;
import com.mncomunity1.event.ActivityResultBus;
import com.mncomunity1.event.ApiBus;
import com.mncomunity1.event.IsoReceivedEvent;
import com.mncomunity1.event.IsoRequestedEvent;
import com.mncomunity1.event.LogisiticsRequestedEvent;
import com.mncomunity1.event.LogisticsReceivedEvent;
import com.mncomunity1.event.MaintenanceReceivedEvent;
import com.mncomunity1.event.MaintenanceRequestedEvent;
import com.mncomunity1.event.ManagemantReceivedEvent;
import com.mncomunity1.event.ManagementRequestedEvent;
import com.mncomunity1.event.ProductionReceivedEvent;
import com.mncomunity1.event.ProductionRequestedEvent;
import com.mncomunity1.event.PurchaseReceivedEvent;
import com.mncomunity1.event.PurchaseRequestedEvent;
import com.mncomunity1.event.QualityReceivedEvent;
import com.mncomunity1.event.QualityRequestedEvent;
import com.mncomunity1.event.SafetyReceivedEvent;
import com.mncomunity1.event.SafetyRequestedEvent;
import com.mncomunity1.event.SaleReceivedEvent;
import com.mncomunity1.event.SaleRequestedEvent;
import com.mncomunity1.model.Post;

public class SeeAllCourseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    MyCourseRecyclerAdapter myCourseRecyclerAdapter;
    ArrayList<Post> list = new ArrayList<>();
    RecyclerView recList;
    String key;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity_course);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        Intent intent = getIntent();
        key = getIntent().getStringExtra("cat");
        Log.e("ddddd", key);
        if (key.equals("MT")) {
            ApiBus.getInstance().postQueue(new MaintenanceRequestedEvent());
        }
        if (key.equals("PD")) {
            ApiBus.getInstance().postQueue(new ProductionRequestedEvent());
        }
        if (key.equals("SA")) {
            ApiBus.getInstance().postQueue(new SafetyRequestedEvent());
        }
        if (key.equals("QC")) {
            ApiBus.getInstance().postQueue(new QualityRequestedEvent());
        }
        if (key.equals("WH")) {
            Toast.makeText(getApplicationContext(), "aaa", Toast.LENGTH_SHORT).show();
            ApiBus.getInstance().postQueue(new LogisiticsRequestedEvent());
        }
        if (key.equals("MA")) {
            ApiBus.getInstance().postQueue(new ManagementRequestedEvent());
        }
        if (key.equals("IS")) {
            ApiBus.getInstance().postQueue(new IsoRequestedEvent());
        }
        if (key.equals("PC")) {
            ApiBus.getInstance().postQueue(new PurchaseRequestedEvent());
        }
        if (key.equals("SM")) {
            ApiBus.getInstance().postQueue(new SaleRequestedEvent());
        }

        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        myCourseRecyclerAdapter = new MyCourseRecyclerAdapter(getApplicationContext(), list);


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            Dialog dialog;
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                dialog = new Dialog(Course_Activity.this, R.style.FullHeightDialog);
//                dialog.setContentView(R.layout.dialog_check_user);
//
//
//                dialog.show();
//            }
//        });

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
                list.add(event.getPost());
                recList.setAdapter(myCourseRecyclerAdapter);
            }
        }

    }

    @Subscribe
    public void GetQuality(final QualityReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                list.add(event.getPost());
                recList.setAdapter(myCourseRecyclerAdapter);
            }

        }

    }

    @Subscribe
    public void GetSafety(final SafetyReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                list.add(event.getPost());
                recList.setAdapter(myCourseRecyclerAdapter);
            }

        }

    }

    @Subscribe
    public void GetProduction(final ProductionReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                list.add(event.getPost());
                recList.setAdapter(myCourseRecyclerAdapter);
            }

        }

    }

    @Subscribe
    public void GetMainten(final MaintenanceReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                list.add(event.getPost());
                recList.setAdapter(myCourseRecyclerAdapter);
            }

            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new MyCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
//                    String id = list.get(position).getPost().get(position).getCode();
//                    Intent i = new Intent(getApplicationContext(),DetailCourseActivity.class);
//                    i.putExtra("id",id);
//                    startActivity(i);
                }
            });

        }

    }

    @Subscribe
    public void GetManagement(final ManagemantReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                list.add(event.getPost());
                recList.setAdapter(myCourseRecyclerAdapter);
            }
        }

    }

    @Subscribe
    public void GetIso(final IsoReceivedEvent event) {
        if (event != null) {

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                list.add(event.getPost());
                recList.setAdapter(myCourseRecyclerAdapter);
            }
        }

    }

    @Subscribe
    public void GetPurchase(final PurchaseReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                list.add(event.getPost());
                recList.setAdapter(myCourseRecyclerAdapter);
            }

        }

    }

    @Subscribe
    public void GetSale(final SaleReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                list.add(event.getPost());
                recList.setAdapter(myCourseRecyclerAdapter);
            }
        }

    }



    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
