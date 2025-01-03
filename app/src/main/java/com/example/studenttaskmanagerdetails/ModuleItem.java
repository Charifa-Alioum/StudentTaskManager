package com.example.studenttaskmanagerdetails;

public class ModuleItem {
    private String moduleName;
    private int creditNumber;

    public ModuleItem(String moduleName, int creditNumber){
        this.moduleName=moduleName;
        this.creditNumber=creditNumber;
    }

    public String getModuleName(){
        return moduleName;
    }

    public int getCreditNumber() {
        return creditNumber;
    }
}
