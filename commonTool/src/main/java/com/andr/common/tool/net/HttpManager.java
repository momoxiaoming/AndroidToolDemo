package com.andr.common.tool.net;

import android.content.Context;
import android.os.Handler;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * 网络模块输出接口,采用okHttp
 * Created by zhangxiaoming on 2019/1/5.
 */

public class HttpManager
{


    private static volatile HttpManager mIntence;
    private Context context;
    private OkHttpImpl okHttpImpl;

    private Handler okHttpHandler;

    public static HttpManager getInstance(Context context)
    {
        if (null == mIntence)
        {
            synchronized (HttpManager.class)
            {
                if (null == mIntence)
                {
                    mIntence = new HttpManager(context);
                }
            }
        }
        return mIntence;
    }

    private HttpManager(Context context)
    {
        this.context = context;


        //初始化Handler
        okHttpHandler = new Handler(context.getMainLooper());

        okHttpImpl = OkHttpImpl.getInstance();

    }


    /**
     * 添加头信息
     *
     * @param map
     */
    public void addHeaders(HashMap<String, String> map)
    {
        okHttpImpl.setHeaders(map);
    }

    /**
     * POST 异步方法
     */
    public void POST_ASY(String reqUrl, String jsonParams, NetCallBack.ReqCallBack reqCallBack)
    {
        okHttpImpl.requestAsyn(reqUrl, OkHttpImpl.ReqType.TYPE_POST_JSON, jsonParams, reqCallBack);
    }


    /**
     * POST 同步方法
     */
    public String POST_SYN(String reqUrl, String jsonParams)
    {
        return okHttpImpl.requestSyn(reqUrl, OkHttpImpl.ReqType.TYPE_POST_JSON, jsonParams);
    }


    /**
     * GET 异步方法
     */
    public void GET_ASY(String reqUrl, String jsonParams, NetCallBack.ReqCallBack reqCallBack)
    {
        okHttpImpl.requestAsyn(reqUrl, OkHttpImpl.ReqType.TYPE_GET, jsonParams, reqCallBack);
    }

    /**
     * GET 同步方法
     */
    public String GET_SYN(String reqUrl, String jsonParams)
    {
        return okHttpImpl.requestSyn(reqUrl, OkHttpImpl.ReqType.TYPE_GET, jsonParams);
    }

    /**
     * 文件异步下载,带进度,下载的文件未进行权限修改
     *
     * @param reqUrl   下载地址
     * @param filePath 保存路径
     * @param fileName 保存的文件名
     * @param callBack 回调
     */
    public void FILE_ASY_DOWN(String reqUrl, String filePath, String fileName, NetCallBack.FileStateCallBack callBack)
    {
        okHttpImpl.downFileForAsy(reqUrl, filePath, fileName, callBack);
    }

    /**
     * 文件异步上传,带进度
     *
     * @param file
     * @param uplodUrl
     * @param callBack
     */
    public void FILE_ASY_UPLOAD(File file, String uplodUrl, NetCallBack.FileStateCallBack callBack)
    {
        okHttpImpl.upLoadFile(file, uplodUrl, callBack);
    }

}