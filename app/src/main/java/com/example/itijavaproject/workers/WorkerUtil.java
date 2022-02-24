package com.example.itijavaproject.workers;

import static android.provider.Settings.System.getString;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.itijavaproject.MainActivity;
import com.example.itijavaproject.R;

public class WorkerUtil {

    public void createNotification(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "gfhfg";
            String description = "awds";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("radwa", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(notificationManager!=null){
                notificationManager.createNotificationChannel(channel);

            }
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"radwa")
                .setSmallIcon(R.drawable.auth_screen)
                .setContentTitle("textTitle")
                .setContentText("textContent")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVibrate(new long[0]);
        Notification notification=builder.build();
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,notification);

    }



//    public void createNotificationDialogue(){
//        LayoutInflater inflater = LayoutInflater.from();
//        View dialogue = inflater.inflate(R.layout.custom_dialogue, null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setView(dialogue);
//        TextView header = dialogue.findViewById(R.id.txtHeader);
//        TextView medName = dialogue.findViewById(R.id.medName);
//        TextView details = dialogue.findViewById(R.id.txtMedDetails);
//        ImageButton iconType = dialogue.findViewById(R.id.Icon);
//        //FloatingActionButton skip=dialogue.findViewById(R.id.cancelBtn);
//        //FloatingActionButton take=dialogue.findViewById(R.id.doneBtn);
//        //FloatingActionButton snooze=dialogue.findViewById(R.id.snoozeBtn);
//        AlertDialog customDialog = builder.create();
//        customDialog.show();
//
//    }



}
