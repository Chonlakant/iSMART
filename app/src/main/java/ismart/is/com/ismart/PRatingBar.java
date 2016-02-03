package ismart.is.com.ismart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.SeekBar;

public class PRatingBar extends SeekBar {

    private int starNumber = 5;
    private float space;
    private int rating;
    private boolean firstTime = true;

    public PRatingBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        _init();
    }

    public PRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init();
    }

    public PRatingBar(Context context) {
        super(context);
        _init();

    }

    /**
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the number of stars
     * 
     * @param starNumber
     */
    public void setStarNumber(int starNumber) {
        this.starNumber = starNumber;
    }

    private void _init() {
        setThumb(null);
        setOnSeekBarChangeListener(sbcl);
        post(new Runnable() {

            @Override
            public void run() {
                setProgress((int) space);
            }
        });
    }

    OnSeekBarChangeListener sbcl = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            rating = (int) (getProgress() / space) + 1;
            setProgress((int) (rating * space));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                boolean fromUser) {
        }
    };

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        setMax(getWidth());
        canvas.clipPath(calculateStarPath());

        // TODO C'mon you can do better than that
        if (firstTime) {
            setProgress((int) space);
            firstTime = false;
        }

        super.onDraw(canvas);

    }

    private Path calculateStarPath() {
        Path path = new Path();

        space = getWidth() / starNumber;
        float centerY = (getHeight() / 2f) - (space / 2f);
        float step = space / 10;
        float startX;
        for (int i = 0; i < starNumber; i++) {
            startX = i * space;

            path.moveTo(startX + (step * 5), centerY + 0);

            path.lineTo(startX + (step * 3.3f), centerY + (step * 3.3f));

            path.lineTo(startX, centerY + (step * 4));

            path.lineTo(startX + (step * 2.3f), centerY + (step * 6.3f));

            path.lineTo(startX + +(step * 2), centerY + space);

            path.lineTo(startX + (step * 5), centerY + (step * 8.1f));

            path.lineTo(startX + +(step * 8), centerY + space);

            path.lineTo(startX + (step * 7.6f), centerY + (step * 6.3f));

            path.lineTo(startX + (step * 10), centerY + (step * 4));

            path.lineTo(startX + (step * 6.7f), centerY + (step * 3.3f));

            path.close();
        }
        return path;
    }

}