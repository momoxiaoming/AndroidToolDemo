package com.mmo.tooldemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.andr.common.tool.util.ReflectUtils;
import com.mmo.tooldemo.room.AppDataBase;
import com.mmo.tooldemo.room.entity.User;

import org.qiyi.video.svg.Andromeda;
import org.qiyi.video.svg.event.Event;
import org.qiyi.video.svg.event.EventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ICheckApple , EventListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Andromeda.registerLocalService(ICheckApple.class,this);

        Andromeda.subscribe("123",MainActivity.this);
        findViewById(R.id.test2).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                User user = new User();
//                user.setAge("123");
//                user.setName("xiaoming");
//                try
//                {
//                    AppDataBase.getInstance(getApplicationContext()).userDao().insertUser(user);
//                } catch (Exception e)
//                {
//                    e.printStackTrace();
//                }

                startActivity(new Intent(MainActivity.this,Main2Activity.class));

//                LoggerUtil.e("12313123");
//
//                if(new File(getFilesDir()+ File.separator+"log/").isDirectory()){
//                    LoggerUtil.e("11111");
//                }else{
//                    LoggerUtil.e("33333");
//                }

            }
        });

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //
                List<User> list = AppDataBase.getInstance(getApplicationContext()).userDao().getUser();

                ReflectUtils.logDump();
                Log.d("allen","---"+list.size());
//                downFile();
            }
        });
    }

    public void downFile()
    {
        //        String url = "http://api.momoxiaoming.com:9102/static/app-release_v2.apk";
        //        OkHttpUtils.downFile()
        //                .url(url)
        //                .build()
        //                .execute(new FileCallBack("/sdcard/", "test123.apk")
        //        {
        //            @Override
        //            public void onProgress(int id, long total, long index)
        //            {
        //                Log.d("allen","下载进度:" +index+"/"+total);
        //            }
        //
        //            @Override
        //            public void onResponse(int id, File file)
        //            {
        //                Log.d("allen","下载成功" );
        //            }
        //
        //            @Override
        //            public void onFailure(int id, Call call, Exception exception, String errmsg)
        //            {
        //                Log.d("allen","下载失败" );
        //            }
        //        });
    }


    @Override
    public void onPostData()
    {
//        LoggerUtil.i("onpostdat");
        Log.d("allen","onpostdat");
    }

    @Override
    public void onNotify(Event event)
    {
//        LoggerUtil.i("收到事件");

        Log.d("allen","收到事件");
    }
}
