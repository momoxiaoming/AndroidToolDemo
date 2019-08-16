package com.mmo.tooldemo.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/6/12
 *     desc  : new class
 * </pre>
 */
@Entity(tableName = "users",indices = {@Index(value = "name",unique = true)})
public class User
{
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @NonNull
    private String name;

    private String age;

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    @NonNull
    public String getName()
    {
        return name;
    }

    public void setName(@NonNull String name)
    {
        this.name = name;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }
}
