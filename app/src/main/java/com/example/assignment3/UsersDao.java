package com.example.assignment3;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UsersDao {

    @Insert
    void insertNewUser(User u);

    @Query("select * from User")
    User[] getAllDBUsers();

    @Delete
    void deleteUser(User u);

    @Query("select * from User where lower(nickName) like :sub")
    User[] getAllDBUsersStartWith(String sub);
}
