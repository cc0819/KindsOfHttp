package com.kindofhttp.cc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kindofhttp.cc.entity.Email;
import com.kindofhttp.cc.entity.MovieEntity;
import com.kindofhttp.cc.entity.MovieEntityRX;
import com.kindofhttp.cc.entity.WeekDayEntiy;
import com.kindofhttp.cc.okhttputlis.OkHttpUtils;
import com.kindofhttp.cc.okhttputlis.callback.StringCallback;
import com.kindofhttp.cc.retrofitutlis.RetrofitUtil;
import com.kindofhttp.cc.retrofitutlis.base.BaseSubscriber;
import com.kindofhttp.cc.utlis.CustomInterceptor;
import com.kindofhttp.cc.utlis.RequestInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.show_text)
    TextView showText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.okhttp_get, R.id.okhttp_post, R.id.retrofit_get, R.id.retrofit_post,R.id.retrofitRX_get,R.id.retrofitRX_post,R.id.retrofitUtils,R.id.testemail})
    public void onClickViews(View view) {
        switch (view.getId()) {
            case R.id.okhttp_get:
                okHttpGetLoad();
                break;
            case R.id.okhttp_post:
                okHttpPostLoad();
                break;
            case R.id.retrofit_get:
                retrofitGetLoad();
                break;
            case R.id.retrofit_post:
                retrofitPostLoad();
                break;
            case R.id.retrofitRX_get:
                retrofitRxGetLoad();
                break;
            case R.id.retrofitRX_post:
                retrofitRxPostLoad();
                break;
            case R.id.retrofitUtils:
                retrofitRxUtilsGET();
//                retrofitRxUtilsPOST();
                break;
            case R.id.testemail:
                testEmailPost();
                break;
            default:
                break;
        }


    }

    private void okHttpGetLoad() {
        String url = "http://www.json.cn/";
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                Log.e("event","请求失败了");
            }

            @Override
            public void onResponse(String response, int id) {
                showText.setText(response);
            }



        });

    }

    private void okHttpPostLoad() {
        String url = "http://api.smith-compass-service.avcdata.com/api/selectInfo/getDateSelectCategory";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lastWeekCount",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils
                .postString()
                .url(url)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        Log.e("event","请求失败了");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        showText.setText(response);
                    }
                });


    }

    private void retrofitGetLoad() {
        String url = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieServiceOfRetrofitGET movieService = retrofit.create(MovieServiceOfRetrofitGET.class);
        retrofit2.Call<MovieEntity> call =movieService.getTopMovie(0,10);
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(retrofit2.Call<MovieEntity> call, Response<MovieEntity> response) {
                Log.e("retrofit","请求成功了");
                showText.setText(response.body().getTitle());
            }

            @Override
            public void onFailure(retrofit2.Call<MovieEntity> call, Throwable t) {
                Log.e("retrofit","请求失败了");
            }


        });
