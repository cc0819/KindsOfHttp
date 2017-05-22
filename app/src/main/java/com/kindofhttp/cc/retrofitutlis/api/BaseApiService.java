package com.kindofhttp.cc.retrofitutlis.api;

import com.kindofhttp.cc.entity.MovieEntityRX;
import com.kindofhttp.cc.retrofitutlis.base.BaseEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface BaseApiService {

    public static final String Base_URL = "https://api.douban.com/v2/movie/";

    @GET("top250")
    Observable<BaseEntity<MovieEntityRX>> getTopMovie(@Query("start") int start, @Query("count") int count);

}
