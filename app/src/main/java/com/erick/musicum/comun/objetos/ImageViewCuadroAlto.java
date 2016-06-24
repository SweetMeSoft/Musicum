package com.erick.musicum.comun.objetos;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ErickSteven on 10/1/2016.
 */
public class ImageViewCuadroAlto extends ImageView {

    public ImageViewCuadroAlto(Context context) {
        super(context);
    }

    public ImageViewCuadroAlto(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewCuadroAlto(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageViewCuadroAlto(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        setMeasuredDimension(height, height);
    }

}
