package com.mmo.tooldemo;

import android.os.Bundle;

import com.andr.view.recy.MMRecyclerView;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MMRecyclerView mm_recyclerView=findViewById(R.id.mm_recyclerView);


    }


}
