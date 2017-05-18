package com.rxjava.interfac;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author: xiaxueyi
 * @date: 2017-05-18
 * @time: 14:38
 * @说明:
 */

public interface HttpUrlInterface {

    @GET
    public Observable<ResponseBody> getDownLoadImage(@Url String imageUrl);//使用get 方法请求

    @POST
    public Observable<ResponseBody> postDownLoadImage(@Url String imageUrl);//使用post 方法请求

}
