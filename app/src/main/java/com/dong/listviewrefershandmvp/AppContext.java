package com.dong.listviewrefershandmvp;

import android.app.Application;

import com.dong.listviewrefershandmvp.api.Config;
import com.dong.listviewrefershandmvp.base.BaseApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by dong1 on 2016/6/18.
 */
public class AppContext extends Application{

    private static AppContext ourInstance=new AppContext();

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
    }

    private void initGson(){
        gson=new GsonBuilder().setDateFormat(Config.GSON_DATA_FORMAT).create();
    }

}
