package com.backend_code;

/**
 * Created by Andy on 8/18/2017. Basic gpa calculation class, stores total hours at a particular grade point
 * use case is meant to be initially setting gradePoints (IE A B etc) then updating hours. Includes getter and setter
 * for gradepoints as well, though I would expect to not actually use those.
 */

public class GPACalculation {
    private int hours;
    private double gradePoints;
    private String semesterOrClassName;


    /**
     * existing gpa constructor
     * @param hours credit hours attempted
     * @param gradePoints grade earned for these credit hours (IE A = 4.0 A- = 3.7 etc.
     */
    public GPACalculation(int hours, double gradePoints){
        this.hours = hours;
        this.gradePoints= (gradePoints * hours);
    }

    /**
     * no existing gpa constructor
     */
    public GPACalculation(){
        this.hours = 0;
        this.gradePoints= 0;
    }

    public void addPointsAndHours(int hours, double grade){
        this.hours += hours;
        this.gradePoints += grade * hours;
    }

    /**
     * calculate cumulative gpa based on existing hours and grade points
     * @return cumulative gpa
     */
    public double calculateGPA(){
        //avoid divide by 0
        if(this.hours != 0) {
            return this.gradePoints / this.hours;
        }else{
            return 0;
        }
    }
    /**
     * updates number of hours stored in GPACalculation
     * @param hours hours to change by
     */
    public void setHours (int hours) {
        this.hours = hours;
    }

    //public getters and setters
    public int getHours(){
        return this.hours;
    }

    public double getGradePoints() {
        return this.gradePoints;
    }

    public void setGradePoints(double points) {
        this.gradePoints = points;
    }

    public void setSemesterOrClassName (String name){
        this.semesterOrClassName = name;
    }
    public String getSemesterOrClassName(){
        return this.semesterOrClassName;
    }
    public void addOrSubtractHours (int hours) {
        this.hours += hours;
    }

    /**
     * calculate raw points
     * @param hours hours at a particular gradepoint
     * @param gradePoints grade
     * @return
     */
    private double totalPoints(int hours, double gradePoints){
        return hours * gradePoints;
    }
}
