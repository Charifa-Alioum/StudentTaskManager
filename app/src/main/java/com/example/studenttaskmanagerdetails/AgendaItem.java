package com.example.studenttaskmanagerdetails;

import java.io.Serializable;
import java.util.List;

public class AgendaItem implements Serializable {
    private String title;
    private String date;
    private String time;
    private Boolean emergencyLevel;

    public AgendaItem(String title,String date, String time, Boolean emergencyLevel) {
        this.title=title;
        this.date = date;
        this.time=time;
        this.emergencyLevel=emergencyLevel;
    }

    public String getTitle(){ return title;}

    public String getDate() {
        return date;
    }

    public String getTime(){ return time;}

    public Boolean getEmergencyLevel() {
        return emergencyLevel;
    }

    @Override
    public String toString(){
        return title + " (" + date + " " + time + ") ";
    }
}
