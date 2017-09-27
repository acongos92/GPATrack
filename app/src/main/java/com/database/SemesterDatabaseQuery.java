package com.database;


import com.backend_code.AddNewClass;
import com.backend_code.ClassGrade;
import com.backend_code.DatabaseDTO;
import com.database.SemesterDatabase.ClassEntry;

import com.constants.Constants;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.backend_code.DatabaseDTO;
import com.backend_code.GPACalculation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;



public class SemesterDatabaseQuery {
    private static final Logger logger = Logger.getLogger("Database_Logger");
    SQLiteDatabase base;
    SemesterDatabaseHelper dBHelper;

    public SemesterDatabaseQuery(Context context, Boolean write) {
        logger.info("start semesterDatabaseQuery constructor");
        dBHelper = new SemesterDatabaseHelper(context);

        if (write) {
            logger.info("Start writable database constructor");
            base = dBHelper.getWritableDatabase();
        } else {
            logger.info("Start readable database constructor");
            base = dBHelper.getReadableDatabase();
        }
    }


    /**
     * adds a particular data transfer object to the database
     *
     * @param data data transfer object containing data to be removed from the database
     */
    public void addToDatabase(DatabaseDTO data) {
        logger.info("start addToDatabase");
        ContentValues cv = new ContentValues();

        cv.put(SemesterDatabase.ClassEntry.COLUMN_CLASS_NAME, data.getClassName());
        cv.put(ClassEntry.COLUMN_GRADE, data.getGrade());
        cv.put(ClassEntry.COLUMN_SEMESTER, data.getSemester());
        cv.put(ClassEntry.COLUMN_CREDIT_HOURS, data.getCreditHours());
        base.insert(ClassEntry.TABLE_NAME, null, cv);
    }

    /**
     * removes a particular item from the database, currently uses only class name to determine
     * which column is going to be deleted but should be able to add or remove based on any
     * item in the data DTO
     *
     * @param data data transfer object containing data to be removed from the database
     */
    public void removeFromDatabase(DatabaseDTO data) {
        base.delete(ClassEntry.TABLE_NAME, ClassEntry.COLUMN_CLASS_NAME + "=? and " + ClassEntry.COLUMN_SEMESTER + "=?",new String[] {data.getClassName(), data.getSemester()});
    }


    /**
     * @return an integer array containing all numeric grades
     */
    public float[] getAllGrades() {
        logger.info("start getAllGrades");
        Cursor cursor = wholeDB();
        int numRows = cursor.getCount();

        float[] grades = new float[numRows];
        int index = cursor.getColumnIndex(SemesterDatabase.ClassEntry.COLUMN_GRADE);
        cursor.moveToFirst();

        for (int i = 0; i < numRows; i++) {
            grades[i] = cursor.getFloat(index);
            cursor.moveToNext();
        }

        cursor.close();
        return grades;
    }
    /**
     * @return a String array containing all class names stored in the database
     */
    public String[] getAllClassNames() {
        logger.info("start getAllClassNames");
        Cursor cursor = wholeDB();
        int numRows = cursor.getCount();

        String[] names = new String[numRows];
        int index = cursor.getColumnIndex(ClassEntry.COLUMN_CLASS_NAME);
        cursor.moveToFirst();

        for (int i = 0; i < numRows; i++) {
            names[i] = cursor.getString(index);
            cursor.moveToNext();
        }

        cursor.close();
        return names;
    }

    /**
     * think this is dead code?
     * @return map containing all credit hours earned at a particular grade point
     */
    public double getCumulativeGpa(){
        logger.info("start getCreditHoursAndGrades");
        Cursor cursor = wholeDB();
        int numRows = cursor.getCount();

        int gradeIndex = cursor.getColumnIndex(ClassEntry.COLUMN_GRADE);
        int creditIndex = cursor.getColumnIndex(ClassEntry.COLUMN_CREDIT_HOURS);
        GPACalculation calc = new GPACalculation();
        cursor.moveToFirst();
        for(int i = 0; i < numRows; i++){
            double grade = cursor.getFloat(gradeIndex);
            int hours = cursor.getInt(creditIndex);
            calc.addPointsAndHours(hours,grade);
            cursor.moveToNext();

        }


        return calc.calculateGPA();
    }

