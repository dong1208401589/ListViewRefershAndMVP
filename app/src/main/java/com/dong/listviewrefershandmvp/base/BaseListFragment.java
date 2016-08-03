package com.dong.listviewrefershandmvp.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


import com.dong.listviewrefershandmvp.R;
import com.dong.listviewrefershandmvp.adapter.base.ListBaseAdapter;
import com.dong.listviewrefershandmvp.bean.ListEntity;
import com.dong.listviewrefershandmvp.ui.views.EmptyLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


@SuppressLint("NewApi")
public abstract class BaseListFragment<T> extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener,
        OnScrollListener {

    public static final String BUNDLE_KEY_CATALOG = "BUNDLE_KEY_CATALOG";

    @BindView(R.id.swiperefreshlayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.listview)
    protected ListView mListView;

    protected ListBaseAdapter<T> mAdapter;

    @BindView(R.id.error_layout)
    protected EmptyLayout mErrorLayout;

    protected int mStoreEmptyState = -1;

    protected int mCurrentPage = 0;

    protected int mCatalog = 1;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pull_refresh_listview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCatalog = args.getInt(BUNDLE_KEY_CATALOG, 0);
        }
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCurrentPage = 0;
                mState = STATE_REFRESH;
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                requestData(true);
            }
        });

        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(this);

        if (mAdapter != null) {
            mListView.setAdapter(mAdapter);
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        } else {
            mAdapter = getListAdapter();
            mListView.setAdapter(mAdapter);

            if (requestDataIfViewCreated()) {
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                mState = STATE_NONE;
                requestData(false);
            } else {
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }

        }
        if (mStoreEmptyState != -1) {
            mErrorLayout.setErrorType(mStoreEmptyState);
        }
    }

    @Override
    public void onDestroyView() {
        mStoreEmptyState = mErrorLayout.getErrorState();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    protected abstract ListBaseAdapter<T> getListAdapter();

    // 下拉刷新数据
    @Override
    public void onRefresh() {
        if (mState == STATE_REFRESH) {
            return;
        }
        // 设置顶部正在刷新
        mListView.setSelection(0);
        setSwipeRefreshLoadingState();
        mCurrentPage = 0;
        mState = STATE_REFRESH;
        requestData(true);
    }

    protected boolean requestDataIfViewCreated() {
        return true;
    }


    protected ListEntity<T> readList(Serializable seri) {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {}

    // 是否需要自动刷新
    protected boolean needAutoRefresh() {
        return true;
    }

    /***
     * 获取列表数据
     *
     *
     * @author 火蚁 2015-2-9 下午3:16:12
     *
     * @return void
     * @param refresh
     */
    protected void requestData(boolean refresh) {
            // 取新的数据
            sendRequestData();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    protected void sendRequestData() {}

    protected void executeOnLoadDataSuccess(List<T> data) {
        if (data == null) {
            data = new ArrayList<T>();
        }

        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        if (mCurrentPage == 0) {
            mAdapter.clear();
        }

        int adapterState = ListBaseAdapter.STATE_EMPTY_ITEM;
        if ((mAdapter.getCount() + data.size()) == 0) {
            adapterState = ListBaseAdapter.STATE_EMPTY_ITEM;
        } else if (data.size() == 0
                || (data.size() < getPageSize() && mCurrentPage == 0)) {
            adapterState = ListBaseAdapter.STATE_NO_MORE;
            mAdapter.notifyDataSetChanged();
        } else {
            adapterState = ListBaseAdapter.STATE_LOAD_MORE;
        }
        mAdapter.setState(adapterState);
        mAdapter.addData(data);

        //mAdapter.getFooterView().setVisibility(View.GONE);

        // 判断等于是因为最后有一项是listview的状态
        if (mAdapter.getCount() == 1) {

            if (needShowEmptyNoData()) {
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            } else {
                mAdapter.setState(ListBaseAdapter.STATE_EMPTY_ITEM);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 是否需要隐藏listview，显示无数据状态
     *
     * @author 火蚁 2015-1-27 下午6:18:59
     *
     */
    protected boolean needShowEmptyNoData() {
        return true;
    }

    protected int getPageSize() {
        return 20;
    }

    protected void onRefreshNetworkSuccess() {}

    protected void executeOnLoadDataError(String error) {
        if (mCurrentPage == 0) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        } else {
          //在无网络时，滚动到底部时，mCurrentPage先自加了，然而在失败时却
          //没有减回来，如果刻意在无网络的情况下上拉，可以出现漏页问题
          //find by TopJohn
            mCurrentPage--;

            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mAdapter.setState(ListBaseAdapter.STATE_NETWORK_ERROR);
            mAdapter.notifyDataSetChanged();
        }
    }

    // 完成刷新
    protected void executeOnLoadFinish() {
        setSwipeRefreshLoadedState();
        mState = STATE_NONE;
    }

    /** 设置顶部正在加载的状态 */
    protected void setSwipeRefreshLoadingState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            // 防止多次重复刷新
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    /** 设置顶部加载完毕的状态 */
    protected void setSwipeRefreshLoadedState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        }
    }



    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }
        // 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
        if (mState == STATE_LOADMORE || mState == STATE_REFRESH) {
            return;
        }
        // 判断是否滚动到底部
        boolean scrollEnd = false;
        try {
            if (view.getPositionForView(mAdapter.getFooterView()) == view
                    .getLastVisiblePosition())
                scrollEnd = true;
        } catch (Exception e) {
            scrollEnd = false;
        }

        if (mState == STATE_NONE && scrollEnd) {
            if (mAdapter.getState() == ListBaseAdapter.STATE_LOAD_MORE
                    || mAdapter.getState() == ListBaseAdapter.STATE_NETWORK_ERROR) {
                mCurrentPage++;
                mState = STATE_LOADMORE;
                requestData(false);
                mAdapter.setFooterViewLoading();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
        // if (mState == STATE_NOMORE || mState == STATE_LOADMORE
        // || mState == STATE_REFRESH) {
        // return;
        // }
        // if (mAdapter != null
        // && mAdapter.getDataSize() > 0
        // && mListView.getLastVisiblePosition() == (mListView.getCount() - 1))
        // {
        // if (mState == STATE_NONE
        // && mAdapter.getState() == ListBaseAdapter.STATE_LOAD_MORE) {
        // mState = STATE_LOADMORE;
        // mCurrentPage++;
        // requestData(true);
        // }
        // }
    }
}
