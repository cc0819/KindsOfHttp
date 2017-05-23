package com.kindofhttp.cc.rerxutils;

import com.kindofhttp.cc.entity.MovieEntityRX;
import com.kindofhttp.cc.entity.UserInfo;
import com.kindofhttp.cc.entity.WeekDayEntiy;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 接口类
 */
public interface BaseApiService {


    public static final String Base_URL = "https://api.douban.com/v2/movie/";
//    public static final String Base_URL = "http://api.smith-compass-service.avcdata.com/";

    public static final String Base_URL_USERINFO = "http://106.15.40.67/";




    @GET("top250")
    Observable<BaseResponseEntity<MovieEntityRX>> getTopMovie(@Query("start") int start, @Query("count") int count);


    @POST("api/selectInfo/getDateSelectCategory")
    Observable<WeekDayEntiy> getWeekDay(@Body WeekDayEntiy route);


    @POST("home/test/guanguan")
    Observable<BaseResponseEntity<UserInfo>> getUserInfo();


    /**
     *普通写法
     */
    @GET("service/getIpInfo.php")
    Observable<BaseResponseEntity<UserInfo>> getData(@Query("ip") String ip);


    @GET("{url}")
    Observable<BaseResponseEntity<Object>> executeGet(
            @Path("url") String url,
            @QueryMap Map<String, String> maps
    );


    @POST("{url}")
    Observable<ResponseBody> executePost(
            @Path("url") String url,
            //  @Header("") String authorization,
            @QueryMap Map<String, String> maps);

    @POST("{url}")
    Observable<ResponseBody> json(
            @Path("url") String url,
            @Body RequestBody jsonStr);

    @Multipart
    @POST("{url}")
    Observable<ResponseBody> upLoadFile(
            @Path("url") String url,
            @Part("image\"; filename=\"image.jpg") RequestBody requestBody);

    @POST("{url}")
    Call<ResponseBody> uploadFiles(
            @Path("url") String url,
            @Path("headers") Map<String, String> headers,
            @Part("filename") String description,
            @PartMap() Map<String, RequestBody> maps);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

}
