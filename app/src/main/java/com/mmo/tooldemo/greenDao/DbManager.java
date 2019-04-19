package com.mmo.tooldemo.greenDao;

import com.mediatek.assist.bean.ModelContent;
import com.mediatek.assist.bean.TransportModel;
import com.mediatek.assist.util.GsonUtil;
import com.mediatek.assist.util.LogUtil;
import com.mediatek.hookassist.model.storage.local.greendao.api.ErrorBillInfoDao;
import com.mediatek.hookassist.model.storage.local.greendao.api.HookTypeInfoDao;
import com.mediatek.hookassist.model.storage.local.greendao.api.SucBillInfoDao;
import com.mediatek.hookassist.model.storage.local.greendao.api.TaskDataInfoDao;
import com.mediatek.hookassist.model.storage.local.greendao.bean.ErrorBillInfo;
import com.mediatek.hookassist.model.storage.local.greendao.bean.HookTypeInfo;
import com.mediatek.hookassist.model.storage.local.greendao.bean.SucBillInfo;
import com.mediatek.hookassist.model.storage.local.greendao.bean.TaskDataInfo;
import com.mediatek.hookassist.util.Tool;
import com.mediatek.util.StringUtil;

import java.util.List;

/**
 * 模块：
 * 作者：YSoul
 * 时间：2019/1/9 15:55
 * 说明：
 */
public class DbManager
{

    public static void insertHookTypeInfo(String type)
    {


        if (type != null)
        {

            if (quryHookTypeInfo() != null)
            {
                deleteHookType();
            }

            HookTypeInfo hookTypeInfo = new HookTypeInfo();
            hookTypeInfo.setHkType(type);
            HookTypeInfoDao hookTypeInfoDao = DbCenter.getInstance().getDaoSession().getHookTypeInfoDao();
            hookTypeInfoDao.insert(hookTypeInfo);


        }


    }

    /**
     * 查询单个类型数据
     *
     * @param
     * @return
     */
    public static void deleteHookType()
    {

        HookTypeInfoDao hookTypeInfoDao = DbCenter.getInstance().getDaoSession().getHookTypeInfoDao();
        hookTypeInfoDao.deleteAll();

    }


    public static String quryHookType()
    {
        String type = "";
        HookTypeInfo hookTypeInfo = quryHookTypeInfo();

        if (hookTypeInfo != null)
        {
            type = hookTypeInfo.getHkType();
        }

        return type;
    }


    /**
     * 查询单个类型数据
     *
     * @param
     * @return
     */
    public static HookTypeInfo quryHookTypeInfo()
    {


        HookTypeInfoDao hookTypeInfoDao = DbCenter.getInstance().getDaoSession().getHookTypeInfoDao();
        List<HookTypeInfo> lists = hookTypeInfoDao.loadAll();
        if (lists.size() > 0)
        {
            return lists.get(0);
        }

        return null;

    }


    /**
     * 插入成功表
     *
     * @param
     */
    public static long insertSucBillTable(SucBillInfo sucBillInfo)
    {

        if (sucBillInfo != null)
        {

            if (querySucBillInfoDao(sucBillInfo.getInternId()) == null)
            {
                SucBillInfoDao sucBillInfoDao = DbCenter.getInstance().getDaoSession().getSucBillInfoDao();
                return sucBillInfoDao.insert(sucBillInfo);
            }


        }
        return -1;


    }

    /**
     * 查询单个成功数据
     *
     * @param innerId
     * @return
     */
    public static SucBillInfo querySucBillInfoDao(String innerId)
    {

        if (innerId != null)
        {
            SucBillInfoDao sucBillInfoDao = DbCenter.getInstance().getDaoSession().getSucBillInfoDao();
            List<SucBillInfo> lists = sucBillInfoDao.queryBuilder().where(SucBillInfoDao.Properties.InternId.eq(innerId)).build().list();
            if (lists.size() > 0)
            {
                return lists.get(0);
            }
        }
        return null;

    }

    /**
     * 查询单个成功数据
     *
     * @param
     * @return
     */
    public static SucBillInfo querySucBillInfoDaoForTranNo(String tranNo)
    {

        if (tranNo != null)
        {
            SucBillInfoDao sucBillInfoDao = DbCenter.getInstance().getDaoSession().getSucBillInfoDao();
            List<SucBillInfo> lists = sucBillInfoDao.queryBuilder().where(SucBillInfoDao.Properties.ThirdtranNo.eq(tranNo)).build().list();
            if (lists.size() > 0)
            {
                return lists.get(0);
            }
        }
        return null;

    }


    /**
     * 根据内部id,删除单个成功表数据
     *
     * @return
     */
    public static void deleteSucBillForInnerId(String innerId)
    {

        SucBillInfo sucBillInfo = querySucBillInfoDao(innerId);
        if (sucBillInfo != null)
        {
            SucBillInfoDao sucBillInfoDao = DbCenter.getInstance().getDaoSession().getSucBillInfoDao();
            sucBillInfoDao.delete(sucBillInfo);

        }


    }

