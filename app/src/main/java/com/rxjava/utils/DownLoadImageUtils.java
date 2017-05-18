package com.rxjava.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author: xiaxueyi
 * @date: 2017-05-18
 * @time: 11:31
 * @说明: 图片下载工具类
 */

public class DownLoadImageUtils {

    private static final String TAG="DownLoadImageUtils";

    public static final Bitmap getImageBitMap(String imageUrl){
        Bitmap bitmap=initNet(imageUrl);
        return bitmap;
    }


    /**
     * 初始化网络
     * @param imageUrl
     * @return
     */
    private static Bitmap initNet(String imageUrl){
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        try{
            URL url=new URL(imageUrl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.connect();

            if(connection.getResponseCode()==200){
                InputStream inputStream=connection.getInputStream();
                bitmap= BitmapFactory.decodeStream(inputStream);
            }

            return bitmap;
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }finally {
            if(connection!=null){
                connection.disconnect();
            }
        }

        return null;
    }
}
