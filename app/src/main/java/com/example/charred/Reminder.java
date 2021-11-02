package com.example.charred;

public class Reminder {

    private String day;
    private String startTime;
    private String endTime;
    private String title;

    public Reminder(String day, String startTime, String endTime, String title) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
    }

    public String getDay() { return day; }

    public String getStartTime() { return startTime; }

    public String getEndTime() { return endTime; }

    public String getTitle() { return title; }
}
