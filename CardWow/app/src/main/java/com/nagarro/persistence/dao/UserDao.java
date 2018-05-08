package com.nagarro.persistence.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nagarro.persistence.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user where first_name LIKE  :firstName AND last_name LIKE :lastName")
    User findByName(String firstName, String lastName);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insertAll(User... users);

    @Query("SELECT latitude FROM user where first_name LIKE :firstName AND last_name LIKE :lastName")
    double findlatitude(String firstName, String lastName);

    @Query("SELECT longitude FROM user where first_name LIKE :firstName AND last_name LIKE :lastName")
    double findlongitude(String firstName, String lastName);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
