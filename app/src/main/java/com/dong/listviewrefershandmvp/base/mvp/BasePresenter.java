package com.dong.listviewrefershandmvp.base.mvp;

import com.dong.listviewrefershandmvp.data.DataManager;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/6/29.
 */
public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;
    public CompositeSubscription mCompostiteSubscription;
    public DataManager mDataManager;

    @Override
    public void attachView(T mvpView) {
        this.mMvpView=mvpView;
        this.mCompostiteSubscription=new CompositeSubscription();
        this.mDataManager=DataManager.getInstance();
    }

    @Override
    public void detachVew() {
        this.mMvpView=null;
        this.mCompostiteSubscription.unsubscribe();
        this.mCompostiteSubscription=null;
        this.mDataManager=null;
    }

    public boolean isViewAttached(){
        return mMvpView!=null;
    }

    public T getMvpView(){
        return mMvpView;
    }


    public void checkViewAttached(){
        if(!isViewAttached()) throw new MvpViewNotAttachedExcepteion();
    }

    public static class MvpViewNotAttachedExcepteion extends RuntimeException{
        public MvpViewNotAttachedExcepteion(){
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

}
