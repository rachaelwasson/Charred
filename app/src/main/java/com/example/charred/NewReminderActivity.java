package com.example.charred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

// this class can be used to add a new reminder or edit an existing reminder
public class NewReminderActivity extends AppCompatActivity {

    int reminderid=-1;
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);

        EditText timeEditText = (EditText) findViewById(R.id.timeEditText);
        EditText titleEditText = (EditText) findViewById(R.id.titleEditText);
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
    }

    public void cancelMethod(View view) {
        Intent cancelIntent = new Intent(this, DailyScheduleActivity.class);
        cancelIntent.putExtra("day", day);
        startActivity(cancelIntent);
    }

    public void saveMethod(View view) {
        EditText timeEditText = (EditText) findViewById(R.id.timeEditText);
        String time = timeEditText.getText().toString();
        EditText titleEditText = (EditText) findViewById(R.id.titleEditText);
        String title = titleEditText.getText().toString();
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("reminders", Context.MODE_PRIVATE, null);
        RemindersDBHelper remindersDBHelper= new RemindersDBHelper(sqLiteDatabase);

        if (reminderid == -1) {
            remindersDBHelper.saveReminders(day, time, title);
        } else {
            remindersDBHelper.updateReminder(day, time, title);
        }

        Intent intent = new Intent(this, DailyScheduleActivity.class);
        intent.putExtra("day", day);
        startActivity(intent);
    }
}