    /**
     *
     * @return a cursor containing items only with unique semester names
     */
    public Cursor getUniqueSemesters(){
        logger.info("Start getUniqueSemesters");
        String[] colNames = new String[1];
        colNames[0] = ClassEntry.COLUMN_SEMESTER;

        return base.query(true, ClassEntry.TABLE_NAME, colNames, null, null, null, null, null, null);
    }

    public ClassGrade getAllClassesInASemesters(String semesterName){
        logger.info("Start GetAllClassesInASemester");
        String[] colNames = new String[2];
        colNames[1] = ClassEntry.COLUMN_CLASS_NAME;
        colNames[0] = ClassEntry.COLUMN_GRADE;

        //think this should just return all the matching strings
        Cursor cursor =  base.query(false, ClassEntry.TABLE_NAME,colNames, ClassEntry.COLUMN_SEMESTER + " = '" + semesterName + "'" , null, null, null, null, null);
        ClassGrade classGrade = new ClassGrade();
        int numRows = cursor.getCount();
        cursor.moveToFirst();
        for(int i = 0; i < numRows; i++){
            classGrade.add(cursor.getString(cursor.getColumnIndex(ClassEntry.COLUMN_CLASS_NAME)), cursor.getDouble(cursor.getColumnIndex(ClassEntry.COLUMN_GRADE)));
            cursor.moveToNext();

        }



        return classGrade;
    }

    public ArrayList<GPACalculation> getAllSemesterNamesAndGPA(){
        logger.info("Start getAllSemesterNamesAndGpa");
        ArrayDeque<GPACalculation> gpas = new ArrayDeque<GPACalculation>();
        Cursor cursor = alphaSortedSemesters();
        String second = "";
        int semesterNameColumn  = cursor.getColumnIndex(ClassEntry.COLUMN_SEMESTER);
        int gradeColumn = cursor.getColumnIndex(ClassEntry.COLUMN_GRADE);
        int creditHoursColumn = cursor.getColumnIndex(ClassEntry.COLUMN_CREDIT_HOURS);

        cursor.moveToFirst();
        int numRows = cursor.getCount();
        for (int i = 0; i < numRows; i++) {
            //garunteed an order in this cursor object so use the string comparisson to detect change
            String first = cursor.getString(semesterNameColumn);
            if (first.equals(second)){
                gpas.peekLast().addPointsAndHours(cursor.getInt(creditHoursColumn), cursor.getDouble(gradeColumn));
            }else {
                gpas.addLast(new GPACalculation(cursor.getInt(creditHoursColumn), cursor.getDouble(gradeColumn)));
                gpas.peekLast().setSemesterOrClassName(cursor.getString(semesterNameColumn));
            }
            second = first;
            cursor.moveToNext();
        }
        return new ArrayList<GPACalculation>(gpas);
    }

    /**
     * close database connection
     */
    public void closeConnection(){
        this.dBHelper.close();
        this.base.close();
    }


    //private helpers
    private Cursor wholeDB() {
        logger.info("start wholeDB");

        return base.query(ClassEntry.TABLE_NAME, null, null, null, null, null, null, null);
    }

    private Cursor alphaSortedSemesters(){
        String[] colNames = new String[3];
        colNames[0] = ClassEntry.COLUMN_SEMESTER;
        colNames[1] = ClassEntry.COLUMN_GRADE;
        colNames[2] = ClassEntry.COLUMN_CREDIT_HOURS;
        //gets a cursor containing all semester names and grades sorted in ascending order by semester name
        return base.query(false, ClassEntry.TABLE_NAME, colNames, null, null, null, null, ClassEntry.COLUMN_SEMESTER, null);
    }

    private DatabaseDTO buildReturnDTO() {
        return new DatabaseDTO("wut", "wut", 10, 10);
    }




}




