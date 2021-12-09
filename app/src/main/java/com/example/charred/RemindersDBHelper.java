package com.example.charred;

import android.app.AlarmManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

// Based on DBHelper class from Lab 5, but instead of selecting
// based on username, select based on day
public class RemindersDBHelper {

    SQLiteDatabase sqLiteDatabase;

    public RemindersDBHelper (SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void createTable() {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS reminders " +
                "(id INTEGER PRIMARY KEY, day TEXT, time TEXT, title TEXT, alarmid TEXT, src TEXT)");
    }

    public ArrayList<Reminder> readReminders(String day) {

        createTable();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * from reminders where day like '%s'", day), null);

        int timeIndex = c.getColumnIndex("time");
        int titleIndex = c.getColumnIndex("title");

        c.moveToFirst();

        ArrayList<Reminder> remindersList = new ArrayList<>();

        while (!c.isAfterLast()) {

            String time = c.getString(timeIndex);
            String title = c.getString(titleIndex);

            Reminder reminder = new Reminder(day, time, title);
            remindersList.add(reminder);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();

        return remindersList;
    }

    public void saveReminders(String day, String time, String title, String alarmid) {
        createTable();
        sqLiteDatabase.execSQL(String.format("INSERT INTO reminders (day, time, title, alarmid) VALUES ('%s', '%s', '%s', '%s')",
                day, time, title, alarmid));
    }

//    public void updateReminder(String day, String time, String title, int alarmid) {
//        createTable();
//        sqLiteDatabase.execSQL(String.format("Update reminders set time = '%s' where title = '%s' and day = '%s'",
//                time, title, day));
//    }

    public int deleteReminder(String day, String time, String title) {
        // get alarmid from database before deleting entry, returned in order to cancel alarm
        createTable();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * from reminders where day like '%s' " +
                "and time like '%s' and title like '%s'", day, time, title), null);
        int alarmidIndex = c.getColumnIndex("alarmid");
        c.moveToFirst();
        int alarmid = Integer.valueOf(c.getString(alarmidIndex));
        c.close();

        sqLiteDatabase.execSQL(String.format("DELETE FROM reminders WHERE day = '%s' and time = '%s' and title = '%s'",
                day, time, title));

        sqLiteDatabase.close();

        return alarmid;

    }
}
