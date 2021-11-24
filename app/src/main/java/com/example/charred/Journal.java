package com.example.charred;

public class Journal {

    private String date;
    private String title;
    private String content;

    public Journal(String date, String title, String content) {
        this.date = date;
        this.title = title;
        this.content = content;
    }

    public String getDate() { return date; }

    public String getTitle() { return title; }

    public String getContent() { return content; }
}
