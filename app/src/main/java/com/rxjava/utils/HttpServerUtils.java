package com.rxjava.utils;

import com.rxjava.interfac.HttpUrlInterface;

/**
 * @author: xiaxueyi
 * @date: 2017-05-19
 * @time: 16:32
 * @说明: 网络请求工具类
 */

public class HttpServerUtils {
    private static HttpUrlInterface mHttpUrlInterface=null; //对象接口

    /**
     *
     * @param baseUrl
     * @return
     */
    public static HttpUrlInterface getInstance(String baseUrl){

        if(!baseUrl.endsWith("/")){
            StringBuilder builder=new StringBuilder();
            builder.append(baseUrl);
            builder.append("/");
            baseUrl=builder.toString();
        }
        if(mHttpUrlInterface==null){
            //获取Retrofit对象
            mHttpUrlInterface=RetrofitUtils.init(baseUrl).create(HttpUrlInterface.class);
        }
        return mHttpUrlInterface;
    }

}
