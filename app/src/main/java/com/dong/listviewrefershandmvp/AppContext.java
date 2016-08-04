package com.dong.listviewrefershandmvp;


import com.dong.listviewrefershandmvp.api.Config;
import com.dong.listviewrefershandmvp.base.BaseApplication;
import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        //facebook 抓包工具
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    private void initGson(){
        gson=new GsonBuilder().setDateFormat(Config.GSON_DATA_FORMAT).create();
    }

}
