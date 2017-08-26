package com.backing_code;


import com.constants.Constants;

import java.util.logging.Logger;

/**
 * Created by Andy on 8/18/2017. currently has basic functionality to add a class with grade points
 * and letter grade, as well as return this information, does not override to string or anything
 * cool like that. Alternate grade functionality not yet implemented 
 */

public class AddNewClass {
    //constants and class level varaibles
    private static final String NO_NAME = "No name provided";
    private static final Logger logger = Logger.getLogger("AddNewClass log");
    //instance variable
    private String className;
    private int creditHours;
    private double numericGrade;
    private double alternateNumeric;
    private String alternateGrade;
    private String grade;
    private GPACalculation gradePoints = new GPACalculation();

    /**
     * constructor to include class name
     * @param className name of class taken
     * @param creditHours credit hours to include with class
     * @param grade grade earned in class, must be A B C D, all others treated as E
     */
    public AddNewClass(String className, int creditHours, String grade) {
        logger.info("Start ClassName included constructor");
        this.className = className;
        this.creditHours = creditHours;
        if (grade.equalsIgnoreCase("a") || grade.equalsIgnoreCase("a-") ||grade.equalsIgnoreCase("b+") ||
                grade.equalsIgnoreCase("b") ||grade.equalsIgnoreCase("b-") || grade.equalsIgnoreCase("c+") ||
                grade.equalsIgnoreCase("c") || grade.equalsIgnoreCase("c-") || grade.equalsIgnoreCase("d+") ||
                grade.equalsIgnoreCase("d") || grade.equalsIgnoreCase("d-")) {
            this.grade = grade;
        } else {
            this.grade = "E";
        }
        this.numericGrade = calculateNumericGrade(this.grade);
        buildGradePoints();
        logger.info("End ClassName included constructor");
    }

    /**
     * no class name given constructor
     * @param creditHours
     * @param grade
     */
    public AddNewClass(int creditHours, String grade) {
        logger.info("start ClassName not included constructor");
        this.className = NO_NAME;
        this.creditHours = creditHours;
        if (grade.equalsIgnoreCase("a") || grade.equalsIgnoreCase("a-") ||grade.equalsIgnoreCase("b+") ||
                grade.equalsIgnoreCase("b") ||grade.equalsIgnoreCase("b-") || grade.equalsIgnoreCase("c+") ||
                grade.equalsIgnoreCase("c") || grade.equalsIgnoreCase("c-") || grade.equalsIgnoreCase("d+") ||
                grade.equalsIgnoreCase("d") || grade.equalsIgnoreCase("d-")) {
            this.grade = grade.toUpperCase();
        } else {
            this.grade = "E";
        }
        this.numericGrade = calculateNumericGrade(this.grade);
        buildGradePoints();
        logger.info("End ClassName not included constructor");
    }


    //private utility methods
    private double calculateNumericGrade(String letterGrade){
        logger.info("Start calculate numeric grade");
        //chained else if to use ignores case
        if (letterGrade.equalsIgnoreCase("a")) {
            return Constants.A;
        }else if (letterGrade.equalsIgnoreCase("a-")) {
            return Constants.A_MINUS;
        }else if (letterGrade.equalsIgnoreCase("b+")) {
            return Constants.B_PLUS;
        }else if (letterGrade.equalsIgnoreCase("b")) {
            return Constants.B;
        }else if (letterGrade.equalsIgnoreCase("b-")) {
            return Constants.B_MINUS;
        }else if (letterGrade.equalsIgnoreCase("c+")) {
            return Constants.C_PLUS;
        }else if (letterGrade.equalsIgnoreCase("c")) {
            return Constants.C;
        }else if (letterGrade.equalsIgnoreCase("c-")) {
            return Constants.C_MINUS;
        }else if (letterGrade.equalsIgnoreCase("d+")) {
            return Constants.D_PLUS;
        }else if (letterGrade.equalsIgnoreCase("d")) {
            return Constants.D;
        }else {
            return 0;
        }


    }

    /**
     * updates gradePoints object to reflect current class gpa
     */
    private void buildGradePoints() {
        logger.info("Start buildGradePoints");
        gradePoints.setHours(this.creditHours);
        gradePoints.setGradePoints(numericGrade);
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
        //comment
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
    public void setNumericGrade(double grade){
        //comment
        this.numericGrade = grade;
    }
    public double getNumericGrade() {
        //comment
        return this.numericGrade;
    }
    public double getClassGPA(){
        //comment
        //only including logging here because i expect to divide by 0 at some point
        logger.info("start getClassGpa");
        return this.gradePoints.calculateGPA();
    }

}
