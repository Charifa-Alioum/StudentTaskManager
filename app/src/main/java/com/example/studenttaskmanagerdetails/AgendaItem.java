package com.example.studenttaskmanagerdetails;

import java.util.List;

public class AgendaItem {
    private String date;
    private List<String> events;

    public AgendaItem(String date, List<String> events) {
        this.date = date;
        this.events = events;
    }

    public String getDate() {
        return date;
    }

    public List<String> getEvents() {
        return events;
    }
}
