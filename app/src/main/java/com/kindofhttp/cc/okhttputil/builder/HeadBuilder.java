package com.kindofhttp.cc.okhttputil.builder;


import com.kindofhttp.cc.okhttputil.OkHttpUtils;
import com.kindofhttp.cc.okhttputil.request.OtherRequest;
import com.kindofhttp.cc.okhttputil.request.RequestCall;

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
