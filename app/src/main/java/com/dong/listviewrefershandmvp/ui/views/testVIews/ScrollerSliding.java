package com.dong.listviewrefershandmvp.ui.views.testVIews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Scroller;

/**
 * Created by dong1 on 2016/6/29.
 */
public class ScrollerSliding extends ImageView {

    private Context mContext;

    public ScrollerSliding(Context context) {
        super(context);
        init(context);
    }

    public ScrollerSliding(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollerSliding(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context){
        this.mContext=context;
        mScroller=new Scroller(context);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //获得手指当前坐标
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                smoothScrollTo(x,0);
                break;

            case MotionEvent.ACTION_MOVE:{

            }
            default:
                break;
        }

        return true;
    }
    Scroller mScroller;

    private void smoothScrollTo(int destX,int destY){
        int scrollX=getScrollX();
        int deltaX = destX-scrollX;
        mScroller.startScroll(scrollX,0,deltaX,0,1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}
