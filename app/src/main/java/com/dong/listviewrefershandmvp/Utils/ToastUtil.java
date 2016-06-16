package com.dong.listviewrefershandmvp.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by chenwendong on 2016/5/27.
 */
public class ToastUtil {
    private static Toast mToast;
    private static void showToast(Context context,String msg,int duration){
        if(mToast==null){
            mToast=new Toast(context);
        }
        mToast.setText(msg);
        mToast.setDuration(duration);
        mToast.show();
    }
    private static void showToast(Context context,int resId,int duration){
        if(mToast==null){
            mToast=new Toast(context);
        }
        mToast.setText(resId);
        mToast.setDuration(duration);
        mToast.show();
    }

    public static void showToastShort(Context context,String text){
        showToast(context,text,Toast.LENGTH_SHORT);
    }
    public static void showToastShort(Context context,int res){
        showToast(context,res,Toast.LENGTH_SHORT);
    }
    public static void showToastLong(Context context,String text){
        showToast(context,text,Toast.LENGTH_LONG);
    }
    public static void showToastLong(Context context,int res){
        showToast(context,res,Toast.LENGTH_LONG);
    }

}
