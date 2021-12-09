package com.example.charred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class DailyScheduleActivity extends AppCompatActivity {

    TextView titleTextView;
    private NavigationBarView bottomNavigationView;
    public static ArrayList<Reminder> reminders = new ArrayList<>();
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedule);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(bottomnavFunction);

        titleTextView = findViewById(R.id.dailyScheduleTitle);
        Intent intent = getIntent();
        day = intent.getStringExtra("day");
        titleTextView.setText(day);

        // Load reminders from database
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("reminders", Context.MODE_PRIVATE, null);

        RemindersDBHelper dbHelper = new RemindersDBHelper(sqLiteDatabase);
        reminders = dbHelper.readReminders(day);

        ArrayList<String> displayReminders = new ArrayList<>();
        for (Reminder reminder: reminders) {

            // order the reminders chronologically
            boolean added = false;
            int numReminders = displayReminders.size();

            // edge case where new reminder is inserted at the start of the list
            if (numReminders > 0) {
                String r = displayReminders.get(0);
                String[] splitString = r.split(" ");
                String rTime = splitString[0];
                String[] splitTime = rTime.split(":");
                int rHour = Integer.valueOf(splitTime[0]);
                int rMinute = Integer.valueOf(splitTime[1].substring(0, 2));
                String newTime = reminder.getTime();
                String[] newSplitTime = newTime.split(":");
                int newHour = Integer.valueOf(newSplitTime[0]);
                int newMinute = Integer.valueOf(newSplitTime[1].substring(0, 2));
                // find the next latest time, and insert reminder immediately before
                if (reminder.getTime().contains("am")) {
                    if (r.contains("am")) {
                        if (newHour < rHour) {
                            displayReminders.add(0, String.format("%s: %s", reminder.getTime(), reminder.getTitle()));
                            added = true;
                        } else if (newHour == rHour) {
                            if (newMinute < rMinute) {
                                displayReminders.add(0, String.format("%s: %s", reminder.getTime(), reminder.getTitle()));
                                added = true;
                            }
                        }
                    } else if (r.contains("pm")) {
                        displayReminders.add(0, String.format("%s: %s", reminder.getTime(), reminder.getTitle()));
                        added = true;
                    }
                } else if (reminder.getTime().contains("pm")) {
                    if (r.contains("pm")) {
                        if (newHour < rHour) {
                            displayReminders.add(0, String.format("%s: %s", reminder.getTime(), reminder.getTitle()));
                            added = true;
                        } else if (newHour == rHour) {
                            if (newMinute < rMinute) {
                                displayReminders.add(0, String.format("%s: %s", reminder.getTime(), reminder.getTitle()));
                                added = true;
                            }
                        }
                    }
                }
            }

            for (int i=1; i<numReminders; i++) {
                String r = displayReminders.get(i);
                String[] splitString = r.split(" ");
                String rTime = splitString[0];
                String[] splitTime = rTime.split(":");
                int rHour = Integer.valueOf(splitTime[0]);
                int rMinute = Integer.valueOf(splitTime[1].substring(0,2));
                String newTime = reminder.getTime();
                String[] newSplitTime = newTime.split(":");
                int newHour = Integer.valueOf(newSplitTime[0]);
                int newMinute = Integer.valueOf(newSplitTime[1].substring(0,2));
                // find the next latest time, and insert reminder immediately before
                if (reminder.getTime().contains("am")) {
                    if (r.contains("am")) {
                        if (newHour < rHour) {
                            displayReminders.add(i, String.format("%s: %s", reminder.getTime(), reminder.getTitle()));
                            added = true;
                        } else if (newHour == rHour) {
                            if (newMinute < rMinute) {
                                displayReminders.add(i, String.format("%s: %s", reminder.getTime(), reminder.getTitle()));
                                added = true;
                            }
                        }
                    } else if (r.contains("pm")) {
                        displayReminders.add(i, String.format("%s: %s", reminder.getTime(), reminder.getTitle()));
                        added = true;
                    }
                } else if (reminder.getTime().contains("pm")) {
                    if (r.contains("pm")) {
                        if (newHour < rHour) {
                            displayReminders.add(i, String.format("%s: %s", reminder.getTime(), reminder.getTitle()));
                            added = true;
                        } else if (newHour == rHour) {
                            if (newMinute < rMinute) {
                                displayReminders.add(i, String.format("%s: %s", reminder.getTime(), reminder.getTitle()));
                                added = true;
                            }
                        }
                    }
                }
            }
            if (!added) {
                displayReminders.add(String.format("%s: %s", reminder.getTime(), reminder.getTitle()));
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.black_text_list_item, displayReminders);
        ListView listView = (ListView) findViewById(R.id.remindersListView);
        listView.setAdapter(adapter);

        // clicking a list item opens up a dialog to delete or edit note
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putInt("position", position);
                args.putLong("id", id);
                openDialog(args);
            }
        });

    }

    public void openDialog(Bundle args) {
        ReminderDialog reminderDialog = new ReminderDialog();
        reminderDialog.setArguments(args);
        reminderDialog.show(getSupportFragmentManager(), "new reminder dialog");
    }

    public void goToReminders() {
        Intent remindersIntent = new Intent(this, RemindersActivity.class);
        startActivity(remindersIntent);
    }

    public void goToHome() {
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
    }
    public void goToHelp() {
        Intent homeIntent = new Intent(this, HelpActivity.class);
        startActivity(homeIntent);
    }

    public void goToJournal() {
        Intent homeIntent = new Intent(this, JournalActivity.class);
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
                case R.id.findHelpMenuItem:
                    goToHelp();
                    break;
                case R.id.journalMenuItem:
                    goToJournal();
                    break;
            }
            return true;
        }
    };

    // I can't figure out a way to get multiple inputs from a dialog box,
    // so changing this part of the design to a new activity like we did in Lab 5
    public void onNewReminderButtonClick(View view) {
        Intent newReminderIntent = new Intent(this, NewReminderActivity.class);
        newReminderIntent.putExtra("day", day);
        startActivity(newReminderIntent);
    }

}