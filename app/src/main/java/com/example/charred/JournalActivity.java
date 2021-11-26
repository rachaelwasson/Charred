package com.example.charred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class JournalActivity extends AppCompatActivity {
    private NavigationBarView bottomNavigationView;
    public static ArrayList<Journal> journals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(bottomnavFunction);

        // Get SQLiteDatabase instance.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("journals", Context.MODE_PRIVATE, null);

        // Initiate the "journals" class variable using readJournals method implemented in journalsDBHelper class.
        journalDBHelper dbHelper = new journalDBHelper(sqLiteDatabase);
        journals = dbHelper.readJournals();

        // Create an ArrayList<Journal> object by iterating over journals object.
        ArrayList<String> displayJournals = new ArrayList<>();
        for (Journal journal : journals) {
            displayJournals.add(String.format("Title: %s\nDate: %s", journal.getTitle(), journal.getDate()));
        }

        // Use ListView view to display journals on screen.
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.black_text_list_item, displayJournals);
        ListView listView = (ListView) findViewById(R.id.journalsListView);
        listView.setAdapter(adapter);

        // Add onItemClickListener for journal item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NewJournalActivity.class);
                intent.putExtra("journalid", position);
                startActivity(intent);
            }
        });

    }

    public void addJournal(View view) {
        Intent intent = new Intent(this, NewJournalActivity.class);
        startActivity(intent);
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
}