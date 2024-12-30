package com.example.studenttaskmanagerdetails;

public class SubjectItem {
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
}
