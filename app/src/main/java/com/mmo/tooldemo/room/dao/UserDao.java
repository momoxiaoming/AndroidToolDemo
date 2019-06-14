package com.mmo.tooldemo.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mmo.tooldemo.room.entity.User;

import java.util.List;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/6/12
 *     desc  : dao类主要提供相关实体类的查询方法
 * </pre>
 */

@Dao
public interface UserDao
{


    @Query("SELECT * FROM USERS WHERE age= :mAge")
    List<User> getUsersForAge(int mAge);

    @Query("SELECT * FROM USERS ")
    List<User> getUser();


    @Delete
    void deleteUser(User user);

    @Insert
    void insertUser(User ...user);

    @Update
    void updateUser(User ...user);

}
