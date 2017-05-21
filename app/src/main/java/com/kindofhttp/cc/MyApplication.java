package com.kindofhttp.cc;

import android.app.Application;

import com.kindofhttp.cc.okhttputlis.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by cc on 17/5/20.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttp();
    }

    private void initOkHttp() {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10000L, TimeUnit.SECONDS)
                .readTimeout(10000L,TimeUnit.SECONDS).build();
        OkHttpUtils.initClient(client);

    }
}
