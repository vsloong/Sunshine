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

import cn.edu.zstu.sunshine.utils.UnitUtil;

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

    private int textUnfocusedColor = Color.parseColor("#EDEDED");//文字没有焦点时的颜色
    private int textDefaultColor = Color.parseColor("#666666");//文字默认的颜色
    private int textAccentColor = Color.parseColor("#FFFFFF");//文字选中时的颜色


    private Paint backCirclePaint;
    private Paint mainCirclePaint;
    private Paint indicatorPaint;
    private Paint progressUnfocusedPaint;
    private Paint progressDefaultPaint;
    private Paint progressAccentPaint;

    private Paint textPaint;

    RectF rectF = new RectF();

    private final int TYPE_DAY = 1;
    private final int TYPE_ORDINAL = 2;
    private final int TYPE_WEEK = 3;
    private int TYPE_CURRENT = 1;

    private float dayRadius = 140;      //星期的半径
    private float ordinalRadius = 220;  //上课节数的半径
    private float weekRadius = 300;     //周数的半径
    private float supplyRadius = 40;    //补充的半径，主要为了添加触摸区域大小
    float downAngle = 0;
    float currentAngle = 0;

    private int day = 0;

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
        indicatorPaint.setStrokeWidth(2);
        indicatorPaint.setStyle(Paint.Style.FILL);
        indicatorPaint.setColor(indicatorColor);

        progressUnfocusedPaint = new Paint();
        progressUnfocusedPaint.setAntiAlias(true);
        progressUnfocusedPaint.setStrokeWidth(1);
        progressUnfocusedPaint.setStyle(Paint.Style.STROKE);
        progressUnfocusedPaint.setColor(progressUnfocusedColor);

        progressDefaultPaint = new Paint();
        progressDefaultPaint.setAntiAlias(true);
        progressDefaultPaint.setStrokeWidth(1);
        progressDefaultPaint.setStyle(Paint.Style.STROKE);
        progressDefaultPaint.setColor(progressDefaultColor);

        progressAccentPaint = new Paint();
        progressAccentPaint.setAntiAlias(true);
        progressAccentPaint.setStrokeWidth(1);
        progressAccentPaint.setStyle(Paint.Style.STROKE);
        progressAccentPaint.setColor(progressAccentColor);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textUnfocusedColor);

        float textSize = UnitUtil.dp2px(14, getContext());
        textPaint.setTextSize(textSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        centerX = canvas.getWidth() / 2;
        centerY = canvas.getHeight() / 2;

        canvas.drawCircle(centerX, centerY, dayRadius + supplyRadius, backCirclePaint);
        canvas.drawCircle(centerX, centerY, 5, indicatorPaint);
        //canvas.drawCircle(centerX, centerY, progressRadius - 140, mainCirclePaint);

        drawIndicator(180, 7, canvas);
        drawDayOfWeek(180, 7, canvas);

        drawOrdinal(190, 12, canvas);
        drawWeekOfTerm(200, 12, canvas);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Logger.e("控件的宽度：" + widthMeasureSpec);
    }

    /**
     * 画星期的刻度盘
     *
     * @param sweepAngle 总角度
     * @param dialCount  刻度数量
     * @param canvas     画布
     */
    private void drawDayOfWeek(int sweepAngle, int dialCount, Canvas canvas) {
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


        //角度是参考竖直方向最下端，逆时针方向绘制
        for (int i = 0; i < dialCount; i++) {
            float angle = angleOffset * i + (360 - sweepAngle) / 2;
            x = centerX + (float) (dayRadius * Math.sin(2 * Math.PI / 360 * angle));
            y = centerY + (float) (dayRadius * Math.cos(2 * Math.PI / 360 * angle));

            //Logger.e("绘制星期角度：" + angle);
            String dayString;
            if (i == 0) {
                dayString = "日";
            } else if (i == 1) {
                dayString = "六";
            } else if (i == 2) {
                dayString = "五";
            } else if (i == 3) {
                dayString = "四";
            } else if (i == 4) {
                dayString = "三";
            } else if (i == 5) {
                dayString = "二";
            } else if (i == 6) {
                dayString = "一";
            } else dayString = "一";

            if (TYPE_CURRENT == TYPE_DAY) {
                indicatorPaint.setColor(progressAccentColor);
            } else {
                indicatorPaint.setColor(progressUnfocusedColor);
            }

            float textWidth = textPaint.measureText(dayString);
            if (i == (6 - day)) {
                indicatorPaint.setColor(progressAccentColor);
                canvas.drawCircle(x, y, 25, indicatorPaint);
                textPaint.setColor(textAccentColor);
                canvas.drawText(dayString, x - textWidth / 2, y + textWidth / 2, textPaint);
            } else {
                //canvas.drawCircle(x, y, 25, progressDefaultPaint);
                textPaint.setColor(textDefaultColor);
                canvas.drawText(dayString, x - textWidth / 2, y + textWidth / 2, textPaint);
            }

//            canvas.drawCircle(x, y, 20, i == 6 - day ? progressAccentPaint : progressDefaultPaint);
//            canvas.drawText(String.valueOf(7 - i), x, y, i == 6 - day ? progressAccentPaint : progressDefaultPaint);

        }

    }

    private void drawIndicator(int sweepAngle, int dialCount, Canvas canvas) {

        //刻度间的角度偏移量（注意-1，因为10个刻度间有9段距离）
        float angleOffset = sweepAngle / (dialCount - 1);
        int temp = (int) ((downAngle - (360 - sweepAngle) / 2) / angleOffset);
        //Logger.e("转换的结果：" + temp);
        float angle;
        if (temp < 1) {
            day = 0;
        } else if (temp > dialCount - 1) {
            day = 6;
        } else {
            day = temp;
        }
        angle = angleOffset * (6 - day) + (360 - sweepAngle) / 2;

        float length = dayRadius;//指示器线的长度
        float endX, endY;
        endX = centerX + (float) (length * Math.sin(2 * Math.PI / 360 * angle));
        endY = centerY + (float) (length * Math.cos(2 * Math.PI / 360 * angle));

        if (TYPE_CURRENT == TYPE_DAY) {
            indicatorPaint.setColor(progressAccentColor);
        } else {
            indicatorPaint.setColor(progressUnfocusedColor);
        }

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
        if (TYPE_CURRENT == TYPE_ORDINAL) {
            drawOrdinal(ordinalRadius, sweepAngle, dialCount, canvas, progressDefaultPaint);
        } else {
            drawOrdinal(ordinalRadius, sweepAngle, dialCount, canvas, progressUnfocusedPaint);
        }

    }

    /**
     * 画周数的刻度盘
     *
     * @param sweepAngle 总角度
     * @param dialCount  刻度数量
     * @param canvas     画布
     */
    private void drawWeekOfTerm(int sweepAngle, int dialCount, Canvas canvas) {
        if (TYPE_CURRENT == TYPE_WEEK) {
            drawWeekOfTerm(weekRadius, sweepAngle, dialCount, canvas, progressDefaultPaint);
        } else {
            drawWeekOfTerm(weekRadius, sweepAngle, dialCount, canvas, progressUnfocusedPaint);
        }

    }

    /**
     * 画刻度线以及进度条
     *
     * @param radius     半径
     * @param sweepAngle 横扫角度
     * @param dialCount  刻度数量
     * @param canvas     画布
     */
    private void drawWeekOfTerm(float radius, int sweepAngle, int dialCount, Canvas canvas, Paint paint) {
        //课程节数刻度盘的半径
        rectF.left = centerX - radius;
        rectF.top = centerY - radius;
        rectF.right = centerX + radius;
        rectF.bottom = centerY + radius;
        //startAngle：从横向最右侧起始点顺时针方向度量的角度
        //sweepAngle：顺时针方向度量的角度
        canvas.drawArc(rectF, 90 + (360 - sweepAngle) / 2, sweepAngle, false, paint);

        //绘制刻度线
        float length = 8;//刻度线的长度
        float startX, startY, endX, endY;
        //刻度间的角度偏移量（必须强制转换为float，否则精度丢失！！）
        float angleOffset = (float) sweepAngle / (dialCount - 1);
        //角度是参考竖直方向最下端，逆时针方向（5个刻度需要有6个刻度线）
        for (int i = 0; i < dialCount; i++) {
            float angle = angleOffset * i + (360 - sweepAngle) / 2;
            startX = centerX + (float) (radius * Math.sin(2 * Math.PI / 360 * angle));
            startY = centerY + (float) (radius * Math.cos(2 * Math.PI / 360 * angle));
            endX = centerX + (float) ((radius - length) * Math.sin(2 * Math.PI / 360 * angle));
            endY = centerY + (float) ((radius - length) * Math.cos(2 * Math.PI / 360 * angle));

            canvas.drawLine(startX, startY, endX, endY, paint);
        }
    }

    /**
     * 画刻度线以及进度条
     *
     * @param radius     半径
     * @param sweepAngle 横扫角度
     * @param dialCount  刻度数量
     * @param canvas     画布
     */
    private void drawOrdinal(float radius, int sweepAngle, int dialCount, Canvas canvas, Paint paint) {
        //课程节数刻度盘的半径
        rectF.left = centerX - radius;
        rectF.top = centerY - radius;
        rectF.right = centerX + radius;
        rectF.bottom = centerY + radius;
        //startAngle：从横向最右侧起始点顺时针方向度量的角度
        //sweepAngle：顺时针方向度量的角度
        //canvas.drawArc(rectF, 90 + (360 - sweepAngle) / 2, sweepAngle, false, paint);

        //绘制刻度线
        float startX, startY;
        //刻度间的角度偏移量（必须强制转换为float，否则精度丢失！！）
        float angleOffset = (float) sweepAngle / (dialCount - 1);
        //角度是参考竖直方向最下端，逆时针方向（5个刻度需要有6个刻度线）
        for (int i = 0; i < dialCount; i++) {
            float angle = angleOffset * i + (360 - sweepAngle) / 2;
            startX = centerX + (float) (radius * Math.sin(2 * Math.PI / 360 * angle));
            startY = centerY + (float) (radius * Math.cos(2 * Math.PI / 360 * angle));

            canvas.drawCircle(startX, startY, 8, paint);

            canvas.save();
            canvas.rotate(sweepAngle);
            canvas.drawText(String.valueOf(12 - i), startX - 10, startY - 10, paint);
            canvas.restore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float downX = event.getX() - centerX;
        float downY = event.getY() - centerY;

        if (Math.pow(downX, 2) + Math.pow(downY, 2) < Math.pow(dayRadius + supplyRadius, 2)) {
            Logger.e("在日期选择范围内");
            TYPE_CURRENT = TYPE_DAY;
            //这个角度是从最右侧开始，逆时针到-180度，顺时针到180度
            currentAngle = (float) ((Math.atan2(downY, downX) * 180) / Math.PI);

            //转换为从最底部开始，顺时针方向
            currentAngle -= 90;
            if (currentAngle < 0) {
                currentAngle += 360;
            }
            downAngle = currentAngle;
        } else if (Math.pow(downX, 2) + Math.pow(downY, 2) < Math.pow(ordinalRadius + supplyRadius, 2)) {
            Logger.e("在上课节数选择范围内");
            TYPE_CURRENT = TYPE_ORDINAL;
        } else if (Math.pow(downX, 2) + Math.pow(downY, 2) < Math.pow(weekRadius + supplyRadius, 2)) {
            Logger.e("在周数选择范围内");
            TYPE_CURRENT = TYPE_WEEK;
        }
        invalidate();

//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                float downX = event.getX() - centerX;
//                float downY = event.getY() - centerY;
//
//                if (Math.pow(downX, 2) + Math.pow(downY, 2) < Math.pow(dayRadius + supplyRadius, 2)) {
//                    Logger.e("在日期选择范围内");
//
//                    //这个角度是从最右侧开始，逆时针到-180度，顺时针到180度
//                    currentAngle = (float) ((Math.atan2(downY, downX) * 180) / Math.PI);
//
//                    //转换为从最底部开始，顺时针方向
//                    currentAngle -= 90;
//                    if (currentAngle < 0) {
//                        currentAngle += 360;
//                    }
//                    downAngle = currentAngle;
//                } else if (Math.pow(downX, 2) + Math.pow(downY, 2) < Math.pow(ordinalRadius + supplyRadius, 2)) {
//                    Logger.e("在上课节数选择范围内");
//                } else if (Math.pow(downX, 2) + Math.pow(downY, 2) < Math.pow(weekRadius + supplyRadius, 2)) {
//                    Logger.e("在周数选择范围内");
//                }
//                invalidate();
//
//                //downAngle = 360 - downAngle;
//
//                //Logger.e("按下时的角度：" + downAngle);
//                //invalidate();
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float moveX = event.getX() - centerX;
//                float moveY = event.getY() - centerY;
//
//                if (Math.pow(moveX, 2) + Math.pow(moveY, 2) < Math.pow(dayRadius + supplyRadius, 2)) {
//                    currentAngle = (float) ((Math.atan2(moveY, moveX) * 180) / Math.PI);
//                    currentAngle -= 90;
//                    if (currentAngle < 0) {
//                        currentAngle += 360;
//                    }
//                    //currentAngle = 360 - currentAngle;
//                    downAngle = currentAngle;
//                }
//
//
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            default:
//                break;
//        }
        return true;
    }
}
