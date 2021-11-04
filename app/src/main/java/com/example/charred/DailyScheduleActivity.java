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
            Log.i("reminder", reminder.getTitle());
            displayReminders.add(String.format("%s: %s", reminder.getTime(), reminder.getTitle()));
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
    public void onNewReminderButtonClick(View view) {
        Intent newReminderIntent = new Intent(this, NewReminderActivity.class);
        newReminderIntent.putExtra("day", day);
        startActivity(newReminderIntent);
    }

}