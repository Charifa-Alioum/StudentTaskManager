package com.example.studenttaskmanagerdetails;

public class ScheduleItem {
    private String subject;
    private String date;
    private String startTime;
    private String endTime;

    public ScheduleItem(String subject, String date, String startTime, String endTime) {
        this.subject = subject;
        this.date = date;
        this.startTime = startTime;
        this.endTime=endTime;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime(){
        return endTime;
    }
}
