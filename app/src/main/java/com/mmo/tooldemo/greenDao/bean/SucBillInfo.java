package com.mmo.tooldemo.greenDao.bean;

import com.andr.common.tool.util.TimeUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * <pre>
 *     author: momoxiaoming
 *     blog  : http://blog.momoxiaoming.com
 *     time  : 2019/3/18
 *     desc  : new class
 * </pre>
 */
@Entity
public class SucBillInfo
{
    @Id(autoincrement = true)
    private Long bid;
    private String time = TimeUtils.getCurrentTimeInString();
    @Unique
    private String internId;
    @NotNull
    private String type;
    @NotNull
    private String data;
    @NotNull
    private String thirdtranNo;

    private String errTime ;  //错表入库时间


    public String getThirdtranNo()
    {
        return thirdtranNo;
    }

    public void setThirdtranNo(String thirdtranNo)
    {
        this.thirdtranNo = thirdtranNo;
    }

    @Generated(hash = 429342965)
    public SucBillInfo() {
    }

    @Generated(hash = 528683500)
    public SucBillInfo(Long bid, String time, String internId, @NotNull String type,
            @NotNull String data, @NotNull String thirdtranNo, String errTime) {
        this.bid = bid;
        this.time = time;
        this.internId = internId;
        this.type = type;
        this.data = data;
        this.thirdtranNo = thirdtranNo;
        this.errTime = errTime;
    }

    public Long getBid() {
        return bid;
    }

    public String getInternId() {
        return internId;
    }

    public void setInternId(String internId) {
        this.internId = internId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getErrTime()
    {
        return errTime;
    }

    public void setErrTime(String errTime)
    {
        this.errTime = errTime;
    }
}
