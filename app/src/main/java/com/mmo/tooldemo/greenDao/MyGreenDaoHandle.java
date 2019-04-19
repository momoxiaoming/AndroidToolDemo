package com.mmo.tooldemo.greenDao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mmo.tooldemo.greenDao.api.DaoMaster;

import org.greenrobot.greendao.database.Database;

import static com.mmo.tooldemo.greenDao.api.DaoMaster.dropAllTables;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/4/19
 *     desc  : new class
 * </pre>
 */
public class MyGreenDaoHandle extends DaoMaster.DevOpenHelper
{


    public MyGreenDaoHandle(Context context, String name)
    {
        super(context, name);
    }

    public MyGreenDaoHandle(Context context, String name, SQLiteDatabase.CursorFactory factory)
    {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {

        dropAllTables(db, true);
        onCreate(db);

    }
}
