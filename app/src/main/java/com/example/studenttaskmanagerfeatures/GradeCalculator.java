package com.example.studenttaskmanagerfeatures;

public class GradeCalculator {

    public static double calculateAverage(double noteCC, boolean checkCC, double noteSN,
                                          boolean checkSN, double noteTP, boolean checkTP){
        int checkedCount=0;
        if(checkCC) checkedCount++;
        if(checkSN) checkedCount++;
        if(checkTP) checkedCount++;

        double average=0;

        switch (checkedCount){
            case 3:
                average=(noteCC * 0.33)+(noteSN * 0.42)+(noteTP * 0.25);
                break;
            case 2:
                average=(noteCC * 0.44)+(noteSN * 0.56)+(noteTP * 0.0);
                break;
            case 1:
                average=(noteCC * 1.00)+(noteSN * 0.0)+(noteTP * 0.0);
                break;
            default:
                throw new IllegalArgumentException("Aucune case cochée.Veuillez cocher au moins une case");
        }
        return average;
    }
}
