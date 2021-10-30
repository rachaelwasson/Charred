package com.example.charred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private NavigationBarView bottomNavigationView;
    Intent remindersIntent = new Intent(this, RemindersActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNav);
        //bottomNavigationView.setOnItemSelectedListener(bottomnavFunction);
    }

//    private NavigationBarView.OnItemSelectedListener bottomnavFunction = new NavigationBarView.OnItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.remindersMenuItem:
//                    startActivity(remindersIntent);
//                    break;
//            }
//            return true;
//        }
//    };

}