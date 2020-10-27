package com.mmo.tooldemo.room.dao;

import com.mmo.tooldemo.room.entity.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
