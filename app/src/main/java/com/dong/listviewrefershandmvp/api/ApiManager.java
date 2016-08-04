package com.dong.listviewrefershandmvp.api;

import com.dong.listviewrefershandmvp.AppContext;
import com.dong.listviewrefershandmvp.BuildConfig;
import com.dong.listviewrefershandmvp.Utils.persistentcookiejar.ClearableCookieJar;
import com.dong.listviewrefershandmvp.Utils.persistentcookiejar.PersistentCookieJar;
import com.dong.listviewrefershandmvp.Utils.persistentcookiejar.cache.SetCookieCache;
import com.dong.listviewrefershandmvp.Utils.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/6/30.
 */
public class ApiManager {

    private static ApiManager ourInstance;

    private ApiManagerService apiManagerService;

    public static ApiManager getInstance() {
        if (ourInstance == null)
            ourInstance = new ApiManager();
        return ourInstance;
    }

    private ApiManager() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);

        if(BuildConfig.islog){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        builder.addNetworkInterceptor(new StethoInterceptor());
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor((AppContext.getInstance().getApplicationContext())));

        builder.cookieJar(cookieJar);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(AppContext.getInstance().gson))
                .client(builder.build()).build();
        apiManagerService = retrofit.create(ApiManagerService.class);
    }

    public ApiManagerService getApiManagerService(){
        return apiManagerService;
    }

}
