package com.kindofhttp.cc.retrofitutlis;

import android.content.Context;

import com.kindofhttp.cc.entity.MovieEntity;
import com.kindofhttp.cc.retrofitutlis.api.BaseApiService;
import com.kindofhttp.cc.retrofitutlis.base.BaseEntity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by cc on 17/5/22.
 * Retrofit工具类
 */

public class RetrofitUtil {
    public static final int DEFAULT_TIMEOUT = 10;

    private Retrofit mRetrofit;
    private BaseApiService mBaseApiService;

    private static RetrofitUtil mInstance;

    private static Context mContext;


    public RetrofitUtil() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request()
//                                .newBuilder()
////                        .addHeader("Content-Type","application/json")
//                                .build();
//                        return chain.proceed(request);
//                    }
//                })
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BaseApiService.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mBaseApiService = mRetrofit.create(BaseApiService.class);
    }


    public static RetrofitUtil getInstance(){
        if (mInstance == null){
            synchronized (RetrofitUtil.class){
                mInstance = new RetrofitUtil();
            }
        }
        return mInstance;
    }


    public  BaseApiService getAPiService(){
        return  mBaseApiService;
    }


    private <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }



    public void getUsers(int start, int count , Subscriber<BaseEntity<MovieEntity>> subscriber){
        mBaseApiService.getTopMovie(start,count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}