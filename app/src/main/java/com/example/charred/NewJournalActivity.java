package com.example.charred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class NewJournalActivity extends AppCompatActivity {

    int journalid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal);

        EditText editText = (EditText) findViewById(R.id.textView);
        Intent intent = getIntent();
        journalid = intent.getIntExtra("journalid", -1);

        if (journalid != -1) {
            Journal journal = JournalActivity.journals.get(journalid);
            String journalContent = journal.getContent();
            editText.setText(journalContent);
        }
    }

    public void saveMethod(View view) {
        EditText editText = (EditText) findViewById(R.id.textView);
        String content = editText.getText().toString();
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("journals", Context.MODE_PRIVATE, null);
        journalDBHelper dbHelper = new journalDBHelper(sqLiteDatabase);
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (journalid == -1) {
            title ="Journal #" + (JournalActivity.journals.size() + 1);
            dbHelper.saveJournals(title, content, date);
        } else {
            title="Journal #" + (journalid + 1);
            dbHelper.updateJournal(title, date, content);
        }

        Intent intent = new Intent(this, JournalActivity.class);
        startActivity(intent);
    }
}