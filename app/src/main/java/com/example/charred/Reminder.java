package com.example.charred;

public class Reminder {

    private String day;
    private String time;
    private String title;

    public Reminder(String day, String time, String title) {
        this.day = day;
        this.time = time;
        this.title = title;
    }

    public String getDay() { return day; }

    public String getTime() { return time; }

    public String getTitle() { return title; }
}
