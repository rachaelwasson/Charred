package com.example.charred;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private NavigationBarView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(bottomnavFunction);

        // request permission from the user to use exact/repeating alarms
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SCHEDULE_EXACT_ALARM}, 1);
            }
        }
    }

    public void onClickHomePage(View view) {
        switch (view.getId()) {
            case (R.id.remindersButton):
                Intent remindersIntent = new Intent(this, RemindersActivity.class);
                startActivity(remindersIntent);
                break;
            case (R.id.helpButton):
                Intent helpIntent = new Intent(this, HelpActivity.class);
                startActivity(helpIntent);
                break;
            case (R.id.journalButton):
                Intent journalIntent = new Intent(this, JournalActivity.class);
                startActivity(journalIntent);
                break;
        }
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
                case R.id.findHelpMenuItem:MenuItem:
                goToHelp();
                    break;
                case R.id.journalMenuItem:
                    goToJournal();
                    break;
            }
            return true;
        }
    };

    public void onButtonClick(View view){
        goToReminders();
    }

}