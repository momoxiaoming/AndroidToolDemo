package com.mmo.tooldemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mmo.tooldemo.room.AppDataBase;
import com.mmo.tooldemo.room.entity.User;

import java.util.List;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.test2).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                User user = new User();
                user.setAge("123");
                user.setName("xiaoming");
                AppDataBase.getInstance(getApplicationContext()).userDao().insertUser(user);

            }
        });

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //
                List<User> list = AppDataBase.getInstance(getApplicationContext()).userDao().getUser();


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


}
