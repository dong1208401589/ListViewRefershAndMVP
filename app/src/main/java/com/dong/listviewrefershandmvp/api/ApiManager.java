package com.dong.listviewrefershandmvp.api;

import com.dong.listviewrefershandmvp.AppContext;

import okhttp3.OkHttpClient;
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

        OkHttpClient okHttpClient = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(AppContext.getInstance().gson))
                .client(okHttpClient).build();
        apiManagerService = retrofit.create(ApiManagerService.class);
    }

    public ApiManagerService getApiManagerService(){
        return apiManagerService;
    }

}
