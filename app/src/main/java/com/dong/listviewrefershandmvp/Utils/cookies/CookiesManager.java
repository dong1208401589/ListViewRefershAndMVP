package com.dong.listviewrefershandmvp.Utils.cookies;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by jinbao on 16-4-20.
 */
public class CookiesManager  implements CookieJar {
    private static PersistentCookieStore cookieStore ;
    //new PersistentCookieStore(getApplicationContext());
    public static CookiesManager cookiesManager;

    private  CookiesManager(Context ctx){
        if(cookieStore==null){
            cookieStore = new PersistentCookieStore(ctx.getApplicationContext());
        }
    }

    public static CookiesManager getInstance(Context ctx){
        if (cookiesManager == null) {
            synchronized (CookiesManager.class) {
                if (cookiesManager == null) {
                    cookiesManager = new CookiesManager(ctx);
                }
            }
        }
          return cookiesManager;
    }
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
