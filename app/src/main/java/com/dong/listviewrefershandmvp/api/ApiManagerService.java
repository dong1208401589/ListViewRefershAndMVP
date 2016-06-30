package com.dong.listviewrefershandmvp.api;

import com.dong.listviewrefershandmvp.bean.GankData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/5/25.
 */
public interface ApiManagerService {

    /**
     * 找妹子、Android、iOS、前端、扩展资源、休息视频 all
     * @param type 数据类型
     * @param size 数据个数
     * @param page 第几页
     * @return Observable<GankData>
     */
   @GET("data/{type}/{size}/{page}")
    Observable<GankData> getData(@Path("type") String type,@Path("size") int size,@Path("page")
                                 int page);

}

