package com.dong.listviewrefershandmvp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/28.
 */
public class BaseGankData implements Serializable{

    //发布人
    @SerializedName("who") public String who;

    //发布时间
    @SerializedName("publishedAt") public String publishedAt;

    //标题
    @SerializedName("desc") public String desc;

    //类型
    @SerializedName("type") public String type;

    //图片url
    @SerializedName("url") public String url;

    //对象Id
    @SerializedName("ojbectId") public String objectId;

    //创建时间
    @SerializedName("createdAt") public String createdAt;

    //更新时间
    @SerializedName("updatedAt") public String updatedAt;
}
