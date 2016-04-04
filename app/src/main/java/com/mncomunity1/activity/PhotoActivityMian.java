package com.mncomunity1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

import com.mncomunity1.R;
import com.squareup.picasso.Picasso;

import com.mncomunity1.event.ActivityResultBus;
import com.mncomunity1.event.ApiBus;

public class PhotoActivityMian extends AppCompatActivity {


    String urlImage;
    ImageView imageView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_main);
        urlImage = getIntent().getStringExtra("photos");
        Log.e("ddddd",urlImage);
//        String url = Constant.enPointh + urlImage;
        imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(getApplicationContext())
                .load(urlImage)
                .into(imageView);
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


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
