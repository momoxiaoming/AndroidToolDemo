package com.andr.common.tool.json;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONObject;

public final class GsonUtil 
{
    public static  <T> T parseJsonWithGson(String jsonData, Class<T> type) throws Exception
    {
        T rlt = null;
        
        if(!TextUtils.isEmpty(jsonData) && null != type)
        {
            Gson gson = new Gson();
            rlt = gson.fromJson(jsonData, type);
        }
        
        return rlt;
    }

    public static JSONObject toJsonWithGson(Object src) throws  Exception
    {
        JSONObject rlt  = null;
        if(null != src)
        {
            String JsonString = toJsonStringWithGson(src);
            rlt = new JSONObject(JsonString);
        }

        return rlt;
    }
    
    public static  String toJsonStringWithGson(Object src) throws  Exception
    {
        String rlt = null;

        if(null != src)
        {
            Gson gson = new Gson();
            rlt = gson.toJson(src);
        }

        return rlt;
    }
}
