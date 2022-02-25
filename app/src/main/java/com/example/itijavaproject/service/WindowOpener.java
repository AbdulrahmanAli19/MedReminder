package com.example.itijavaproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.itijavaproject.util.Window;

public class WindowOpener extends Service {
    public WindowOpener() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        new Window(getApplicationContext(), "Test", "its running from serices").open();
        return null;
    }
}