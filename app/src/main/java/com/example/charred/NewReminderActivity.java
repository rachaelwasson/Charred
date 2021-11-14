package com.example.charred;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

// this class can be used to add a new reminder or edit an existing reminder
public class NewReminderActivity extends AppCompatActivity {

    //private static final int NOTIFICATION_REMINDER_NIGHT = 0;
    int reminderid=-1;
    String day;
    int dayInt;
    public static final String CHANNEL_1_ID = "channel1";
    private NotificationManagerCompat notificationManager;
    EditText timeEditText;
    EditText titleEditText;
    static int reminderHour;
    static int reminderMinute;
    static String formattedTime;

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            reminderHour = hourOfDay;
            reminderMinute = minute;
            String formattedHour;
            boolean pm = false;
            if (hourOfDay > 12) {
                formattedHour = String.valueOf(hourOfDay - 12);
                pm = true;
            } else {
                formattedHour = String.valueOf(hourOfDay);
            }
            String formattedMinute = String.valueOf(minute);
            if (formattedMinute.length() < 2) {
                formattedMinute = "0" + formattedMinute;
            }
            if (pm) {
                formattedTime = formattedHour + ":" + formattedMinute + "pm";
            } else {
                formattedTime = formattedHour + ":" + formattedMinute + "am";
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        Intent intent = getIntent();
        day = intent.getStringExtra("day");
        switch (day) {
            case "Monday":
                dayInt = 1;
            case "Tuesday":
                dayInt = 2;
            case "Wednesday":
                dayInt = 3;
            case "Thursday":
                dayInt = 4;
            case "Friday":
                dayInt = 5;
            case "Saturday":
                dayInt = 6;
            case "Sunday":
                dayInt = 7;
        }
        reminderid = intent.getIntExtra("reminderid", -1);

        if (reminderid != -1) {
            Reminder reminder = DailyScheduleActivity.reminders.get(reminderid);
            //String time = reminder.getTime();
            //timeEditText.setText(time);
            String title = reminder.getTitle();
            titleEditText.setText(title);
        }

        notificationManager = NotificationManagerCompat.from(this);
    }

    public void cancelMethod(View view) {
        Intent cancelIntent = new Intent(this, DailyScheduleActivity.class);
        cancelIntent.putExtra("day", day);
        startActivity(cancelIntent);
    }

    public void saveMethod(View view) {
        //String time = String.valueOf(reminderHour) + ":" + String.valueOf(reminderMinute);
        String title = titleEditText.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("reminders", Context.MODE_PRIVATE, null);
        RemindersDBHelper remindersDBHelper= new RemindersDBHelper(sqLiteDatabase);

        if (reminderid == -1) {
            remindersDBHelper.saveReminders(day, formattedTime, title);
        } else {
            remindersDBHelper.updateReminder(day, formattedTime, title);
        }

        Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show();
        sendOnChannel(view);

        Intent intent1 = new Intent(this, DailyScheduleActivity.class);
        intent1.putExtra("day", day);
        startActivity(intent1);
    }

    public void sendOnChannel(View v) {
        //String time = timeEditText.getText().toString();
        //String time = String.valueOf(reminderHour) + ":" + String.valueOf(reminderMinute);
        String title = titleEditText.getText().toString();

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("title", title);
        broadcastIntent.putExtra("day", day);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // if alarm time is passed, schedule for the next day
        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != dayInt
                    && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= reminderHour
                    && Calendar.getInstance().get(Calendar.MINUTE) >= reminderMinute
        ) {
            calendar.add(Calendar.DAY_OF_YEAR, 1); // add, not set!
        }
        calendar.set(Calendar.HOUR_OF_DAY, reminderHour);
        calendar.set(Calendar.MINUTE, reminderMinute);
        calendar.set(Calendar.SECOND, 0);

        //long timeAtButtonClick = System.currentTimeMillis();

        //long fiveSecondsInMillis = 1000 * 5;

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                actionIntent);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}