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
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import java.util.ArrayList;

import ismart.is.com.ismart.R;
import ismart.is.com.ismart.adapter.AllCourseRecyclerAdapter;
import ismart.is.com.ismart.adapter.MyCourseRecyclerAdapter;
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
        key = getIntent().getStringExtra("key");
        Log.e("ddddd", key);
        if (key.equals("0")) {
            ApiBus.getInstance().postQueue(new MaintenanceRequestedEvent());
        }
        if (key.equals("1")) {
            ApiBus.getInstance().postQueue(new ProductionRequestedEvent());
        }
        if (key.equals("2")) {
            ApiBus.getInstance().postQueue(new SafetyRequestedEvent());
        }
        if (key.equals("3")) {
            ApiBus.getInstance().postQueue(new QualityRequestedEvent());
        }
        if (key.equals("4")) {
            Toast.makeText(getApplicationContext(), "aaa", Toast.LENGTH_SHORT).show();
            ApiBus.getInstance().postQueue(new LogisiticsRequestedEvent());
        }
        if (key.equals("5")) {
            ApiBus.getInstance().postQueue(new ManagementRequestedEvent());
        }
        if (key.equals("6")) {
            ApiBus.getInstance().postQueue(new IsoRequestedEvent());
        }
        if (key.equals("7")) {
            ApiBus.getInstance().postQueue(new PurchaseRequestedEvent());
        }
        if (key.equals("8")) {
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
                    Intent i = new Intent(getApplicationContext(),DetailCourseActivity.class);
                    startActivity(i);
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
//
//    @Subscribe
//    public void getList(final QualityReceivedEvent event) {
//        if (event != null) {
//            Log.e("bbbbbbbb", event.getPost().getPost().size() + "");
//            for (int i = 0; i < event.getPost().getPost().size(); i++) {
//                list.add(event.getPost());
//
//            }
//
//
//
////            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new MyCourseRecyclerAdapter.OnItemClickListener() {
////                Dialog dialog;
////
////                @Override
////                public void onItemClick(View view, int position) {
////                    Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
////                    dialog = new Dialog(MyCourseActivity.this, R.style.FullHeightDialog);
////                    dialog.setContentView(R.layout.dialog_check_user);
////                    dialog.show();
////                }
////            });
//
//        }
//
//    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
