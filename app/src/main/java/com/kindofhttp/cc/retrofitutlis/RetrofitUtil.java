package com.kindofhttp.cc.retrofitutlis;

import android.content.Context;

import com.kindofhttp.cc.entity.MovieEntityRX;
import com.kindofhttp.cc.entity.WeekDayEntiy;
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
import rx.functions.Func1;
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


    public  void cancel(){
    }


    private <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }



    public void getTopMovie(int start, int count , Subscriber<BaseEntity<MovieEntityRX>> subscriber){
        mBaseApiService.getTopMovie(start,count)
                .compose(schedulersTransformer())
//                .compose(transformer())
                .subscribe(subscriber);
    }



    public void getWeekDay(WeekDayEntiy weekDayEntiy, Subscriber<WeekDayEntiy> subscriber){
        mBaseApiService.getWeekDay(weekDayEntiy)
                .compose(schedulersTransformer())
//                .compose(transformer())
                .subscribe(subscriber);
    }






    Observable.Transformer schedulersTransformer() {
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable)  observable)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }

           /* @Override
            public Observable call(Observable observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }*/
        };
    }

    public <T> Observable.Transformer<BaseEntity<T>, T> transformer() {

        return new Observable.Transformer() {

            @Override
            public Object call(Object observable) {
                return ((Observable) observable).map(new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
            }
        };
    }


    private static class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override public Observable<T> call(Throwable t) {
            return Observable.error(ExceptionHandle.handleException(t));
        }
    }



    private class HandleFuc<T> implements Func1<BaseEntity<T>, T> {
        @Override
        public T call(BaseEntity<T> response) {
            if (!response.isSuccess()) throw new RuntimeException(response.getCode() + "" + response.getMsg() != null ? response.getMsg(): "");
            return response.getData();
        }
    }


}
