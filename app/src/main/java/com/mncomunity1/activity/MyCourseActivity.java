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

import com.mncomunity1.R;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import com.mncomunity1.adapter.MyCourseRecyclerAdapter;
import com.mncomunity1.event.ActivityResultBus;
import com.mncomunity1.event.ApiBus;
import com.mncomunity1.event.QualityReceivedEvent;
import com.mncomunity1.event.QualityRequestedEvent;
import com.mncomunity1.model.Post;

public class MyCourseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    MyCourseRecyclerAdapter myCourseRecyclerAdapter;
    ArrayList<Post> list = new ArrayList<>();
    RecyclerView recList;

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
        ApiBus.getInstance().postQueue(new QualityRequestedEvent());
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Course");
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
    public void getList(final QualityReceivedEvent event) {
        if (event != null) {
            Log.e("bbbbbbbb", event.getPost().getPost().size() + "");
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                list.add(event.getPost());
                recList.setAdapter(myCourseRecyclerAdapter);
            }

            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new MyCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {


                }
            });

//            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new MyCourseRecyclerAdapter.OnItemClickListener() {
//                Dialog dialog;
//
//                @Override
//                public void onItemClick(View view, int position) {
//                    Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
//                    dialog = new Dialog(MyCourseActivity.this, R.style.FullHeightDialog);
//                    dialog.setContentView(R.layout.dialog_check_user);
//                    dialog.show();
//                }
//            });

        }

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
