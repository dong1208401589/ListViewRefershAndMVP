package com.dong.listviewrefershandmvp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/28.
 */
public class Error implements Serializable {

    @SerializedName("error") public boolean error;

    @SerializedName("msg") public boolean msg;
}
