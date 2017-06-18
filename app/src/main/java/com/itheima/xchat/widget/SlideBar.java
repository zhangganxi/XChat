package com.itheima.xchat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hyphenate.util.DensityUtil;
import com.itheima.xchat.R;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.widget
 *  @文件名:   SlideBar
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/9 11:37
 *  @描述：    TODO
 */
public class SlideBar extends View {
    private static final String TAG = "SlideBar";

    private static final String[] SECTIONS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
            , "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Paint mPaint;

    private float mTextHight;
    private float mBaseLine;

    private int mCurreIndex;

    public SlideBar(Context context) {
        this(context,null);
    }

    public SlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(DensityUtil.sp2px(getContext(),12));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(getResources().getColor(R.color.textgray));
    }

    //布局完成之后调用的方法
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTextHight = getHeight() * 1.0f/ SECTIONS.length;
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        float drawTextHight = metrics.descent - metrics.ascent;
        mBaseLine = mTextHight / 2 +(drawTextHight / 2 - metrics.descent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0;i < SECTIONS.length; i++){
            float x = getWidth() / 2;
            float y = mBaseLine + mTextHight * i;
            canvas.drawText(SECTIONS[i],x,y,mPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int index = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(Color.GRAY);
                index = (int) (event.getY() / mTextHight);

                if (mOnSlideIndexListener != null){
                    mOnSlideIndexListener.onIndexChange(SECTIONS[index]);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                index = (int) (event.getY() / mTextHight);
                System.out.println(index);
                if (mOnSlideIndexListener != null && index != mCurreIndex && index >= 0 && index <= 25){
                    mOnSlideIndexListener.onIndexChange(SECTIONS[index]);
                }
                mCurreIndex = index;
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.TRANSPARENT);
                if (mOnSlideIndexListener != null){
                    mOnSlideIndexListener.onIndexChangeEnd();
                }
                break;
            default:

                break;
        }
        return true;

    }

    private OnSlideIndexListener mOnSlideIndexListener;

    public interface OnSlideIndexListener{
        void onIndexChange(String index);
        void onIndexChangeEnd();
    }

    public void setOnSlideIndexListener(OnSlideIndexListener onSlideIndexLisetener){
        this.mOnSlideIndexListener  = onSlideIndexLisetener;
    }
}
