package cn.edu.zstu.sunshine.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.orhanobut.logger.Logger;

/**
 * 旋转式拖动条（单指针和双指针模式）
 * Created by CooLoongWu on 2017-11-13 14:28.
 */

public class RotarySeekBar extends View {

    private float centerX;  //圆心X
    private float centerY;  //圆心Y

    //day：星期
    //ordinal：课程节数
    //week：课程周数

    private int backCircleColor = Color.parseColor("#EDEDED");
    private int mainCircleColor = Color.parseColor("#FFFFFF");
    private int indicatorColor = Color.parseColor("#FFA036");//指示器的颜色
    private int progressUnfocusedColor = Color.parseColor("#EDEDED");//进度条没有焦点时的颜色
    private int progressDefaultColor = Color.parseColor("#FFA036");//进度条默认的颜色
    private int progressAccentColor = Color.parseColor("#95E362");//进度条选中时的颜色

    private Paint backCirclePaint;
    private Paint mainCirclePaint;
    private Paint indicatorPaint;
    private Paint progressUnfocusedPaint;
    private Paint progressDefaultPaint;
    private Paint progressAccentPaint;

    RectF rectF = new RectF();

    private float progressRadius = 180;

    public RotarySeekBar(Context context) {
        super(context);
        init();
    }

    public RotarySeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RotarySeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        backCirclePaint = new Paint();
        backCirclePaint.setAntiAlias(true);
        backCirclePaint.setColor(backCircleColor);
        backCirclePaint.setStyle(Paint.Style.FILL);

        mainCirclePaint = new Paint();
        mainCirclePaint.setAntiAlias(true);
        mainCirclePaint.setColor(mainCircleColor);
        mainCirclePaint.setStyle(Paint.Style.FILL);

        indicatorPaint = new Paint();
        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setStrokeWidth(4);
        indicatorPaint.setStyle(Paint.Style.STROKE);
        indicatorPaint.setColor(indicatorColor);

        progressUnfocusedPaint = new Paint();
        progressUnfocusedPaint.setAntiAlias(true);
        progressUnfocusedPaint.setStrokeWidth(1);
        progressUnfocusedPaint.setStyle(Paint.Style.STROKE);
        progressUnfocusedPaint.setColor(progressUnfocusedColor);

        progressDefaultPaint = new Paint();
        progressDefaultPaint.setAntiAlias(true);
        progressDefaultPaint.setStrokeWidth(2);
        progressDefaultPaint.setStyle(Paint.Style.STROKE);
        progressDefaultPaint.setColor(progressDefaultColor);

        progressAccentPaint = new Paint();
        progressAccentPaint.setAntiAlias(true);
        progressAccentPaint.setStrokeWidth(2);
        progressAccentPaint.setStyle(Paint.Style.STROKE);
        progressAccentPaint.setColor(progressAccentColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        centerX = canvas.getWidth() / 2;
        centerY = canvas.getHeight() / 2;

        canvas.drawCircle(centerX, centerY, progressRadius - 100, backCirclePaint);
        canvas.drawCircle(centerX, centerY, progressRadius - 110, mainCirclePaint);

        drawDay(180, 7, canvas);
        drawOrdinal(190, 12, canvas);
        drawWeek(200, 12, canvas);

    }


    /**
     * 画星期的刻度盘
     *
     * @param sweepAngle 总角度
     * @param dialCount  刻度数量
     * @param canvas     画布
     */
    private void drawDay(int sweepAngle, int dialCount, Canvas canvas) {
        /*
         * 假设一个圆的圆心坐标是(a,b)，半径为r，则圆上每个点的
         * X坐标=a + Math.sin(2*Math.PI / 360) * r ；
         * Y坐标=b + Math.cos(2*Math.PI / 360) * r ；
         */

        //每个刻度的圆心X
        float x;
        //每个刻度的圆心Y
        float y;
        //刻度间的角度偏移量（注意-1，因为10个刻度间有9段距离）
        float angleOffset = sweepAngle / (dialCount - 1);

        //星期刻度盘的半径
        float radius = progressRadius - 70;

        //角度是参考竖直方向最下端，逆时针方向绘制
        for (int i = 0; i < dialCount; i++) {
            float angle = angleOffset * i + (360 - sweepAngle) / 2;
            x = centerX + (float) (radius * Math.sin(2 * Math.PI / 360 * angle));
            y = centerY + (float) (radius * Math.cos(2 * Math.PI / 360 * angle));

            Logger.e("绘制星期角度：" + angle);
            canvas.drawCircle(x, y, 6, progressDefaultPaint);
//            canvas.drawPoint(x, y, indicatorPaint);

            if (i == dialCount - 1) {
                drawIndicator(angle, canvas);
            }
        }

    }

