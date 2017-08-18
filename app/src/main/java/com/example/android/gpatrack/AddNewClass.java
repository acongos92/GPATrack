package com.example.android.gpatrack;
import com.example.android.gpatrack.GPACalculation;
import

import static android.provider.Settings.Global.getString;

/**
 * Created by zigza on 8/18/2017.
 */

public class AddNewClass {
    //constants
    private static final String NO_NAME = "No name provided";

    //instance variables
    private String className;
    private int creditHours;
    private String alternateGrade;
    private String grade;
    private GPACalculation gradePoints;

    /**
     * constructor to include class name
     * @param className name of class taken
     * @param creditHours credit hours to include with class
     * @param grade grade earned in class, must be A B C D, all others treated as E
     */
    public AddNewClass(String className, int creditHours, String grade) {
        this.className = className;
        this.creditHours = creditHours;
        if (grade.equalsIgnoreCase("a") || grade.equalsIgnoreCase("b") || grade.equalsIgnoreCase("c") || grade.equalsIgnoreCase("d")) {
            this.grade = grade;
        } else {
            this.grade = "E";
        }
    }

    /**
     * no class name given constructor
     * @param creditHours
     * @param grade
     */
    public AddNewClass(int creditHours, String grade) {
        this.className = NO_NAME;
        this.creditHours = creditHours;
        if (grade.equalsIgnoreCase("a") || grade.equalsIgnoreCase("b") || grade.equalsIgnoreCase("c") || grade.equalsIgnoreCase("d")) {
            this.grade = grade;
        } else {
            this.grade = "E";
        }
    }


    //Getters and setters
    public String getClassName() {
        //comment
        return this.className;
    }

    public void setClassName (String className) {
        //comment
        this.className = className;
    }
    public String getGrade(){
        //commenjt
        return this.grade;
    }
    public void setGrade(String grade) {
        //comment
        this.grade = grade;

    }
    public void setAlternateGrade(String alternateGrade) {
        //comment
        this.alternateGrade = alternateGrade;
    }
    public String getAlternateGrade(){
        //comment
        return this.alternateGrade;
    }

}
