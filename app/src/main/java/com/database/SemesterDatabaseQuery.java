package com.database;


import com.backend_code.DatabaseDTO;
import com.database.SemesterDatabase.ClassEntry;

import com.constants.Constants;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.backend_code.DatabaseDTO;

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
        base.delete(ClassEntry.TABLE_NAME, ClassEntry.COLUMN_CLASS_NAME, new String[]{"=", data.getClassName(), null});
    }

    /**
     * Returns an array of strings that contain all the current semesters
     */
    public String[] queryAllSemester() {
        logger.info("start getAllClassNames");
        Cursor cursor = wholeDB();
        int numRows = cursor.getCount();

        String[] names = new String[numRows];
        int index = cursor.getColumnIndex(ClassEntry.COLUMN_SEMESTER);
        cursor.moveToFirst();

        for (int i = 0; i < numRows; i++) {
            names[i] = cursor.getString(index);
            cursor.moveToNext();
        }

        cursor.close();
        return names;

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
     *
     * @return map containing all credit hours earned at a particular grade point
     */
    public Map<Float, Integer> getCreditHoursAndGrades(){
        logger.info("start getCreditHoursAndGrades");
        Cursor cursor = wholeDB();
        int numRows = cursor.getCount();

        Map<Float, Integer> gradesAndPoints = new HashMap<Float, Integer>();
        int gradeIndex = cursor.getColumnIndex(ClassEntry.COLUMN_GRADE);
        int creditIndex = cursor.getColumnIndex(ClassEntry.COLUMN_CREDIT_HOURS);

        cursor.moveToFirst();
        for(int i = 0; i < numRows; i++) {
            if(gradesAndPoints.containsKey(cursor.getFloat(gradeIndex))){
                int current = gradesAndPoints.get(cursor.getFloat(gradeIndex));
                gradesAndPoints.put(cursor.getFloat(gradeIndex),(current + cursor.getInt(creditIndex)));
            }else {
                gradesAndPoints.put(cursor.getFloat(gradeIndex), cursor.getInt(creditIndex));
            }
            cursor.moveToNext();
        }

        return gradesAndPoints;
    }


    //private helpers
    private Cursor wholeDB() {
        logger.info("start wholeDB");

        return base.query(ClassEntry.TABLE_NAME, null, null, null, null, null, null, null);
    }

    private DatabaseDTO buildReturnDTO() {
        return new DatabaseDTO("wut", "wut", 10, 10);
    }

    public void closeConnection(){
        this.dBHelper.close();
        this.base.close();
    }


}




