package com.crazyostudio.to_do_list.Receiver;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.crazyostudio.to_do_list.MainActivity;
import com.crazyostudio.to_do_list.R;

public class TaskAlarmReceiver extends BroadcastReceiver {
    MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        int alarmId = intent.getIntExtra("alarmId", 0);
        String alarmName = intent.getStringExtra("alarmName");

//        Toast.makeText(context, alarmId, Toast.LENGTH_SHORT).show();
        createNotification(alarmName, alarmId, context);
        Toast.makeText(context, alarmName, Toast.LENGTH_SHORT).show();
        Log.i("stsrennnnnnnnnnn", "onReceive: " + alarmName);
        Log.i("stsrennnnnnnnnnn", "alarmId: " + alarmId);


    }

    private void createNotification(String alarmName, int alarmId, Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification builder;

        int flags;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags = PendingIntent.FLAG_IMMUTABLE;
        } else {
            flags = PendingIntent.FLAG_UPDATE_CURRENT;
        }
        Intent actionIntent = new Intent(context, MainActivity.class);
//        actionIntent.putExtra("alarmId", alarmId);
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(
                context,
                alarmId,
                actionIntent,
                flags
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a notification channel if it doesn't exist (for Android Oreo and above).
            String channelId = "TaskAlarm"; // Use the same channel ID consistently
            builder = new Notification.Builder(context, channelId)
                    .setSmallIcon(R.drawable.ic_baseline_delete_24)
                    .setContentTitle("Alarm Notification")
                    .setContentText(alarmName)
                    .setOngoing(false)
                    .addAction(
                            R.drawable.ic_baseline_calendar_month_24, // Replace with the icon for your button
                            "Stop", // Replace with the text for your button
                            actionPendingIntent
                    )
                    .build();

            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "TaskAlarm",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.enableLights(false);
            channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);
        } else {
            builder = new Notification.Builder(context)
                    .setSmallIcon(R.drawable.ic_baseline_delete_24)
                    .setContentTitle("Alarm Notification")
                    .setContentText(alarmName)
                    .setOngoing(false)
                    .addAction(
                            R.drawable.ic_baseline_calendar_month_24, // Replace with the icon for your button
                            "Button Text", // Replace with the text for your button
                            actionPendingIntent
                    )
                    .build();

        }
        notificationManager.notify(alarmId, builder);
    }
}
