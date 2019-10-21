package com.mmo.tooldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.qiyi.video.svg.Andromeda;
import org.qiyi.video.svg.event.Event;

public class Main2Activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.send_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                ICheckApple iCheckApple = Andromeda.getLocalService(ICheckApple.class);
                iCheckApple.onPostData();


                Event evet=new Event();
                evet.setName("123");
                Andromeda.publish(evet);
            }
        });
    }
}
