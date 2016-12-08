package com.liyiheng.cc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liyiheng on 2016/12/8.
 */

public class CircleProgress extends View {
    public CircleProgress(Context context) {
        super(context);
        init();
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mPaint;
    private RectF rectF;

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(0xFFFF0000);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mLoopWidth);


        rectF = new RectF();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        int doubleWidth = mLoopWidth * 2;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {

            int desired = getPaddingLeft() + getPaddingRight() + doubleWidth;
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int desired = getPaddingTop() + doubleWidth + getPaddingBottom();
            height = desired;
        }

//---------------------------------------------
        setMeasuredDimension(width, height);
//---------------------------------------------


        if (width >= height) {
            int i = width - height;
            i /= 2;
            rectF.set(i + mLoopWidth, mLoopWidth, width - i - mLoopWidth, height - mLoopWidth);
        } else {
            int i = height - width;
            i /= 2;
            rectF.set(mLoopWidth, i + mLoopWidth, width - mLoopWidth, height - i - mLoopWidth);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //canvas.drawArc(rectF, 0, 60, true, mPaint);

        int degree = progressCurrent * 360 / progressMax;
        mPaint.setColor(colorPrimary);
        canvas.drawArc(rectF, mStartAngle, degree, false, mPaint);
        mPaint.setColor(colorSecondary);
        canvas.drawArc(rectF, mStartAngle + degree, 360 - degree, false, mPaint);

    }

    private int progressMax = 100;
    private int progressCurrent = 30;

    private float mStartAngle = -90;

    private int colorPrimary = 0xFFFF0000;
    private int colorSecondary = 0xFFEEEEEE;
    private int mLoopWidth = 15;

    public void setProgressColor(int colorPrimary, int colorSecondary) {
        this.colorPrimary = colorPrimary;
        this.colorSecondary = colorSecondary;
        invalidate();
    }

    public void setCircleWidth(int strokeWidth) {
        mLoopWidth = strokeWidth;
        int height = getHeight();
        int width = getWidth();
        if (width >= height) {
            int i = width - height;
            i /= 2;
            rectF.set(i + mLoopWidth, mLoopWidth, width - i - mLoopWidth, height - mLoopWidth);
        } else {
            int i = height - width;
            i /= 2;
            rectF.set(mLoopWidth, i + mLoopWidth, width - mLoopWidth, height - i - mLoopWidth);
        }
        mPaint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        this.progressMax = maxProgress < 1 ? 1 : maxProgress;
        invalidate();
    }

    public void setProgress(int progress) {
        this.progressCurrent = progress > progressMax ? progressMax : progress;
        invalidate();
    }


}
