package com.example.assignment3;

import android.app.Application;


import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApp extends Application {
    NetworkingService networkingService = new NetworkingService();
    User user = new User();
    DBManager dbManager = new DBManager();

    static ExecutorService executorService = Executors.newFixedThreadPool(4);
}
