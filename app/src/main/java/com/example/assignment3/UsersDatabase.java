package com.example.assignment3;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UsersDatabase extends RoomDatabase {
    public abstract UsersDao getDao();
}
