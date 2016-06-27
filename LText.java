package com.liyiheng.pwdgenerator;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 2个字与3个字对齐:"用户名"与"密码"对齐
 * _______试图封装文字对齐的TextView,未果;此TextView仅限一种场景
 * Created by liyiheng on 16/6/27.
 */
public class LText extends TextView {

    public LText(Context context) {
        super(context);
        //String text = this.getText().toString();
    }

    public LText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        TextPaint paint = getPaint();
//        paint.setColor(getCurrentTextColor());
//        paint.drawableState = getDrawableState();
//        mViewWidth = getMeasuredWidth();
//        String text = (String) getText();
//        float textWidth = paint.measureText(text);
//        if (textWidth < mViewWidth) {
//            int length = text.length();
//            float padding = (mViewWidth - textWidth - (textWidth / length)) / (length - 1);
//            //float padding = (mViewWidth - textWidth)/(text.length()-2);
//            for (int i = 0; i < length; i++) {
//                canvas.drawText(String.valueOf(text.charAt(i)), (textWidth / length + padding) * i, getTextSize(), paint);
//            }
//        } else {
//            //canvas.drawText(text, 0, getTextSize(), paint);
//            super.onDraw(canvas);
//        }
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        String text = (String) getText();
        int length = text.length();
        if (length<2){
            super.onDraw(canvas);
            return;
        }
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();

        float viewWidth = getMeasuredWidth();
        float textWidth = paint.measureText(text);
        float charWidth = textWidth / length;
        float spacing = (viewWidth - textWidth) / (length - 1);
        for (int i=0;i<length;i++){
            canvas.drawText(String.valueOf(text.charAt(i)), (charWidth+spacing)*i, getTextSize(), paint);
        }
    }
//        if (textWidth < viewWidth) {
//            if (length == 2) {
//                float padddd = (viewWidth - charWidth * 3) / 2;
//                canvas.drawText(String.valueOf(text.charAt(0)), padddd, getTextSize(), paint);
//                canvas.drawText(String.valueOf(text.charAt(1)), padddd + textWidth, getTextSize(), paint);
//            }
//        }
    /*

    ImplementationA: 可能未对其:第一个字符与最后一个字符距TextView边缘有一定距离
    字数差距越大,这个距离差距就越大,因此是每个字符均匀分散
    CharWidth = TextWidth/CharNum;// 字符宽
    CharSpace = ViewWidth/CharNum;// 每个字符平均宽度
    CharPadding = (CharSpace – CharWidth)/2;
    绘制第一个字符:
	    始于CharPadding
    绘制第二个字符:
	    始于CharPadding + CharSpace
    绘制第三个字符:
	    始于CharPadding + CharSpace*2
    绘制第n个字符
	    始于CharPadding + CharSpace*(n-1)
------------------------------------------------------------------
    ImplementationB:第一个字符与最后一个字符紧贴TextView边缘
    CharWidth = TextWidth/CharNum;// 字符宽
    间距  = (ViewWidth – TextWidth)/(CharNum – 1);

    绘制第一个字符:
	    始于0
    绘制第二个字符:
	    始于 CharWidth + 间距
    绘制第三个字符:
	    始于 (CharWidth + 间距)*2
    绘制第n个字符
	    始于(CharWidth + 间距)*2*(n-1)
     */
}