    /**
     * 查询成功表数据
     *
     * @return
     */
    public static List<SucBillInfo> quryAllSucBill()
    {

        return DbCenter.getInstance().queryAll(SucBillInfo.class);

    }


    public static void insertErrorListData(String type, String data)
    {
        if (StringUtil.isStringEmpty(type) || StringUtil.isStringEmpty(data))
        {
            return;
        }

        TransportModel transportModel = GsonUtil.parseJsonWithGson(data, TransportModel.class);

        if (transportModel != null)
        {
            List<ModelContent> list = transportModel.getDataList();

            if (list != null)
            {
                for (ModelContent item : list)
                {
                    insertErrorBillTable(type, item);
                }

            }
        }


    }

    /**
     * 插入错表
     *
     * @param
     */
    public static long insertErrorBillTable(String type, String data, String tranNO)
    {

        synchronized (DbManager.class)
        {
            if (StringUtil.isStringEmpty(type) || StringUtil.isStringEmpty(data))
            {
                return -1;
            }

            String innerId = Tool.getInnerTradeId(type, data);


            if (tranNO == null || "".equals(tranNO))
            {
                tranNO = innerId;
            }

            ErrorBillInfo errorBillInfo = new ErrorBillInfo();
            errorBillInfo.setData(data);
            errorBillInfo.setType(type);
            errorBillInfo.setThirdtranNo(tranNO);
            errorBillInfo.setInternId(innerId);
            if (querySucBillInfoDaoForTranNo(tranNO) == null && queryErrorBillInfoDaoForTanNo(tranNO) == null)
            {
                ErrorBillInfoDao errorBillInfoDao = DbCenter.getInstance().getDaoSession().getErrorBillInfoDao();
                long rlt = errorBillInfoDao.insert(errorBillInfo);
                LogUtil.d("错表无数据,开始插入.....:" + tranNO + " 插入状态:" + rlt);

                return rlt;

            } else
            {
                //                    LogUtil.d("重复的账单数据..." + tranNo);
            }

            return -1;
        }
    }


    /**
     * 插入错表
     *
     * @param
     */
    public static long insertErrorBillTable(String type, ModelContent modelContent)
    {

        synchronized (DbManager.class)
        {
            if (StringUtil.isStringEmpty(type))
            {
                return -1;
            }

            if (modelContent != null && modelContent.getContent() != null)
            {

                String innerId = Tool.getInnerTradeId(type, modelContent.getContent());

                String tranNo = modelContent.getTridTranNo();


                if (tranNo == null || "".equals(tranNo))
                {
                    tranNo = innerId;
                }

                ErrorBillInfo errorBillInfo = new ErrorBillInfo();
                errorBillInfo.setData(modelContent.getContent());
                errorBillInfo.setType(type);
                errorBillInfo.setThirdtranNo(tranNo);
                errorBillInfo.setInternId(innerId);

                if (querySucBillInfoDaoForTranNo(tranNo) == null && queryErrorBillInfoDaoForTanNo(tranNo) == null)
                {
                    ErrorBillInfoDao errorBillInfoDao = DbCenter.getInstance().getDaoSession().getErrorBillInfoDao();
                    long rlt = errorBillInfoDao.insert(errorBillInfo);
                    LogUtil.d("错表无数据,开始插入.....:" + tranNo + " 插入状态:" + rlt);

                    return rlt;

                } else
                {
                    //                    LogUtil.d("重复的账单数据..." + tranNo);
                }


            } else
            {

                LogUtil.d("账单数据转换TransportModel 失败...");
            }


            return -1;
        }
    }

    /**
     * 插入错表
     *
     * @param
     */
    public static long insertErrorBillTable(ErrorBillInfo errorBillInfo)
    {

        if (errorBillInfo != null)
        {
            if (queryErrorBillInfoDao(errorBillInfo.getInternId()) == null)
            {
                ErrorBillInfoDao errorBillInfoDao = DbCenter.getInstance().getDaoSession().getErrorBillInfoDao();
                return errorBillInfoDao.insert(errorBillInfo);
            }
        }
        return -1;

    }

    /**
     * 删除错表,入成功表
     *
     * @param innerId
     */
    public static void deleteErrorAndAddSuc(String innerId)
    {
        synchronized (DbManager.class)
        {
            ErrorBillInfo errorBillInfo = deleteErrorBillForInnerId(innerId);
            if (errorBillInfo != null)
            {

                SucBillInfo sucBillInfo = new SucBillInfo();
                sucBillInfo.setType(errorBillInfo.getType());
                sucBillInfo.setInternId(errorBillInfo.getInternId());
                sucBillInfo.setData(errorBillInfo.getData());
                sucBillInfo.setThirdtranNo(errorBillInfo.getThirdtranNo());
                sucBillInfo.setErrTime(errorBillInfo.getTime());
                insertSucBillTable(sucBillInfo);
            }
        }
    }


