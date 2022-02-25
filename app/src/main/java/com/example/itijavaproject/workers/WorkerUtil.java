package com.example.itijavaproject.workers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.itijavaproject.R;

public class WorkerUtil {

    private final Context context;

    public WorkerUtil(Context context) {
        this.context = context;
    }

    public void createNotification(String body, String title) {

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "gfhfg";
            String description = "awds";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("MED_REMINDER_CHANNEL1", name, importance);
            channel.setDescription(description);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);

            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                "MED_REMINDER_CHANNEL")
                .setSmallIcon(R.drawable.auth_screen)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        Notification notification = builder.build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, notification);

    }


}
