package com.dong.listviewrefershandmvp;

import android.app.Application;

import com.anupcowkur.reservoir.Reservoir;
import com.dong.listviewrefershandmvp.api.Config;
import com.dong.listviewrefershandmvp.base.BaseApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

/**
 * Created by dong1 on 2016/6/18.
 */
public class AppContext extends BaseApplication{

    private static AppContext ourInstance=new AppContext();
    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = ONE_KB * 1024L;
    public static final long CACHE_DATA_MAX_SIZE = ONE_MB * 3L;
    public boolean log=true;

    public Gson gson;

    public static AppContext getInstance(){
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance=this;
        initGson();
        initReservoir();
    }

    private void initGson(){
        gson=new GsonBuilder().setDateFormat(Config.GSON_DATA_FORMAT).create();
    }

    private void initReservoir(){
        try {
            Reservoir.init(context(),CACHE_DATA_MAX_SIZE,gson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
