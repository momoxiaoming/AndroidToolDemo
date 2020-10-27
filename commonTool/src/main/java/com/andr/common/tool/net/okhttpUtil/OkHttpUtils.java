package com.andr.common.tool.net.okhttpUtil;


import com.andr.common.tool.net.okhttpUtil.builder.DownFileBuilder;
import com.andr.common.tool.net.okhttpUtil.builder.FileBuilder;
import com.andr.common.tool.net.okhttpUtil.builder.GetBuilder;
import com.andr.common.tool.net.okhttpUtil.builder.PostBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/27
 *     desc  : new class
 * </pre>
 */
public class OkHttpUtils
{

    private static OkHttpUtils okHttpUtils;
    private OkHttpClient okHttpClient;
    private int defultConnectTimeOut = 30000;
    private int defultWriteTimeOut = 20000;
    private int defultReadTimeout = 15000;


    public OkHttpUtils(OkHttpClient client)
    {
        if (client == null)
        {
//            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
//            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder()
                     .retryOnConnectionFailure(true)
//                    .addNetworkInterceptor(logInterceptor)   //打印请求的信息,缺点是会拖慢下载速度
                    .connectTimeout(defultConnectTimeOut, TimeUnit.MILLISECONDS)
                    .readTimeout(defultWriteTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(defultReadTimeout, TimeUnit.MILLISECONDS)
                    .build();
        } else
        {
            okHttpClient = client;
        }

    }

    public static OkHttpUtils getInstance()
    {
        return OkHttpUtils.setOkHttpClient(null);
    }

    private static OkHttpUtils setOkHttpClient(OkHttpClient client)
    {
        if (OkHttpUtils.okHttpUtils == null)
        {
            OkHttpUtils.okHttpUtils = new OkHttpUtils(client);
        }
        return OkHttpUtils.okHttpUtils;
    }

    /**
     * 取消tag标识请求
     *
     * @param tag
     */
    public void cancelTag(String tag)
    {

        if (tag == null) return;
        for (Call call : OkHttpUtils.getInstance().getOkHttpClient().dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for (Call call : getOkHttpClient().dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }

    /**
     * 取消tag标识请求
     *
     * @param tag
     */
    public boolean hasTag(String tag)
    {

        if (tag == null) return false;
        for (Call call : OkHttpUtils.getInstance().getOkHttpClient().dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                return true;
            }
        }
        for (Call call : getOkHttpClient().dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                return true;
            }
        }
        return false;
    }

    public static PostBuilder post()
    {
        return new PostBuilder();
    }

    public static GetBuilder get()
    {
        return new GetBuilder();
    }

    public static FileBuilder uploadFile()
    {
        return new FileBuilder();
    }

    public static DownFileBuilder downFile()
    {
        return new DownFileBuilder();
    }

    public OkHttpClient getOkHttpClient()
    {
        return okHttpClient;
    }


}
