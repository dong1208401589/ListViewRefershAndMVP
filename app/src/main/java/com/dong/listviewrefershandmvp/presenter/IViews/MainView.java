package com.dong.listviewrefershandmvp.presenter.IViews;

import com.dong.listviewrefershandmvp.base.mvp.MvpView;
import com.dong.listviewrefershandmvp.bean.BaseGankData;
import com.dong.listviewrefershandmvp.bean.GankData;

import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 */
public interface MainView extends MvpView {

    void onGetDataSuccess(List<BaseGankData> data,boolean refresh);
}