    private void drawIndicator(float angle, Canvas canvas) {
        float length = 50;//指示器线的长度
        float endX, endY;
        endX = centerX + (float) (length * Math.sin(2 * Math.PI / 360 * angle));
        endY = centerY + (float) (length * Math.cos(2 * Math.PI / 360 * angle));

        canvas.drawLine(centerX, centerY, endX, endY, indicatorPaint);
    }

    /**
     * 画一天课程节数的刻度盘
     *
     * @param sweepAngle 总角度
     * @param dialCount  刻度数量
     * @param canvas     画布
     */
    private void drawOrdinal(int sweepAngle, int dialCount, Canvas canvas) {
        drawDial(progressRadius - 30, sweepAngle, dialCount, canvas);
    }

    /**
     * 画周数的刻度盘
     *
     * @param sweepAngle 总角度
     * @param dialCount  刻度数量
     * @param canvas     画布
     */
    private void drawWeek(int sweepAngle, int dialCount, Canvas canvas) {
        drawDial(progressRadius, sweepAngle, dialCount, canvas);
    }

    /**
     * 画刻度线以及进度条
     *
     * @param radius     半径
     * @param sweepAngle 横扫角度
     * @param dialCount  刻度数量
     * @param canvas     画布
     */
    private void drawDial(float radius, int sweepAngle, int dialCount, Canvas canvas) {
        //课程节数刻度盘的半径
        rectF.left = centerX - radius;
        rectF.top = centerY - radius;
        rectF.right = centerX + radius;
        rectF.bottom = centerY + radius;
        //startAngle：从横向最右侧起始点顺时针方向度量的角度
        //sweepAngle：顺时针方向度量的角度
        canvas.drawArc(rectF, 90 + (360 - sweepAngle) / 2, sweepAngle, false, progressUnfocusedPaint);

        //绘制刻度线
        float length = 8;//刻度线的长度
        float startX, startY, endX, endY;
        //刻度间的角度偏移量（必须强制转换为float，否则精度丢失！！）
        float angleOffset = (float) sweepAngle / (dialCount - 1);
        //角度是参考竖直方向最下端，逆时针方向（5个刻度需要有6个刻度线）
        for (int i = 0; i < dialCount; i++) {
//            if (i % 3 == 0 || i == 0) {
//                indicatorPaint.setStrokeWidth(2);
//                length = 12;
//            } else {
//                indicatorPaint.setStrokeWidth(1);
//                length = 8;
//            }
            float angle = angleOffset * i + (360 - sweepAngle) / 2;
            startX = centerX + (float) (radius * Math.sin(2 * Math.PI / 360 * angle));
            startY = centerY + (float) (radius * Math.cos(2 * Math.PI / 360 * angle));
            endX = centerX + (float) ((radius - length) * Math.sin(2 * Math.PI / 360 * angle));
            endY = centerY + (float) ((radius - length) * Math.cos(2 * Math.PI / 360 * angle));

            canvas.drawLine(startX, startY, endX, endY, progressUnfocusedPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float downAngle;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                float dx = event.getX() - centerX;
                float dy = event.getY() - centerY;
                //这个角度是从最右侧开始，逆时针到-180度，顺时针到180度
                downAngle = (float) ((Math.atan2(dy, dx) * 180) / Math.PI);
                if (downAngle > 0) {
                    downAngle = 360 - downAngle;
                } else {
                    downAngle = Math.abs(downAngle);
                }
                Logger.e("按下时的角度：" + downAngle);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
