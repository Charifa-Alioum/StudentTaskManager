package com.example.studenttaskmanagerdetails;

import java.io.Serializable;

public class SubjectItem implements Serializable {
    private String subjectName;
    private int color;
    private boolean ccCheckbox;
    private double ccMark;
    private boolean snCheckbox;
    private double snMark;
    private boolean tpCheckbox;
    private double tpMark;
    private String commentZone;

    public SubjectItem(String subjectName,int color,boolean ccCheckbox,double ccMark,boolean snCheckbox,
                       double snMark,boolean tpCheckbox,double tpMark,String commentZone){
        this.subjectName=subjectName;
        this.color=color;
        this.ccCheckbox=ccCheckbox;
        this.ccMark=ccMark;
        this.snCheckbox=snCheckbox;
        this.snMark=snMark;
        this.tpCheckbox=tpCheckbox;
        this.tpMark=tpMark;
        this.commentZone=commentZone;
    }
    public String getSubjectName(){
        return subjectName;
    }
    public int getColor() {
        return color;
    }
    public boolean isCcCheckbox() {
        return ccCheckbox;
    }
    public double getCcMark() {
        return ccMark;
    }
    public boolean isSnCheckbox() {
        return snCheckbox;
    }
    public double getSnMark() {
        return snMark;
    }
    public boolean isTpCheckbox() {
        return tpCheckbox;
    }
    public double getTpMark() {
        return tpMark;
    }
    public String getCommentZone() {
        return commentZone;
    }

    public void setCcCheckbox(boolean ccCheckbox) {
        this.ccCheckbox = ccCheckbox;
    }

    public void setCcMark(double ccMark) {
        this.ccMark = ccMark;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setCommentZone(String commentZone) {
        this.commentZone = commentZone;
    }

    public void setSnCheckbox(boolean snCheckbox) {
        this.snCheckbox = snCheckbox;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSnMark(double snMark) {
        this.snMark = snMark;
    }

    public void setTpCheckbox(boolean tpCheckbox) {
        this.tpCheckbox = tpCheckbox;
    }

    public void setTpMark(double tpMark) {
        this.tpMark = tpMark;
    }
}
