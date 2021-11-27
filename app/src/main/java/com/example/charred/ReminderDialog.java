package com.example.charred;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ReminderDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle mArgs = getArguments();
        int position = mArgs.getInt("position");
        int id = mArgs.getInt("id");
        Reminder reminder = DailyScheduleActivity.reminders.get(position);
        String day = reminder.getDay();
        String time = reminder.getTime();
        String title = reminder.getTitle();
        Context context = getContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("reminders", Context.MODE_PRIVATE, null);
        RemindersDBHelper remindersDBHelper= new RemindersDBHelper(sqLiteDatabase);
        builder.setTitle("Edit Reminder")
                .setMessage("Would you like to delete or edit this reminder?")
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, NewReminderActivity.class);
                        intent.putExtra("reminderid", position);
                        intent.putExtra("day", day);
                        context.startActivity(intent);

                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remindersDBHelper.deleteReminder(day, time, title);
                        Intent intent = new Intent(context, DailyScheduleActivity.class);
                        intent.putExtra("day", day);
                        context.startActivity(intent);
                    }
                });
        return builder.create();
    }
}
