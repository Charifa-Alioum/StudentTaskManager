package com.example.studenttaskmanagerdetails;

import java.util.Calendar;

public class HomeworkItem {
    private String name;
    private boolean checked;
    private Calendar dateDue;

    public HomeworkItem(String name,boolean checked,Calendar dateDue){
        this.name=name;
        this.checked=checked;
        this.dateDue=dateDue;
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return checked;
    }

    public Calendar getDateDue(){return dateDue; }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
