package com.dong.listviewrefershandmvp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.dong.listviewrefershandmvp.base.BaseApplication;

/**
 * Created by Administrator on 2016/6/15.
 */
public class TDevice {
    public static boolean hasInternet(){
        boolean flag;
        if(((ConnectivityManager)BaseApplication.context().getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo()!=null) {
            flag = true;
        }
            else
            flag=false;

        return flag;
    }
}
