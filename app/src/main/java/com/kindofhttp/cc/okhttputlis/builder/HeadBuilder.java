package com.kindofhttp.cc.okhttputlis.builder;


import com.kindofhttp.cc.okhttputlis.OkHttpUtils;
import com.kindofhttp.cc.okhttputlis.request.OtherRequest;
import com.kindofhttp.cc.okhttputlis.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
