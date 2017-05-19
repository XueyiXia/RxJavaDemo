package com.rxjava.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: xiaxueyi
 * @date: 2017-05-18
 * @time: 15:35
 * @说明:
 */

public class RetrofitUtils {
    private static Retrofit retrofit=null;

    public static Retrofit init(String baseUrl){

        retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl) //请求的路径
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   //添加RXJava
                .addConverterFactory(GsonConverterFactory.create()) //返回的格式可以是一个json
                .build();

        return retrofit;
    }

}
