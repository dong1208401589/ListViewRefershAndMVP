package com.dong.listviewrefershandmvp.model.impl;

import com.dong.listviewrefershandmvp.bean.GankData;

import rx.Observable;


/**
 * Created by Administrator on 2016/6/30.
 */
public interface IDataModel {

    Observable<GankData> getData(String type, int size, int page);

}
