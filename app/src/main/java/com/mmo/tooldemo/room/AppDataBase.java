package com.mmo.tooldemo.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mmo.tooldemo.room.dao.UserDao;
import com.mmo.tooldemo.room.entity.User;

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
