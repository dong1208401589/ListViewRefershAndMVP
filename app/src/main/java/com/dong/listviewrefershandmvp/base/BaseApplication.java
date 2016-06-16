package com.dong.listviewrefershandmvp.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/6/15.
 */
public class BaseApplication extends Application {

    static Context _context;

    @Override
    public void onCreate() {
        super.onCreate();
        _context=getApplicationContext();
    }

    public static synchronized BaseApplication context(){
        return (BaseApplication) _context;
    }
}
