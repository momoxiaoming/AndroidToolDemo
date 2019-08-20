package com.andr.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.andr.common.tool.log.LoggerUtil;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/8/20
 *     desc  : 网络状态监听广播
 * </pre>
 */
public class NetworkConnectStateReciver extends BroadcastReceiver
{


    @Override
    public void onReceive(Context context, Intent intent)
    {
        System.out.println("网络状态发生变化");
        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        //        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP)
        //        {

        //获得ConnectivityManager对象
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //获取ConnectivityManager对象对应的NetworkInfo对象
        //获取WIFI连接的信息
        NetworkInfo wifiNetworkInfo = connMgr.getActiveNetworkInfo();

        if (wifiNetworkInfo!=null&&wifiNetworkInfo.isConnected())
        {
            LoggerUtil.d("网络已连接:"+wifiNetworkInfo.getTypeName());
        } else
        {

            Toast.makeText(context,"网络已断开,请检查网络",Toast.LENGTH_SHORT).show();
        }


    }
}
