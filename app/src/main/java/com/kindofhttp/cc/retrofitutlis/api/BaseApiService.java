package com.kindofhttp.cc.retrofitutlis.api;

import com.kindofhttp.cc.entity.Email;
import com.kindofhttp.cc.entity.MovieEntityRX;
import com.kindofhttp.cc.entity.WeekDayEntiy;
import com.kindofhttp.cc.retrofitutlis.base.BaseEntity;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface BaseApiService {

//    public static final String Base_URL = "https://api.douban.com/v2/movie/";
//    public static final String Base_URL = "http://api.smith-compass-service.avcdata.com/";

    public static final String Base_URL = "http://api.smith-management-service.avcdata.com/";




    @GET("top250")
    Observable<BaseEntity<MovieEntityRX>> getTopMovie(@Query("start") int start, @Query("count") int count);


    @POST("api/selectInfo/getDateSelectCategory")
    Observable<WeekDayEntiy> getWeekDay(@Body WeekDayEntiy route);


    @POST("userInfo/sendVerifyByMail")
    Observable<WeekDayEntiy> getEmail(@Body Email route);


}
