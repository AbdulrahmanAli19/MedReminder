package com.example.itijavaproject.data.db;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;

import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.M)
public class UploadWorker extends Worker {
    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        return null;
    }


    Constraints myConstraints = new Constraints.Builder()
            .setRequiresDeviceIdle(true)
            .setRequiresCharging(true)
            .build();

    PeriodicWorkRequest periodicWork = new PeriodicWorkRequest.Builder(UploadWorker.class, 12, TimeUnit.HOURS)
            .build();
    WorkManager workManager= (WorkManager) WorkManager.getInstance().enqueue(periodicWork);

}
