package com.example.charred;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class NotificationReceiver extends BroadcastReceiver {
    public static final String CHANNEL_1_ID = "channel1";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        String title = intent.getStringExtra("title");
        String day = intent.getStringExtra("day");
        Long alarmTime = intent.getLongExtra("calendar", Calendar.getInstance().getTimeInMillis());

        Intent activityIntent = new Intent(context, DailyScheduleActivity.class);
        activityIntent.putExtra("day", day);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, activityIntent, 0);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_local_fire_department_24)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setOnlyAlertOnce(false)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(1, notification);

        // set alarm for 7 days in the future
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent broadcastIntent = new Intent(context, NotificationReceiver.class);
        broadcastIntent.putExtra("title", title);
        broadcastIntent.putExtra("day", day);
        broadcastIntent.putExtra("calendar", alarmTime+(AlarmManager.INTERVAL_DAY*7));

        PendingIntent actionIntent = PendingIntent.getBroadcast(context,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                alarmTime+(AlarmManager.INTERVAL_DAY*7),
                actionIntent);
    }

}
