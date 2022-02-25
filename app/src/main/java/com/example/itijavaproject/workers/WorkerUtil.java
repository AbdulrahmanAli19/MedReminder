package com.example.itijavaproject.workers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.itijavaproject.R;

public class WorkerUtil {

    private final Context context;

    public WorkerUtil(Context context) {
        this.context = context;
    }

    public void createNotification(String body, String title) {

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                "MED_REMINDER_CHANNEL")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "gfhfg";
            String description = "awds";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("MED_REMINDER_CHANNEL1", name, importance);
            channel.setDescription(description);
            builder.setChannelId("MED_REMINDER_CHANNEL1");
            notificationManager.createNotificationChannel(channel);
        }

        if (notificationManager != null)
            notificationManager.notify(88, builder.build());

    }
}
