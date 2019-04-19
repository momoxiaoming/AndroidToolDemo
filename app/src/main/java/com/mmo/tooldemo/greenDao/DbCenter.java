package com.mmo.tooldemo.greenDao;

import android.content.Context;

import com.mmo.tooldemo.greenDao.api.DaoMaster;
import com.mmo.tooldemo.greenDao.api.DaoSession;

import java.util.List;

/**
 * Created by zhangxiaoming on 2018/6/10.
 */

public class DbCenter
{

    private static DbCenter dbCenter;
    private MyGreenDaoHandle openHelper;

    private DaoSession daoSession;


    /**
     * 获取单例引用
     *
     * @return
     */
    public static DbCenter getInstance() {
        if (dbCenter == null)
        {
            synchronized (DbCenter.class)
            {
                if (dbCenter == null)
                {
                    dbCenter = new DbCenter();
                }
            }
        }
        return dbCenter;
    }

    /**
     * 初始化数据库
     *
     * @param context
     */
    public void initDb(Context context, String dbName) {
        openHelper = new MyGreenDaoHandle(context, dbName, null);
        daoSession = getDaoSession();
    }


    public DaoSession getDaoSession() {
        if (daoSession == null)
        {
            if (openHelper != null)
            {
                DaoMaster daoMaster = null;


                daoMaster = new DaoMaster(openHelper.getWritableDatabase());


                daoSession = daoMaster.newSession();
            }
        }

        return daoSession;
    }

    public <T> void insert(T entity) {
        if (entity == null)
        {
            return;
        }
        try
        {
            DaoSession daoSession = getDaoSession();
            if (daoSession != null)
            {
                daoSession.insert(entity);

            }

        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {

        }
    }

    public <T> void delete(T entity) {
        if (entity == null)
        {
            return;
        }
        DaoSession daoSession;
        try
        {
            daoSession = getDaoSession();
            if (daoSession != null)
            {
                daoSession.delete(entity);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public <T> void update(T entity) {
        if (entity == null)
        {
            return;
        }
        try
        {
            DaoSession daoSession = getDaoSession();
            if (daoSession != null)
            {
                daoSession.update(entity);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public <T> List<T> queryAll(Class<T> entityClass) {
        if (entityClass == null)
        {
            return null;
        }
        List<T> queryList = null;
        try
        {
            DaoSession daoSession = getDaoSession();
            if (daoSession != null)
            {
                queryList = daoSession.loadAll(entityClass);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return queryList;
    }

    public <T> List<T> queryRaw(Class<T> entityClass, String where, String... selectionArgs) {
        if (entityClass == null)
        {
            return null;
        }
        List<T> queryList = null;
        try
        {
            DaoSession daoSession = getDaoSession();

            if (daoSession != null)
            {
                queryList = daoSession.queryRaw(entityClass, where, selectionArgs);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            daoSession.clear();
        }
        return queryList;
    }



}
