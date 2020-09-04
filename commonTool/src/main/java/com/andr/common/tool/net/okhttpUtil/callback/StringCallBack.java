package com.andr.common.tool.net.okhttpUtil.callback;


import java.io.IOException;

import okhttp3.Response;

public abstract class StringCallBack extends Callback
{
    public StringCallBack()
    {
        super();
    }

    @Override
    public String onParse(Response response, String tag) throws IOException
    {
        if (response.body() == null)
        {
            return null;
        }

        return new String(response.body().bytes());
    }

    @Override
    public void onResponse(String tag, Object object)
    {
        onResponse(tag,(String)object);
    }

    public abstract void onResponse(String tag, String res);
}

