package ismart.is.com.ismart.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ismart.is.com.ismart.Constant;
import ismart.is.com.ismart.R;
import ismart.is.com.ismart.adapter.AllCourseRecyclerAdapter;
import ismart.is.com.ismart.adapter.CourseListviewAdapter;
import ismart.is.com.ismart.event.ActivityResultBus;
import ismart.is.com.ismart.event.ApiBus;
import ismart.is.com.ismart.event.IsoRequestedEvent;
import ismart.is.com.ismart.event.LogisiticsRequestedEvent;
import ismart.is.com.ismart.event.LogisticsReceivedEvent;
import ismart.is.com.ismart.event.MaintenanceRequestedEvent;
import ismart.is.com.ismart.event.ManagementRequestedEvent;
import ismart.is.com.ismart.event.ProductionRequestedEvent;
import ismart.is.com.ismart.event.PurchaseRequestedEvent;
import ismart.is.com.ismart.event.QualityRequestedEvent;
import ismart.is.com.ismart.event.SafetyRequestedEvent;
import ismart.is.com.ismart.event.SaleRequestedEvent;
import ismart.is.com.ismart.model.Post;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

public class PhotoActivity extends AppCompatActivity {


    String urlImage;
    ImageViewTouch imageView;
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
        String url = Constant.enPointh + urlImage;
        imageView = (ImageViewTouch) findViewById(R.id.imageView);
        imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        Picasso.with(getApplicationContext())
                .load(url)
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
