package com.dong.listviewrefershandmvp.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.dong.listviewrefershandmvp.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/6/16.
 */
public abstract class BaseSwipeRefreshLayoutActivity extends BaseToolbarActivity {

    @BindView(R.id.swipe_refresh_layout) protected SwipeRefreshLayout mSwipeRefreshLayout;

    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;
    public static final int STATE_NOMORE = 3;
    public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
    public static int mState = STATE_NONE;

    /**
     * 当前activity彻底运行起来之后调用
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initSwipRefreshLayout();
    }

    public void initSwipRefreshLayout(){
        if(mSwipeRefreshLayout!=null){
            mSwipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
            //mSwipeRefreshLayout.setOnRefreshListener(()->onSwipeRefresh());
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    onSwipeRefresh();
                }
            });
        }
    }

    public abstract void onSwipeRefresh();


    public boolean isRefreshStatus() {
        return refreshStatus;
    }

    public void setRefreshStatus(boolean refreshStatus) {
        this.refreshStatus = refreshStatus;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {

    }
}
