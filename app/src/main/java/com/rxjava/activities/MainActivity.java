package com.rxjava.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rxjava.R;
import com.rxjava.interfac.HttpUrlInterface;
import com.rxjava.utils.HttpServerUtils;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView mTitle;

    private Button mAction;

    private ImageView mImage=null;

    private String imageUrl="http://pic.qiantucdn.com/58pic/22/06/55/57b2d9a265f53_1024.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle=(TextView)super.findViewById(R.id.title) ;
        mAction=(Button)super.findViewById(R.id.action) ;
        mImage=(ImageView)super.findViewById(R.id.image) ;
        mAction.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action:
                initImage();
                break;
        }
    }


    /**
     * 这是没有进行封装的RxJava
     */
    private void initImage2(){
        String baseUrl=imageUrl;
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl+"/") //请求的路径 必须以"/"结束
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   //添加RXJava
                .addConverterFactory(GsonConverterFactory.create()) //返回的格式可以是一个json
                .build();


        //获取下载的服务对象
        HttpUrlInterface apiServer=retrofit.create(HttpUrlInterface.class);

        //获取被观察者对象
        Observable<ResponseBody> observable=apiServer.getDownLoadImage(baseUrl);

        observable.subscribeOn(Schedulers.newThread())    //在新的线程实现该方法
                .map(new Func1<ResponseBody, Object>() {

                    @Override
                    public Object call(ResponseBody responseBody) {
                        return responseBody;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())  //在主线程中显示
                .subscribe(new Subscriber<Object>() {

                    @Override
                    public void onCompleted() {
                        Log.v("MainActivity","success");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("MainActivity",e.toString());
                    }

                    @Override
                    public void onNext(Object object) {
                        ResponseBody responseBody=(ResponseBody)object;
                        InputStream is=responseBody.byteStream();
                        Bitmap bitMap= BitmapFactory.decodeStream(is);
                        mImage.setImageBitmap(bitMap);
                    }
                });
    }

    /**
     * 把RxJava 封装成一个工具类
     */
    private void initImage(){
        HttpServerUtils.getInstance(imageUrl)
                .getDownLoadImage(imageUrl)
                .subscribeOn(Schedulers.newThread())    //在新的线程里面执行下载操作
                .map(new Func1<ResponseBody, Object>() {
                    @Override
                    public Object call(ResponseBody responseBody) {
                        return responseBody;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())  //在主线程显示
                .subscribe(new Subscriber<Object>(){

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object object) {
                        ResponseBody responseBody=(ResponseBody)object;
                        InputStream is=responseBody.byteStream();
                        Bitmap bitMap= BitmapFactory.decodeStream(is);
                        mImage.setImageBitmap(bitMap);
                    }
                });

    }

}
