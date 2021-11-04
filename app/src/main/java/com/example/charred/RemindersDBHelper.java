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
                "(id INTEGER PRIMARY KEY, day TEXT, time TEXT, title TEXT, src TEXT)");
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

    public void saveReminders(String day, String time, String title) {
        createTable();
        sqLiteDatabase.execSQL(String.format("INSERT INTO reminders (day, time, title) VALUES ('%s', '%s', '%s')",
                day, time, title));
    }

    public void updateReminder(String day, String time, String title) {
        createTable();
        sqLiteDatabase.execSQL(String.format("Update reminders set time = '%s' where title = '%s' and day = '%s'",
                time, title, day));
    }

    public void deleteReminder(String day, String time, String title) {
        createTable();
        sqLiteDatabase.execSQL(String.format("DELETE FROM reminders WHERE day = '%s' and time = '%s' and title = '%s'",
                day, time, title));
        // DELETE FROM reminders WHERE title=Eat lunch!
        //return sqLiteDatabase.delete("reminders", "title" + "=" + title, null) > 0;
    }
}
