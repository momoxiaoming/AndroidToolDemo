package com.andr.common.tool.net.okhttpUtil.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

public abstract class BitmapCallBack extends Callback
{
    public BitmapCallBack()
    {
        super();
    }

    public Bitmap onParse(Response response, String tag)
    {
        if (response.body() == null)
        {
            return null;
        }

        return BitmapFactory.decodeStream(response.body().byteStream());
    }

    public abstract void onResponse(int id, Bitmap bitmap);

    @Override
    public void onResponse(String tag, Object object)
    {
        onResponse(tag, (Bitmap) object);
    }
}

