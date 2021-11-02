package com.example.charred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class DailyScheduleActivity extends AppCompatActivity {

    TextView titleTextView;
    private NavigationBarView bottomNavigationView;
    public static ArrayList<Reminder> reminders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedule);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(bottomnavFunction);

        titleTextView = findViewById(R.id.dailyScheduleTitle);
        Intent intent = getIntent();
        String day = intent.getStringExtra("day");
        titleTextView.setText(day);

        // Load reminders from database
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("reminders", Context.MODE_PRIVATE, null);

        RemindersDBHelper dbHelper = new RemindersDBHelper(sqLiteDatabase);
        reminders = dbHelper.readReminders(day);

        ArrayList<String> displayReminders = new ArrayList<>();
        for (Reminder reminder: reminders) {
            displayReminders.add(String.format("%s-%s: ", reminder.getStartTime(), reminder.getEndTime(), reminder.getTitle()));
        }

        // TODO useListView view to display reminders on screen

    }

    public void goToReminders() {
        Intent remindersIntent = new Intent(this, RemindersActivity.class);
        startActivity(remindersIntent);
    }

    public void goToHome() {
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
    }

    private NavigationBarView.OnItemSelectedListener bottomnavFunction = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.remindersMenuItem:
                    goToReminders();
                    break;
                case R.id.homeMenuItem:
                    goToHome();
                    break;
            }
            return true;
        }
    };

    public void onButtonClick(View view) {
        openDialog();
    }

    public void openDialog() {
        NewReminderDialog newReminderDialog = new NewReminderDialog();
        newReminderDialog.show(getSupportFragmentManager(), "new reminder dialog");
    }

}