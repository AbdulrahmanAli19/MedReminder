package com.example.itijavaproject.workers;

import static java.lang.Thread.sleep;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.itijavaproject.util.Window;

public class MedReminder extends Worker {

    private static final String TAG = "MedReminder.DEV";

    public MedReminder(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    Handler handler = new Handler(getApplicationContext().getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String medId = getInputData().getString("medId");
            boolean isPermissionGranted = getInputData().getBoolean("permission", false);

            if (isPermissionGranted) {
                new Window(getApplicationContext(), medId);
            }
//           new WorkerUtil(getApplicationContext()).createNotification("Test", "title");

        }
    };


    @NonNull
    @Override
    public Result doWork() {
        try {
            sleep(5000);
            handler.sendMessage(new Message());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success();
    }
}
