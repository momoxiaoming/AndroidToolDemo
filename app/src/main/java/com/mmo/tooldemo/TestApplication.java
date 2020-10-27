package com.mmo.tooldemo;

import android.app.Application;

import com.andr.common.tool.log.LoggerUtil;



import java.io.File;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/8/16
 *     desc  : new class
 * </pre>
 */
public class TestApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        File file = new File(getFilesDir() + File.separator + "log");

        if(!file.exists()||!file.isDirectory()){
            file.mkdirs();
        }

        LoggerUtil.initLogger("tag", false, 3, getFilesDir() + File.separator + "log");

        LoggerUtil.e("123123123");
    }
}
