package com.example.assignment3;

import android.app.Application;

public class MyApp extends Application {
    NetworkingService networkingService = new NetworkingService();
    User user = new User();
}
