package com.example.charred;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

// this class can be used to add a new reminder or edit an existing reminder
public class NewReminderActivity extends AppCompatActivity {

    //private static final int NOTIFICATION_REMINDER_NIGHT = 0;
    int reminderid=-1;
    String day;
    public static final String CHANNEL_1_ID = "channel1";
    private NotificationManagerCompat notificationManager;
    EditText timeEditText;
    EditText titleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);

        timeEditText = (EditText) findViewById(R.id.timeEditText);
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        Intent intent = getIntent();
        day = intent.getStringExtra("day");
        reminderid = intent.getIntExtra("reminderid", -1);

        if (reminderid != -1) {
            Reminder reminder = DailyScheduleActivity.reminders.get(reminderid);
            String time = reminder.getTime();
            timeEditText.setText(time);
            String title = reminder.getTitle();
            titleEditText.setText(title);
        }

        //createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(this);
    }

    public void cancelMethod(View view) {
        Intent cancelIntent = new Intent(this, DailyScheduleActivity.class);
        cancelIntent.putExtra("day", day);
        startActivity(cancelIntent);
    }

    public void saveMethod(View view) {
        String time = timeEditText.getText().toString();
        String title = titleEditText.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("reminders", Context.MODE_PRIVATE, null);
        RemindersDBHelper remindersDBHelper= new RemindersDBHelper(sqLiteDatabase);

        if (reminderid == -1) {
            remindersDBHelper.saveReminders(day, time, title);
        } else {
            remindersDBHelper.updateReminder(day, time, title);
        }

        Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show();
        sendOnChannel(view);

        Intent intent1 = new Intent(this, DailyScheduleActivity.class);
        intent1.putExtra("day", day);
        startActivity(intent1);
    }

    public void sendOnChannel(View v) {
        String time = timeEditText.getText().toString();
        String title = titleEditText.getText().toString();

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("title", title);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timeAtButtonClick = System.currentTimeMillis();

        long fiveSecondsInMillis = 1000 * 5;

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                timeAtButtonClick + fiveSecondsInMillis,
                actionIntent);
    }



}