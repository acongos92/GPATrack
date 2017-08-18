package com.example.android.gpatrack;

/**
 * Created by Andy on 8/18/2017.
 */

public class GPACalculation {
    private int hours;
    private double gradePoints;

    /**
     * existing gpa constructor
     * @param hours credit hours attempted
     * @param gradePoints grade earned for these credit hours (IE A = 4.0 A- = 3.7 etc.
     */
    public GPACalculation(int hours, double gradePoints){
        this.hours = hours;
        this.gradePoints= gradePoints;
    }
    /**
     * no existing gpa constructor
     */
    public GPACalculation(){
        this.hours = 0;
        this.gradePoints= 0;
    }


    /**
     * calculate cumulative gpa based on existing hours and grade points
     * @return cumulative gpa
     */
    public double calculateGPA(){
        return totalPoints(this.hours,this.gradePoints)/this.hours;
    }

    /**
     * calculate raw points
     * @param hours
     * @param gradePoints
     * @return
     */
    private double totalPoints(int hours, double gradePoints){
        return hours * gradePoints;
    }
}
