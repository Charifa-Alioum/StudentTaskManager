package com.example.studenttaskmanagerdetails;

import java.util.List;

public class AgendaItem {
    private String date;
    private List<String> events;
    private Boolean emergencyLevel;

    public AgendaItem(String date, List<String> events, Boolean emergencyLevel) {
        this.date = date;
        this.events = events;
        this.emergencyLevel=emergencyLevel;
    }

    public String getDate() {
        return date;
    }

    public List<String> getEvents() {
        return events;
    }

    public Boolean getEmergencyLevel() {
        return emergencyLevel;
    }
}
