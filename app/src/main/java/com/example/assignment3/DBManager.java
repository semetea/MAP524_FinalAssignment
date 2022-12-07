package com.example.assignment3;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

public class DBManager {

    interface DataBaseListener {
        void insertingUserCompleted();
        void gettingAllUsersCompleted(User[] u);
        void deletingUserCompleted();
    }

    UsersDatabase db;
    DataBaseListener listener;

    Handler handler = new Handler(Looper.getMainLooper());

    UsersDatabase getDB(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context,
                    UsersDatabase.class, "database-users").build();
        }
        return db;
    }

    void insertNewUserAsync(User u) {
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().insertNewUser(u);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.insertingUserCompleted();
                    }
                });
            }
        });
    }

    void getAllUsersAsync() {
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                User[] list = db.getDao().getAllDBUsers();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.gettingAllUsersCompleted(list);
                    }
                });
            }
        });
    }

    void deleteUserAsync(User u) {
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().deleteUser(u);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.deletingUserCompleted();
                    }
                });
            }
        });
    }
}
