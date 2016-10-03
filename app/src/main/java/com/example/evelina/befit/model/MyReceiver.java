package com.example.evelina.befit.model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.evelina.befit.LoginActivity;
import com.example.evelina.befit.R;
import com.example.evelina.befit.WelcomeActivity;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        long alarmTime= (long) intent.getExtras().get("ALARM TIME");

        if(!(System.currentTimeMillis()>alarmTime)){
            Intent resultIntent = new Intent(context, LoginActivity.class);
        long[] pattern = {0, 300, 0};
        PendingIntent pi = PendingIntent.getActivity(context, 01234, resultIntent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Be fit")
                .setContentText("Start training!")
                .setVibrate(pattern);


        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(01234, mBuilder.build());
    }
        else{
            Intent resultIntent = new Intent(context, LoginActivity.class);
            long[] pattern = {0, 300, 0};
            PendingIntent pi = PendingIntent.getActivity(context, 01234, resultIntent, 0);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("Be fit")
                    .setContentText("You haven`t trained for a while.Start training!")
                    .setVibrate(pattern);


            mBuilder.setContentIntent(pi);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mBuilder.setAutoCancel(true);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(01234, mBuilder.build());

        }
    }
}
