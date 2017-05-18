package com.rxjava;

import android.app.Application;

/**
 * @author: xiaxueyi
 * @date: 2017-05-18
 * @time: 15:44
 * @说明:
 */

public class AppLoader extends Application {

    private static AppLoader mInstance=null;

    public static AppLoader getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        mInstance=this;
        super.onCreate();
    }
}
