package com.andr.common.tool.net.okhttpUtil.request;

import android.os.Handler;
import android.os.Looper;

import com.andr.common.tool.net.okhttpUtil.OkHttpUtils;
import com.andr.common.tool.net.okhttpUtil.callback.BeanCallback;
import com.andr.common.tool.net.okhttpUtil.callback.Callback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/27
 *     desc  : new class
 * </pre>
 */
public class HttpRequestCall
{
    private Call call;


    private HttpRequest httpRequest;
    private Request request;

    private long defaultTimeOut;
    private long readTimeOut;
    private long conTimeOut;
    private long writeTimeOut;
    private boolean isOnMainThread;  //默认是回调到主线程


    public HttpRequestCall(HttpRequest httpRequest, long defaultTimeOut, long readTimeOut,
                           long conTimeOut, long writeTimeOut, boolean isOnMainThread)
    {
        this.httpRequest = httpRequest;
        this.defaultTimeOut = defaultTimeOut;
        this.readTimeOut = readTimeOut;
        this.conTimeOut = conTimeOut;
        this.writeTimeOut = writeTimeOut;
        this.isOnMainThread = isOnMainThread;
    }

    /**
     * 回调到主线程
     * 貌似OkHttpClient 并非需要强制单例
     *
     * @param callBack
     */
    public void execute(final Callback callBack)
    {
        this.request =
                this.httpRequest.onRequest(this.httpRequest.onRequestBody(this.httpRequest.onRequestBody(), callBack));
        if (request == null)
        {
            callBack.onFailure(httpRequest.getTag(), new Exception("请求对象为空!"), "请求对象为空!");
            return;
        }
        if (this.conTimeOut > 0 || this.readTimeOut > 0 || this.writeTimeOut > 0)
        {
            //newBuilder 会和单例共享线程池,防止了OkHttpClient过多党总支哦
            OkHttpClient.Builder builder = OkHttpUtils.getInstance().getOkHttpClient().newBuilder();
            if (conTimeOut <= 0)
            {
                conTimeOut = this.defaultTimeOut;
            }

            if (readTimeOut <= 0)
            {
                readTimeOut = this.defaultTimeOut;
            }

            if (writeTimeOut <= 0)
            {
                writeTimeOut = this.defaultTimeOut;
            }

            builder.connectTimeout(conTimeOut, TimeUnit.MILLISECONDS);
            builder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
            builder.writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS);


            OkHttpClient client = builder.build();

            this.call = client.newCall(this.request);
        } else
        {
            this.call = OkHttpUtils.getInstance().getOkHttpClient().newCall(this.request);
        }


        final int id = httpRequest.getId();
        final String tag = httpRequest.getTag();

        call.enqueue(new okhttp3.Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {

                try
                {
                    throw e;
                }  catch (Exception e1)
                {
                    e1.printStackTrace();
                    String msg=e.getMessage();
                    if(msg.equals("Canceled")|| msg.equals("Socket closed")|| msg.equals("Socket is closed")){
                        sendFailure(tag, e, "连接取消", callBack);
                    }else if(msg.contains("No space left on device")){
                        sendFailure(tag, e1, "磁盘空间不足", callBack);
                    }else{
                        sendFailure(tag, e1, "您的网络不可用,请检查网络", callBack);
                    }

                }

            }

            @Override
            public void onResponse(Call call, Response response)
            {
                try
                {
                    if (call.isCanceled())
                    {
                        sendFailure(tag, new IOException("request is canceled"), "请求已取消", callBack);
                    } else if (response.isSuccessful())
                    {
                        sendSucess(tag, callBack.onParse(response, tag), callBack);
                    } else
                    {
                        sendFailure(tag,
                                new IOException("request failed ,response code is " + response.code()), "您的网络不可用,请检查网络", callBack);

                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                    if (e.getMessage().contains("No space left on device"))
                    {
                        sendFailure(tag, e, "磁盘空间不足", callBack);
                    } else if (e.getMessage().equals("Socket closed") || e.getMessage().equals(
                            "Socket is closed"))
                    {
                        sendFailure(tag, e, "连接取消", callBack);
                    } else
                    {
                        sendFailure(tag, e, "您的网络不可用,请检查网络", callBack);
                    }
                } finally
                {
                    if (response.body() != null)
                    {
                        response.close();
                    }
                }


            }
        });
    }


    public void execute(Class<?> cls, BeanCallback callBack)
    {
        if (callBack != null)
        {
            callBack.setClazz(cls);
        }
        this.execute(callBack);
    }


    /**
     * 回到到主线程
     */
    public void sendFailure(final String tag, final Exception exep, final String errmsg,
                            final Callback callback)
    {

        if (isOnMainThread)
        {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    callback.onFailure(tag, exep, errmsg);
                }
            });
        } else
        {
            callback.onFailure(tag, exep, errmsg);
        }

    }

    /**
     * 回到到主线程
     */
    public void sendSucess(final String tag, final Object rlt, final Callback callBack)
    {
        if (isOnMainThread)
        {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    callBack.onResponse(tag, rlt);
                }
            });
        } else
        {
            callBack.onResponse(tag, rlt);
        }

    }
}
