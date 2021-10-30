package com.example.charred;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationBarView;

public class RemindersActivity extends AppCompatActivity {
    private NavigationBarView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        bottomNavigationView = findViewById(R.id.bottomNav);
    }
}