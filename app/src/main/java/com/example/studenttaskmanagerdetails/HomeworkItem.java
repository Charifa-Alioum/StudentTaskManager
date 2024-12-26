package com.example.studenttaskmanagerdetails;

public class HomeworkItem {
    private String name;
    private boolean checked;

    public HomeworkItem(String name,boolean checked){
        this.name=name;
        this.checked=checked;
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
