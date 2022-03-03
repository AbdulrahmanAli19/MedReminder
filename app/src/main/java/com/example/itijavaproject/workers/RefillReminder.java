package com.example.itijavaproject.workers;

import static java.lang.Thread.sleep;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.itijavaproject.util.Window;
import com.example.itijavaproject.util.WindowRefill;

public class RefillReminder extends Worker {
    private static final String TAG = "RefillReminder";
    public RefillReminder(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    Handler handler = new Handler(getApplicationContext().getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            String medId = getInputData().getString("medId");
            Log.d(TAG, "handleMessage: "+medId);
            boolean isPermissionGranted = getInputData().getBoolean("permission", false);

            if (isPermissionGranted) {
                new WindowRefill(getApplicationContext(), medId);
            }
//            new WorkerUtil(getApplicationContext()).createNotification("you need refill your med","Refill reminder");


        }
    };

    @NonNull
    @Override
    public Result doWork() {
        try {
            sleep(10000);
            handler.sendMessage(new Message());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success();
    }
}
