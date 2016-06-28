package com.dong.listviewrefershandmvp.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by dong1 on 2016/6/27.
 */
public class MoveImageView extends ImageView {
    public MoveImageView(Context context) {
        super(context);
    }

    public MoveImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private final static String TAG="MoveImageView";
    //分别记录上次滑动的坐标
    private int mLastX=0;
    private int mLastY=0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //获得手指当前坐标
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                break;

            case MotionEvent.ACTION_MOVE:{
                int delataX=x-mLastX;
                int delataY=y-mLastY;
                Log.i(TAG,"move,delaX:"+delataX+" delaY:"+delataY);
                float translationX= getTranslationX()+delataX;
                float translationY= getTranslationY()+delataY;
                setTranslationX(translationX);
                setTranslationY(translationY);
            }
            default:
                break;
        }

        mLastX=x;
        mLastY=y;

        return true;
    }
}
