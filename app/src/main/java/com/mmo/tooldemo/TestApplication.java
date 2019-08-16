package com.mmo.tooldemo;

import android.app.Application;

import com.andr.common.tool.log.LoggerUtil;

import org.qiyi.video.svg.Andromeda;

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
        Andromeda.init(this);
        LoggerUtil.initLogger("tag",true,true);
    }
}
