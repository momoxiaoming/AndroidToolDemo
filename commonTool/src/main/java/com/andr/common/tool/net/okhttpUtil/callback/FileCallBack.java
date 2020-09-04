package com.andr.common.tool.net.okhttpUtil.callback;


import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

public abstract class FileCallBack extends Callback
{
    private String filePath;
    private long postion=0;  //当前断点

    public FileCallBack(String filePath, long postion)
    {
        super();
        this.filePath = filePath;
        this.postion = postion;
    }

    public abstract void onProgress(String tag, long total, long index);

    public abstract void onResponse(String tag, File file);

    @Override
    public void onResponse(String tag, Object object)
    {
        onResponse(tag, (File) object);
    }

    public File onParse(Response response, String tag) throws Exception
    {
        return this.saveFile(response, tag);
    }


    public File saveFile(Response arg17, String tag) throws Exception
    {
        File saveFile = new File(filePath);
        InputStream v12 = null;
        RandomAccessFile randomAccessFile = null;
        try
        {
            if (!saveFile.exists())
            {
                saveFile.getParentFile().mkdirs();
            }
            if (!saveFile.exists())
            {
                saveFile.createNewFile();
            }

            if (arg17.body() != null)
            {
                long v9 = arg17.body().contentLength();
                v12 = arg17.body().byteStream();
                long fileLength=v9+postion;
                randomAccessFile = new RandomAccessFile(saveFile, "rwd");
                randomAccessFile.setLength(fileLength);
                randomAccessFile.seek(postion);

                byte[] buffer = new byte[1024 * 16];//缓冲数组16kB
                int len = 0;
                while ((len = v12.read(buffer)) != -1)
                {
                    randomAccessFile.write(buffer, 0, len);
                    postion += len;
                    this.sendProgress(tag, fileLength, postion);

                }
                return saveFile;

            }
        } catch (IOException e)
        {
            throw e;
        } finally
        {
            if (v12 != null)
            {
                v12.close();
            }
            if (randomAccessFile != null)
            {
                randomAccessFile.close();
            }
        }

        return null;
    }

    private void sendProgress(final String tag, final long total, final long index)
    {
        new Handler(Looper.getMainLooper()).post(() -> FileCallBack.this.onProgress(tag, total, index));

    }
}

