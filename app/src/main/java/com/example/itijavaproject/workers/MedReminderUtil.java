package com.example.itijavaproject.workers;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MedReminderUtil {
    private static final String TAG = "AddMedReminder.DEV";

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void addMedReminder(long time, Context context, String medId) {

        long delay = ((time / 1000L) - (System.currentTimeMillis() / 1000L));

        Data data = new Data.Builder().putString("medId", medId)
                .putBoolean("permission", Settings.canDrawOverlays(context))
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresCharging(false)
                .build();

        OneTimeWorkRequest periodicWorkRequest = new OneTimeWorkRequest
                .Builder(MedReminder.class)
                .setInitialDelay(delay, TimeUnit.SECONDS)
                .setInputData(data)
                .addTag(medId)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(context).enqueue(periodicWorkRequest);
    }

    public static void removeMedReminder(String medId, Context context) {
        WorkManager.getInstance(context).cancelAllWorkByTag(medId);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void addSingleReminder(int duration, String medId, Context context) {

        Data data = new Data.Builder().putString("medId", medId)
                .putBoolean("permission", Settings.canDrawOverlays(context))
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresCharging(false)
                .build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest
                .Builder(MedReminder.class)
                .setInitialDelay(duration, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setInputData(data)
                .addTag(medId)
                .build();

        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void addDayReminder(int time, String tag, Context context) {
        Data data = new Data.Builder()
                .putBoolean("permission", Settings.canDrawOverlays(context))
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresCharging(false)
                .build();

        PeriodicWorkRequest oneTimeWorkRequest = new PeriodicWorkRequest
                .Builder(DayWork.class, time, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInputData(data)
                .addTag(tag)
                .build();

        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);
    }

}
