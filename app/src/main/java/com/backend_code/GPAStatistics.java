package com.backend_code;

import android.content.Context;

import com.database.SemesterDatabaseQuery;
import com.gui.DisplayGPAStatistics;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Andy on 12/27/2017. enhanced functionality for display of gpa stats
 */

public class GPAStatistics {
    private SemesterDatabaseQuery sdq;

    /*
     * Constructor
     */
    public GPAStatistics (Context context){
        sdq = new SemesterDatabaseQuery(context, false);

    }
    /*
     * public methods
     */

    /**
     *
     * @return a single best semester, or a list of several
     */
    public ArrayList<GPACalculation> getBestSemester(){
       ArrayList<GPACalculation> findBest = sdq.getAllSemesterNamesAndGPA();
       ArrayList<GPACalculation> topGrades = new ArrayList<GPACalculation>();
       String bestName = findBest.get(0).getSemesterOrClassName();
       Double bestGPA = findBest.get(0).calculateGPA();
       int index = 0;
       //TODO: actually make the list, not just a single class
       for(int i = 1; i < findBest.size(); i++) {
            String nextName = findBest.get(i).getSemesterOrClassName();
            Double nextGPA = findBest.get(i).calculateGPA();
            if(nextGPA > bestGPA) {
                bestGPA = nextGPA;
                bestName = nextName;
                index = i;
            }
       }
       topGrades.add(new GPACalculation(findBest.get(index).getHours(), findBest.get(index).getGradePoints()));
       topGrades.get(0).setSemesterOrClassName(bestName);
       return topGrades;
    }

    public String getWorstSemester(){
        return null;
    }
    public List getA(){
        return null;
    }

    /**
     * closes open database connection associated with the stat calculation, needs to be called
     * to avoid leak when the calling class is done using this class
     */
    public void close(){
        sdq.closeConnection();
    }
    /*
     * private helpers
     */
}
