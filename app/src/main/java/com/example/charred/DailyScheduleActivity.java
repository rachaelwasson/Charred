package com.example.charred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DailyScheduleActivity extends AppCompatActivity {

    TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedule);

        titleTextView = findViewById(R.id.dailyScheduleTitle);
        Intent intent = getIntent();
        String day = intent.getStringExtra("day");
        titleTextView.setText(day);
    }


}