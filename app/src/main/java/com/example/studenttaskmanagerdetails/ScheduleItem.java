package com.example.studenttaskmanagerdetails;

public class ScheduleItem {
    private String subjectName;
    private String date;
    private String startTime;
    private String endTime;

    public ScheduleItem(String subjectName, String date, String startTime, String endTime) {
        this.subjectName = subjectName;
        this.date = date;
        this.startTime = startTime;
        this.endTime=endTime;
    }

    public String getSubjectName() {
        return subjectName;
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

    @Override
    public String toString(){
        return subjectName + " (" + startTime + " - " + endTime + ") ";
    }
}
