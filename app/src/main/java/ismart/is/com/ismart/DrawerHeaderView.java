package ismart.is.com.ismart;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class DrawerHeaderView extends FrameLayout {
    public DrawerHeaderView(Context context) {
        super(context);
        setup();
    }

    public DrawerHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ImageView user_image = (ImageView) findViewById(R.id.user_image);

        Picasso.with(context)
                .load("http://vignette4.wikia.nocookie.net/fallout/images/0/02/Sam-worthington-natalie-mark-03.jpg/revision/latest?cb=20100803091735")
                .transform(new RoundedTransformation(50, 4))
                .resize(100, 100)
                .into(user_image);
        setup();
    }

    private void setup() {
        inflate(getContext(), R.layout.navigation_header, this);
    }
}
