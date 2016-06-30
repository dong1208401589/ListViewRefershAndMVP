package com.dong.listviewrefershandmvp.data;

import com.dong.listviewrefershandmvp.Utils.RxUtils;
import com.dong.listviewrefershandmvp.bean.BaseGankData;
import com.dong.listviewrefershandmvp.model.DataModel;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Administrator on 2016/6/29.
 */
public class DataManager {

    private static DataManager dataManager;
    private DataModel dataModel;

    private DataManager(){
        dataModel=DataModel.getInstance();
    }

    public synchronized static DataManager getInstance(){
        if (dataManager==null){
            dataManager=new DataManager();
        }
        return dataManager;
    }

    public Observable<ArrayList<BaseGankData>> getData(String type,int size,int page){
        return dataModel.getData(type,size,page).map(gankData -> gankData.results).compose
                (RxUtils.applyIOToMainThreadSchedulers());
    }

}
