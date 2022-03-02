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

public class AddRefillReminder {
    private final Context context;
    private final String medId;

    public AddRefillReminder(Context context , String medId) {
        this.medId = medId;
        this.context = context;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addRefill()
    {
        Data data = new Data.Builder().putString("medId", medId)
                .putBoolean("permission", Settings.canDrawOverlays(context))
                .build();
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresCharging(false)
                .build();
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
                .Builder(RefillReminder.class, 10, TimeUnit.SECONDS)
                .setInputData(data)
                .addTag("TestPWorker")
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance(context).enqueue(periodicWorkRequest);
    }
}
