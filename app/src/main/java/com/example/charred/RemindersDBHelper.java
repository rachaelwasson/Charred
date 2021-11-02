package com.example.charred;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

// Based on DBHelper class from Lab 5, but instead of selecting
// based on username, select based on day (not sure if this will work tho lol)
public class RemindersDBHelper {

    SQLiteDatabase sqLiteDatabase;

    public RemindersDBHelper (SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void createTable() {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS reminders " +
                "(id INTEGER PRIMARY KEY, day TEXT, startTime TEXT, endTime TEXT, title TEXT, src TEXT)");
    }

    public ArrayList<Reminder> readReminders(String day) {

        createTable();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * from reminders where day like '%s'", day), null);

        int startTimeIndex = c.getColumnIndex("startTime");
        int endTimeIndex = c.getColumnIndex("endTime");
        int titleIndex = c.getColumnIndex("title");

        c.moveToFirst();

        ArrayList<Reminder> remindersList = new ArrayList<>();

        while (!c.isAfterLast()) {

            String startTime = c.getString(startTimeIndex);
            String endTime = c.getString(endTimeIndex);
            String title = c.getString(titleIndex);

            Reminder reminder = new Reminder(day, startTime, endTime, title);
            remindersList.add(reminder);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();

        return remindersList;
    }

    public void saveReminders(String day, String startTime, String endTime, String title) {
        createTable();
        sqLiteDatabase.execSQL(String.format("INSERT INTO notes (day, startTime, endTime, title) VALUES ('%s', '%s', '%s', '%s')",
                day, startTime, endTime, title));
    }

    public void updateReminder(String day, String startTime, String endTime, String title) {
        createTable();
        sqLiteDatabase.execSQL(String.format("Update notes set startTime = '%s', endTime = '%s' where title = '%s' and day = '%s'",
                startTime, endTime, title, day));
    }
}
