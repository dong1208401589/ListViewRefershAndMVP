package com.dong.listviewrefershandmvp.model;

import com.dong.listviewrefershandmvp.api.ApiManager;
import com.dong.listviewrefershandmvp.bean.GankData;
import com.dong.listviewrefershandmvp.data.DataManager;
import com.dong.listviewrefershandmvp.model.impl.IDataModel;

import rx.Observable;

/**
 * Created by Administrator on 2016/6/30.
 */
public class DataModel implements IDataModel {

    private static final DataModel ourInstance = new DataModel();

    public static DataModel getInstance() {
        return ourInstance;
    }

    private DataModel() {

    }

    @Override
    public Observable<GankData> getData(String type, int size, int page) {
        return ApiManager.getInstance().getApiManagerService().getData(type, size, page);
    }
}
