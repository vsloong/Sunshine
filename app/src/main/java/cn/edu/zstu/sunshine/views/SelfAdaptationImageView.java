package cn.edu.zstu.sunshine.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 宽度填充满屏幕，高度自适应的ImageView
 * Created by CooLoongWu on 2017-9-13 17:13.
 */

public class SelfAdaptationImageView extends AppCompatImageView {
    public SelfAdaptationImageView(Context context) {
        super(context);
    }

    public SelfAdaptationImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfAdaptationImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d != null) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            //高度 根据 屏幕宽度 计算而得
            int height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
