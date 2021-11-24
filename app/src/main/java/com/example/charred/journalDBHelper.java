package com.example.charred;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class journalDBHelper {

    SQLiteDatabase sqLiteDatabase;

    public journalDBHelper (SQLiteDatabase sqLiteDatabase) { this.sqLiteDatabase = sqLiteDatabase; }

    public void createTable() {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS journals " +
                "(id INTEGER PRIMARY KEY, date TEXT, title TEXT, content TEXT, src TEXT)");
    }

    public ArrayList<Journal> readJournals() {

        createTable();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * from journals"), null);

        int dateIndex = c.getColumnIndex("date");
        int titleIndex = c.getColumnIndex("title");
        int contentIndex = c.getColumnIndex("content");

        c.moveToFirst();

        ArrayList<Journal> journalsList = new ArrayList<>();

        while (!c.isAfterLast()) {

            String title = c.getString(titleIndex);
            String date = c.getString(dateIndex);
            String content = c.getString(contentIndex);

            Journal journal = new Journal(date, title, content);
            journalsList.add(journal);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();

        return journalsList;
    }

    public void saveJournals(String title, String content, String date) {
        createTable();
        sqLiteDatabase.execSQL(String.format("INSERT INTO journals (date, title, content) VALUES ('%s', '%s', '%s')",
                date, title, content));
    }

    public void updateJournal(String title, String date, String content) {
        createTable();
        sqLiteDatabase.execSQL(String.format("UPDATE journals set content = '%s', date = '%s' where title = '%s'",
                content, date, title));
    }
}
