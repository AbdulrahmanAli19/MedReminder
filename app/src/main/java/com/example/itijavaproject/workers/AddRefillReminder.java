package com.example.itijavaproject.workers;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class AddRefillReminder {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void addRefill(Context context, String medId) {
        Data data = new Data.Builder().putString("medId", medId)
                .putBoolean("permission", Settings.canDrawOverlays(context))
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresCharging(false)
                .build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest
                .Builder(RefillReminder.class)
                .setInputData(data)
                .addTag(medId)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);
    }

    public static void removeRefillReminder(String medId, Context context) {
        WorkManager.getInstance(context).cancelAllWorkByTag(medId);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void RefillSingleReminder(int duration, String medId, Context context) {

        Data data = new Data.Builder().putString("medId", medId)
                .putBoolean("permission", Settings.canDrawOverlays(context))
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresCharging(false)
                .build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest
                .Builder(RefillReminder.class)
                .setInitialDelay(duration, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setInputData(data)
                .addTag(medId)
                .build();

        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void RefillDayReminder(int time, String tag, Context context) {
        Data data = new Data.Builder()
                .putBoolean("permission", Settings.canDrawOverlays(context))
                .build();
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresCharging(false)
                .build();
        PeriodicWorkRequest oneTimeWorkRequest = new PeriodicWorkRequest
                .Builder(RefillWork.class, time, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInputData(data)
                .addTag(tag)
                .build();

        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);
    }


}
