package com.example.charred;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class NewJournalActivity extends AppCompatActivity {

    int journalid = -1;
    public SpeechRecognizer speechRecognizer;
    ImageView micButton;
    EditText editText;
    EditText titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal);

        editText = (EditText) findViewById(R.id.textView);
        titleText = (EditText) findViewById(R.id.titleView);
        micButton = (ImageView) findViewById(R.id.micButton);
        Intent intent = getIntent();
        journalid = intent.getIntExtra("journalid", -1);

        if (journalid != -1) {
            Journal journal = JournalActivity.journals.get(journalid);
            String journalContent = journal.getContent();
            editText.setText(journalContent);
            String title = journal.getTitle();
            titleText.setText(title);
        }

        // speech-to-text functionality
        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, 1);
                }
                catch (Exception e) {
                    Toast
                            .makeText(NewJournalActivity.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                editText.setText(
                        Objects.requireNonNull(result).get(0));
            }
        }
    }

    public void saveMethod(View view) {
        editText = (EditText) findViewById(R.id.textView);
        String content = editText.getText().toString();
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("journals", Context.MODE_PRIVATE, null);
        JournalDBHelper dbHelper = new JournalDBHelper(sqLiteDatabase);
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (journalid == -1) {
            //title ="Journal #" + (JournalActivity.journals.size() + 1);
            title = titleText.getText().toString();
            dbHelper.saveJournals(title, content, date);
        } else {
            //title="Journal #" + (journalid + 1);
            title = titleText.getText().toString();
            dbHelper.updateJournal(title, date, content);
        }

        Intent intent = new Intent(this, JournalActivity.class);
        startActivity(intent);
    }

}