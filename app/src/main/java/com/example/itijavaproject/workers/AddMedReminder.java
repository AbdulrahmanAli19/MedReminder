package com.example.itijavaproject.workers;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class AddMedReminder {

    private final Context context;

    public AddMedReminder(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addMedReminder() {
        Data data = new Data.Builder().putString("title", "dummy")
                .putString("body", "dummy body")
                .putBoolean("permission", Settings.canDrawOverlays(context)).build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresCharging(false)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
                .Builder(MedReminder.class, 30, TimeUnit.SECONDS)
                .setInputData(data)
                .addTag("TestPWorker")
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(context).enqueue(periodicWorkRequest);
    }
}
