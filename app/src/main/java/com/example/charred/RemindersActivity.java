package com.example.charred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationBarView;

public class RemindersActivity extends AppCompatActivity {
    private NavigationBarView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(bottomnavFunction);
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

    public void goToDailySchedule(String day) {
        Intent dayIntent = new Intent(this, DailyScheduleActivity.class);
        dayIntent.putExtra("day", day);
        startActivity(dayIntent);
    }

    // can we use only one class for each day, updating the schedule/page text with params?
    // maybe? otherwise we'll need a class for each day
    public void onButtonClick(View view, String day) {
//        switch (view.getId()) {
//            case (R.id.mondayButton):
//                goToDailySchedule("Monday");
//                break;
//            case (R.id.tuesdayButton):
//                goToDailySchedule("Tuesday");
//                break;
//            case (R.id.wednesdayButton):
//                goToDailySchedule("Wednesday");
//                break;
//        }
        goToDailySchedule("Other Day");
    }
}