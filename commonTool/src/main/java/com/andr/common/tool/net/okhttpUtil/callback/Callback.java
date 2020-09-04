package com.andr.common.tool.net.okhttpUtil.callback;

import okhttp3.Response;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/27
 *     desc  : new class
 * </pre>
 */
public abstract class Callback
{
    public abstract void onFailure(String tag,  Exception exception, String errmsg);

    public abstract Object onParse(Response response, String tag) throws Exception;

    public abstract void onResponse(String tag, Object object);





}