    /**
     * 查询单个错表数据
     *
     * @param innerId
     * @return
     */
    public static ErrorBillInfo queryErrorBillInfoDao(String innerId)
    {

        if (innerId != null)
        {
            ErrorBillInfoDao errorBillInfoDao = DbCenter.getInstance().getDaoSession().getErrorBillInfoDao();
            List<ErrorBillInfo> lists = errorBillInfoDao.queryBuilder().where(ErrorBillInfoDao.Properties.InternId.eq(innerId)).build().list();
            if (lists.size() > 0)
            {
                return lists.get(0);
            }
        }
        return null;

    }

    /**
     * 查询单个错表数据
     *
     * @param innerId
     * @return
     */
    public static ErrorBillInfo queryErrorBillInfoDao(String innerId, String tranNo)
    {

        if (innerId != null && tranNo != null)
        {
            ErrorBillInfoDao errorBillInfoDao = DbCenter.getInstance().getDaoSession().getErrorBillInfoDao();
            List<ErrorBillInfo> lists = errorBillInfoDao.queryBuilder().where(ErrorBillInfoDao.Properties.InternId.eq(innerId), ErrorBillInfoDao.Properties.ThirdtranNo.eq(tranNo)).build().list();
            if (lists.size() > 0)
            {
                return lists.get(0);
            }
        }
        return null;

    }

    /**
     * 查询单个错表数据
     *
     * @param tranNo
     * @return
     */
    public static ErrorBillInfo queryErrorBillInfoDaoForTanNo(String tranNo)
    {

        if (tranNo != null)
        {
            ErrorBillInfoDao errorBillInfoDao = DbCenter.getInstance().getDaoSession().getErrorBillInfoDao();
            List<ErrorBillInfo> lists = errorBillInfoDao.queryBuilder().where(ErrorBillInfoDao.Properties.ThirdtranNo.eq(tranNo)).build().list();
            if (lists.size() > 0)
            {

                return lists.get(0);
            }
        }
        return null;
    }


    /**
     * 根据内部id,删除单个错表数据
     *
     * @return
     */
    public static ErrorBillInfo deleteErrorBillForInnerId(String innerId)
    {

        ErrorBillInfo errorBillInfo = queryErrorBillInfoDao(innerId);
        if (errorBillInfo != null)
        {

            ErrorBillInfoDao errorBillInfoDao = DbCenter.getInstance().getDaoSession().getErrorBillInfoDao();
            errorBillInfoDao.delete(errorBillInfo);
            return errorBillInfo;
        }
        return null;


    }


    /**
     * 查询所有错表
     *
     * @return
     */
    public static List<ErrorBillInfo> quryAllErrorBill()
    {


        return DbCenter.getInstance().queryAll(ErrorBillInfo.class);

    }


    /**
     * 条件查询错表
     *
     * @return
     */
    public static List<ErrorBillInfo> quryErrorBillForNum(int num)
    {

        ErrorBillInfoDao errorBillInfoDao = DbCenter.getInstance().getDaoSession().getErrorBillInfoDao();
        return errorBillInfoDao.queryBuilder().limit(num)//只获取结果集的前个数据
                .orderDesc(ErrorBillInfoDao.Properties.Time)//通过 StudentNum 这个属性进行正序排序
                .build().list();
    }


    /**
     * 插入模块任务提交数据
     *
     * @return
     */
    public static void insertTaskDataInfoData(String type, String data)
    {


        if (!StringUtil.isStringEmpty(type) && !StringUtil.isStringEmpty(data))
        {


            TaskDataInfo taskDataInfo = queryTaskDataInfoData(type);

            TaskDataInfoDao taskDataInfoDao = DbCenter.getInstance().getDaoSession().getTaskDataInfoDao();

            if (taskDataInfo != null)
            {
                taskDataInfo.setType(type);
                taskDataInfo.setTaskInfo(data);

                LogUtil.d("应用信息表-有数据,直接更新");

                taskDataInfoDao.update(taskDataInfo);

            } else
            {

                LogUtil.d("应用信息表-没有数据,直接插入");

                TaskDataInfo dataInfo = new TaskDataInfo();
                dataInfo.setType(type);
                dataInfo.setTaskInfo(data);

                taskDataInfoDao.insert(dataInfo);
            }
        }


    }

    /**
     * 查询模块提交任务
     *
     * @param type
     * @return
     */
    public static TaskDataInfo queryTaskDataInfoData(String type)
    {
        if (!StringUtil.isStringEmpty(type))
        {
            TaskDataInfoDao taskDataInfoDao = DbCenter.getInstance().getDaoSession().getTaskDataInfoDao();

            List<TaskDataInfo> list = taskDataInfoDao.queryBuilder().where(TaskDataInfoDao.Properties.Type.eq(type)).orderDesc(TaskDataInfoDao.Properties.Time).build().list();

            if (list != null && list.size() > 0)
            {
                return list.get(0);
            }
        }


        return null;
    }

    /**
     * 删除所有的hk数据
     */
    public static void deleteHKData()
    {
        TaskDataInfoDao taskDataInfoDao = DbCenter.getInstance().getDaoSession().getTaskDataInfoDao();
        taskDataInfoDao.deleteAll();
    }

}
