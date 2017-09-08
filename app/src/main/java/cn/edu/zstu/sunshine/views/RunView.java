package cn.edu.zstu.sunshine.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * 自定义用于显示阳光长跑时间的环形进度条
 * Created by CooLoongWu on 2017-9-6 16:16.
 */

public class RunView extends View {

    private float centerX;  //中心X坐标
    private float centerY;  //中心Y坐标
    private int ringStrokeWidth = 16;   //圆环宽度
    private float diameter = 500;       //圆环直径
    private float startAngle = 135;     //圆环起始角度
    private float sweepAngle = 270;     //圆环总的角度
    private float currentAngle = 200;     //当前角度

    private Paint ringBackgroundPaint;  //圆环背景画笔
    private Paint ringProgressPaint;    //圆环进度画笔

    private RectF ringBackgroundRect;   //圆环所在矩形

    private ValueAnimator progressAnimator;//进度动画

    private int ringBackgroundColor = Color.parseColor("#10FFFFFF");     //圆环背景颜色
    private int ringProgressColor = Color.argb(66, 146, 204, 255);  //圆环进度颜色
    private int[] colors = new int[]{Color.parseColor("#501C6AC9"), Color.parseColor("#1C6AC9")};

    private Matrix rotateMatrix;
    private SweepGradient sweepGradient;

    private String title = "累计奔跑";
    private double runTime = 0;
    private String note = "小时";
    private Paint titlePaint;
    private Paint valuePaint;
    private Paint notePaint;
    private int titleSize = dp2px(14);
    private int valueSize = dp2px(36);
    private int noteSize = dp2px(12);

    public RunView(Context context) {
        this(context, null);
    }

    public RunView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RunView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //圆环背景
        ringBackgroundPaint = new Paint();
        ringBackgroundPaint.setAntiAlias(true);     //消除锯齿
        ringBackgroundPaint.setStyle(Paint.Style.STROKE);//绘制空心圆
        ringBackgroundPaint.setStrokeWidth(ringStrokeWidth);
        ringBackgroundPaint.setColor(ringBackgroundColor);
        ringBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);//设置末端为圆弧

        //圆环进度
        ringProgressPaint = new Paint();
        ringProgressPaint.setAntiAlias(true);
        ringProgressPaint.setStyle(Paint.Style.STROKE);
        ringProgressPaint.setStrokeWidth(ringStrokeWidth);
        ringProgressPaint.setColor(Color.GREEN);
        ringProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        //标题
        titlePaint = new Paint();
        titlePaint.setTextSize(titleSize);
        titlePaint.setAntiAlias(true);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setColor(Color.parseColor("#ffffff"));

        //长跑时间
        valuePaint = new Paint();
        valuePaint.setTextSize(valueSize);
        valuePaint.setAntiAlias(true);
        valuePaint.setTextAlign(Paint.Align.CENTER);
        valuePaint.setColor(Color.parseColor("#fcb813"));

        //单位
        notePaint = new Paint();
        notePaint.setTextSize(noteSize);
        notePaint.setAntiAlias(true);
        notePaint.setTextAlign(Paint.Align.CENTER);
        notePaint.setColor(Color.parseColor("#80ffffff"));


        int top = 50;
        //得到圆环直径
        diameter = 3 * getScreenWidth() / 7;
        centerX = getScreenWidth() / 2;
        centerY = diameter / 2 + top;

        //圆环所在矩形
        ringBackgroundRect = new RectF();
        ringBackgroundRect.left = centerX - diameter / 2;
        ringBackgroundRect.top = top;
        ringBackgroundRect.right = centerX + diameter / 2;
        ringBackgroundRect.bottom = top + diameter;

        rotateMatrix = new Matrix();
        sweepGradient = new SweepGradient(centerX, centerY, colors, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getScreenWidth();
        int height = (int) diameter + 50;
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制圆环背景
        canvas.drawArc(ringBackgroundRect, startAngle, sweepAngle, false, ringBackgroundPaint);    //绘制圆弧，从左下角开始，绘制120度，不显示半径连线

        //绘制圆进度环渐变色
        rotateMatrix.setRotate(130, centerX, centerY);
        sweepGradient.setLocalMatrix(rotateMatrix);
        ringProgressPaint.setShader(sweepGradient);
        canvas.drawArc(ringBackgroundRect, startAngle, currentAngle, false, ringProgressPaint);    //绘制圆弧，从左下角开始，绘制120度，不显示半径连线

        //绘制标题
        canvas.drawText(title, centerX, centerY - 2 * valueSize / 3, titlePaint);

        //绘制运动时间
        canvas.drawText(String.valueOf(runTime), centerX, centerY + valueSize / 3, valuePaint);

        //绘制单位
        canvas.drawText(note, centerX, centerY + 2 * valueSize / 3, notePaint);
    }

    /**
     * 为进度设置动画
     *
     * @param last
     * @param current
     */
    public void setAnimation(float last, float current, int length) {
        progressAnimator = ValueAnimator.ofFloat(last, current);
        progressAnimator.setDuration(length);
        progressAnimator.setTarget(currentAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngle = (float) animation.getAnimatedValue();
                //curValues = currentAngle / k;
            }
        });
        progressAnimator.start();
    }

    /**
     * @return dp转px
     */
    private int dp2px(float dpValue) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f * (dpValue >= 0 ? 1 : -1));
    }

    /**
     * @return 屏幕宽度
     */
    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}

