package com.polybiastudios.photosweep.view;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.polybiastudios.photosweep.R;

/**
 * Created by dallascharter on 30/03/16.
 */
public class TintedImageButtonGry extends ImageButton {
    public TintedImageButtonGry(Context context) {
        super(context);
    }

    public TintedImageButtonGry(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TintedImageButtonGry(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ColorFilter colorFilter = new PorterDuffColorFilter(getResources().getColor(R.color.ps_grey), PorterDuff.Mode.SRC_ATOP);
        setColorFilter(colorFilter);
    }
}
