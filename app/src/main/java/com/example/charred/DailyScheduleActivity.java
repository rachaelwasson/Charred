package com.example.charred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayReminders);
        ListView listView = (ListView) findViewById(R.id.remindersListView);
        listView.setAdapter(adapter);

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

    // I can't figure out a way to get multiple inputs from a dialog box,
    // so changing this part of the design to a new activity like we did in Lab 5
//    public void onButtonClick(View view) {
//        openDialog();
//    }
    public void onButtonClick(View view) {
        Intent newReminderIntent = new Intent(this, NewReminderActivity.class);
        startActivity(newReminderIntent);
    }

    public void openDialog() {
        NewReminderDialog newReminderDialog = new NewReminderDialog();
        newReminderDialog.show(getSupportFragmentManager(), "new reminder dialog");
    }

}