//        call.cancel();


    }

    private void retrofitPostLoad() {
        String url ="http://api.smith-compass-service.avcdata.com/";
        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(initOKhTTP())
                .build();
        WeekDayRetrofitPOST weekDayRetrofitPOST = retrofit.create(WeekDayRetrofitPOST.class);
        WeekDayEntiy weekDayEntiy = new WeekDayEntiy();
        weekDayEntiy.setLastWeekCount(0);
        Call<WeekDayEntiy> call = weekDayRetrofitPOST.getWeekDay(weekDayEntiy);
        call.enqueue(new Callback<WeekDayEntiy>() {
            @Override
            public void onResponse(Call<WeekDayEntiy> call, Response<WeekDayEntiy> response) {
                Log.e("retrofit","请求成功了"+response);
                showText.setText(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<WeekDayEntiy> call, Throwable t) {
                Log.e("retrofit","请求失败了"+t.toString());
            }

        });

    }

    private OkHttpClient initOKhTTP() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
//                        .addHeader("Content-Type","application/json")
                        .build();

                return chain.proceed(request);
            }
        })
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new CustomInterceptor())
                .addInterceptor(new RequestInterceptor())
                .connectTimeout(1000,TimeUnit.MILLISECONDS)
                .build();

        return httpClient;

    }


    private void retrofitRxGetLoad(){
        String url = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(initOKhTTP())
                .build();
        MovieServiceOfRXGET movieServiceOfRXGET = retrofit.create(MovieServiceOfRXGET.class);
        movieServiceOfRXGET.getTopMovie(0,10)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieEntity>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.e("retrofit","请求开始了");
                    }

                    @Override
                    public void onCompleted() {
                        Log.e("retrofit","请求完成了");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("retrofit","请求失败了"+e.toString());
                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        Log.e("retrofit","请求成功了--"+movieEntity.getTitle());
                        showText.setText(movieEntity.getTitle());
                    }
                });

    }

    private void retrofitRxPostLoad(){
        String url ="http://api.smith-compass-service.avcdata.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(initOKhTTP())
                .build();
        WeekDayOfRXPOST weekDayOfRXPOST = retrofit.create(WeekDayOfRXPOST.class);
        WeekDayEntiy weekDayEntiy = new WeekDayEntiy();
        weekDayEntiy.setLastWeekCount(0);
        weekDayOfRXPOST.getWeekDay(weekDayEntiy)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeekDayEntiy>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.e("retrofit","请求开始了");
                    }

                    @Override
                    public void onCompleted() {
                        Log.e("retrofit","请求完成了");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("retrofit","请求失败了"+e.toString());
                    }

                    @Override
                    public void onNext(WeekDayEntiy weekDayEntiy) {
                        Log.e("retrofit","请求成功了--"+weekDayEntiy.getMessage());
                        showText.setText(weekDayEntiy.getMessage());
                    }
                });

    }


    private void retrofitRxUtilsGET() {
        RetrofitUtil.getInstance().getTopMovie(0,10, new BaseSubscriber<MovieEntityRX>(this,true) {

            @Override
            protected void onSuccees(MovieEntityRX movieEntityRX) throws Exception {
                Log.e("retrofit","请求成功了11--"+movieEntityRX.getCount());
                Log.e("retrofit","请求成功了11--"+movieEntityRX.getTitle());
                Log.e("retrofit","请求成功了11--"+movieEntityRX.toString());
                Toast.makeText(MainActivity.this,"--MovieEntityRX-"+movieEntityRX,Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                Log.e("retrofit","请求失败了111"+e.toString());
                Log.e("retrofit","请求失败了111"+isNetWorkError);
            }
        });

    }



    private void retrofitRxUtilsPOST() {
        WeekDayEntiy weekDayEntiy = new WeekDayEntiy();
        weekDayEntiy.setLastWeekCount(0);
        RetrofitUtil.getInstance().getWeekDay(weekDayEntiy, new BaseSubscriber<WeekDayEntiy>(this,true) {

            @Override
            protected void onSuccees(WeekDayEntiy weekDayEntiy) throws Exception {
                Log.e("retrofit","请求成功了11--"+weekDayEntiy.getMessage());
                Log.e("retrofit","请求成功了11--"+weekDayEntiy.getReturnValue().get(0));
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                Log.e("retrofit","请求失败了111"+e.toString());
            }
        });





    }



    private void testEmailPost() {
        Email weekDayEntiy = new Email();
        weekDayEntiy.setEmail("1186669460@qq.com");
        RetrofitUtil.getInstance().getEmail(weekDayEntiy, new BaseSubscriber<Email>(this,true) {

            @Override
            protected void onSuccees(Email email) throws Exception {
                Log.e("retrofit","请求成功了11--"+email.toString());
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                Log.e("retrofit","请求失败了111"+e.toString());
            }
        });






    }









    public  interface  MovieServiceOfRetrofitGET{
        @GET("top250")
        Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

    }


    public  interface  WeekDayRetrofitPOST{
        @POST("api/selectInfo/getDateSelectCategory")
        Call<WeekDayEntiy> getWeekDay(@Body WeekDayEntiy route);
    }


    public  interface  MovieServiceOfRXGET{
        @GET("top250")
        Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
    }


    public  interface  WeekDayOfRXPOST{
        @POST("api/selectInfo/getDateSelectCategory")
        Observable<WeekDayEntiy> getWeekDay(@Body WeekDayEntiy route);
    }


}
