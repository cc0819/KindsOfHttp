package com.kindofhttp.cc.utlis;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cc on 17/5/21.
 */

public class RequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("User-Agent", "app-name")
                .header("Cache-Control", "max-age=640000")
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }

}
