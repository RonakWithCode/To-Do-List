package com.crazyostudio.to_do_list.Receiver;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.crazyostudio.to_do_list.R;

public class AlarmUtils  {
//    private static final String CHANNEL_ID = "alarmId";
//    private static final String CHANNEL_NAME = "Alarm Channel";
////    private static final int NOTIFICATION_ID = 1; // Unique ID for the notification.
//
//
//
//    public static void cancelAlarm(Context context, int alarmId) {
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, TaskAlarmReceiver.class);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, flags);
//
//        alarmManager.cancel(pendingIntent);
//    }
//
//    public void showNotification(Context context, int alarmId, String alarmName) {
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
//            channel.enableLights(true);
//            channel.setLightColor(Color.RED);
//            channel.enableVibration(true);
//
//            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_baseline_delete_24)
//                .setContentTitle("Alarm")
//                .setContentText(alarmName)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri);
//        notificationManager.notify(alarmId, builder.build());
//        Notification notification = builder.build();
//
//        startForeground(alarmId,notification);
//
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
}