package com.mmo.tooldemo.room;

import android.content.Context;

import com.mmo.tooldemo.room.dao.UserDao;
import com.mmo.tooldemo.room.entity.User;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/6/12
 *     desc  : 管理实体类和dao类
 * </pre>
 */

@Database(entities = {User.class}, version = 1,exportSchema=false)
public abstract class AppDataBase extends RoomDatabase
{
    public abstract UserDao userDao();

    private static AppDataBase INSTANCE;

    private static final Object sLock = new Object();

    public static AppDataBase getInstance(Context context)
    {
        synchronized (sLock)
        {
            if (INSTANCE == null)
            {
                INSTANCE = Room.databaseBuilder(context, AppDataBase.class, "user.db").allowMainThreadQueries().build();


            }
            return INSTANCE;
        }
    }


}
