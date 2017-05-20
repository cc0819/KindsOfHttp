package com.kindofhttp.cc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kindofhttp.cc.entity.MovieEmtity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class MainActivity extends AppCompatActivity {

    private OkHttpClient okHttpClient;

    @BindView(R.id.show_text)
    TextView showText;
//    @BindView(R.id.okhttp_get)
//    Button okhttpGet;
//    @BindView(R.id.okhttp_post)
//    Button okhttpPost;
//    @BindView(R.id.retrofit_get)
//    Button retrofitGet;
//    @BindView(R.id.retrofit_post)
//    Button retrofitPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        okHttpClient = new OkHttpClient.Builder().readTimeout(50, java.util.concurrent.TimeUnit.SECONDS).connectTimeout(50, java.util.concurrent.TimeUnit.SECONDS).writeTimeout(50, java.util.concurrent.TimeUnit.SECONDS).build();


    }


    @OnClick({R.id.okhttp_get, R.id.okhttp_post, R.id.retrofit_get, R.id.retrofit_post})
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
            default:
                break;
        }


    }

    private void okHttpGetLoad() {
        String url = "http://www.json.cn/";
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("event","请求失败了");
            }

            @Override
            public void onResponse(String response, int id) {
                showText.setText(response);
            }


            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                Log.e("event","请求开始了");
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                Log.e("event","请求结束了");
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
                    public void onError(Call call, Exception e, int id) {
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

        MovieService movieService = retrofit.create(MovieService.class);
        retrofit2.Call<MovieEmtity> call =movieService.getTopMovie(0,10);
        call.enqueue(new Callback<MovieEmtity>() {
            @Override
            public void onResponse(retrofit2.Call<MovieEmtity> call, Response<MovieEmtity> response) {
                Log.e("retrofit","请求成功了");
                showText.setText(response.body().getTitle());
            }

            @Override
            public void onFailure(retrofit2.Call<MovieEmtity> call, Throwable t) {
                Log.e("retrofit","请求失败了");
            }


        });
//        call.cancel();


    }

    private void retrofitPostLoad() {



    }


    public  interface  MovieService{
        @GET("top250")
        retrofit2.Call<MovieEmtity> getTopMovie(@Query("start") int start,@Query("count") int count);


    }


}
