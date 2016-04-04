package com.mncomunity1;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mncomunity1.R;
import com.squareup.picasso.Picasso;


public class DrawerHeaderView extends FrameLayout {
    PrefManager pref;
    public DrawerHeaderView(Context context) {
        super(context);
        setup();
        ImageView user_image = (ImageView) findViewById(R.id.user_image);
        TextView username = (TextView) findViewById(R.id.username);
        pref = IsmartApp.getPrefManagerPaty();
        Picasso.with(context)
                .load("http://vignette4.wikia.nocookie.net/fallout/images/0/02/Sam-worthington-natalie-mark-03.jpg/revision/latest?cb=20100803091735")
                .transform(new RoundedTransformation(50, 4))
                .resize(100, 100)
                .into(user_image);
    }

    public DrawerHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setup();
    }

    private void setup() {
        inflate(getContext(), R.layout.navigation_header, this);

    }
}
