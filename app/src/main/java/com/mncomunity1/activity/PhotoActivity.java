package com.mncomunity1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import com.mncomunity1.Constant;
import com.mncomunity1.R;
import com.squareup.picasso.Picasso;

import com.mncomunity1.event.ActivityResultBus;
import com.mncomunity1.event.ApiBus;
//import it.sephiroth.android.library.imagezoom.ImageViewTouch;
//import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

public class PhotoActivity extends AppCompatActivity {


    String urlImage;
    //ImageViewTouch imageView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        urlImage = getIntent().getStringExtra("photos");
        Log.e("ddddd",urlImage);
        String url = Constant.enPointh + urlImage;
//        imageView = (ImageViewTouch) findViewById(R.id.imageView);
//        imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
//        Picasso.with(getApplicationContext())
//                .load(url)
//                .into(imageView);
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
