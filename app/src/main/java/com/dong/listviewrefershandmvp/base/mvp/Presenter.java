package com.dong.listviewrefershandmvp.base.mvp;

/**
 * Created by Administrator on 2016/6/29.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachVew();
}
