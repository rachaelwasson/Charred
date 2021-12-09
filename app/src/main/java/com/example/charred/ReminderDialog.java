package com.example.charred;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
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
        Reminder reminder = DailyScheduleActivity.reminders.get(position);
        String day = reminder.getDay();
        String time = reminder.getTime();
        String title = reminder.getTitle();
        Context context = getContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("reminders", Context.MODE_PRIVATE, null);
        RemindersDBHelper remindersDBHelper= new RemindersDBHelper(sqLiteDatabase);
        builder.setTitle("Delete Reminder")
                .setMessage("Would you like to delete this reminder?")
                .setNegativeButton("Yes, delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int alarmid = remindersDBHelper.deleteReminder(day, time, title);

                        // cancel alarm
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        Intent myIntent = new Intent(context.getApplicationContext(), NotificationReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                context.getApplicationContext(), alarmid, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.cancel(pendingIntent);

                        Intent intent = new Intent(context, DailyScheduleActivity.class);
                        intent.putExtra("day", day);
                        context.startActivity(intent);

                    }
                })
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, DailyScheduleActivity.class);
                        intent.putExtra("day", day);
                        context.startActivity(intent);
                    }
                });
        return builder.create();
    }
}
