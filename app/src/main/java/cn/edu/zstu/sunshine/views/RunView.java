package cn.edu.zstu.sunshine.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义用于显示阳光长跑时间的环形进度条
 * Created by CooLoongWu on 2017-9-6 16:16.
 */

public class RunView extends View {

    private Context context;

    private Paint ringBackgroundPaint;
    private Paint ringProgressPaint;

    private int ringStrokeWidth = 10; //圆环宽度

    private int ringBackgroundColor = Color.rgb(146, 204, 255);
    private int ringProgressColor = Color.argb(66, 146, 204, 255);

    public RunView(Context context) {
        this(context, null);
    }

    public RunView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RunView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        init();
    }

    private void init() {
        ringBackgroundPaint = new Paint();
        ringBackgroundPaint.setAntiAlias(true);     //消除锯齿
        ringBackgroundPaint.setStyle(Paint.Style.STROKE);//绘制空心圆
        ringBackgroundPaint.setStrokeWidth(ringStrokeWidth);
        ringBackgroundPaint.setColor(ringBackgroundColor);

        ringProgressPaint = new Paint();
        ringProgressPaint.setAntiAlias(true);
        ringProgressPaint.setStyle(Paint.Style.STROKE);
        ringProgressPaint.setStrokeWidth(ringStrokeWidth);
        ringProgressPaint.setColor(ringProgressColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        int radius = dp2px(context, 88);    //圆环半径
        int width = dp2px(context, 5);      // 圆环宽度

        RectF oval = new RectF();
        oval.left = center - radius;
        oval.top = 100;
        oval.right = center + radius;
        oval.bottom = 100 + radius * 2;
        canvas.drawArc(oval, -225, 270, false, ringBackgroundPaint);    //绘制圆弧，从左下角开始，绘制120度，不显示半径连线

        super.onDraw(canvas);
    }

    /**
     * dp转px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
