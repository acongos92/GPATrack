package com.database;


import com.backend_code.DatabaseDTO;
import com.database.SemesterDatabase.ClassEntry;

import com.constants.Constants;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.backend_code.DatabaseDTO;

import java.util.ArrayList;
import java.util.logging.Logger;



public class SemesterDatabaseQuery {
    private static final Logger logger = Logger.getLogger("Database_Logger");
    SQLiteDatabase base;
    SemesterDatabaseHelper dBHelper;
    public SemesterDatabaseQuery(Context context , Boolean write) {
        logger.info("start semesterDatabaseQuery constructor");
        dBHelper = new SemesterDatabaseHelper(context);

        if (write) {
            logger.info("Start writable database constructor");
            base = dBHelper.getWritableDatabase();
        }else{
            logger.info("Start readable database constructor");
            base = dBHelper.getReadableDatabase();
        }
    }


    /**
     * adds a particular data transfer object to the database
     * @param data data transfer object containing data to be removed from the database
     */
    public void addToDatabase (DatabaseDTO data){
        ContentValues cv = new ContentValues();

        cv.put(SemesterDatabase.ClassEntry.COLUMN_CLASS_NAME, data.getClassName());
        cv.put(ClassEntry.COLUMN_GRADE, data.getGrade());
        cv.put(ClassEntry.COLUMN_SEMESTER,data.getSemester());
        cv.put(ClassEntry.COLUMN_CREDIT_HOURS, data.getSemester());
        base.insert(ClassEntry.TABLE_NAME, null, cv);
    }

    /**
     * removes a particular item from the database, currently uses only class name to determine
     * which column is going to be deleted but should be able to add or remove based on any
     * item in the data DTO
     * @param data data transfer object containing data to be removed from the database
     */
    public void removeFromDatabase (DatabaseDTO data) {
        base.delete(ClassEntry.TABLE_NAME, ClassEntry.COLUMN_CLASS_NAME, new String[] {"=", data.getClassName(), null});
    }

    //private helpers
    private Cursor wholeDB() {

        return null;
    }

    private DatabaseDTO buildDTO() {
        return new DatabaseDTO("wut" ,"wut", 10, 10);
    }

    /**
     * Returns an array of strings that contain all the current semesters
     */
    public String[] queryAllSemester(){

        String selectQuery = "SELECT * FROM " + SemesterDatabase.DATABASE_NAME;
        Cursor cursor = base.rawQuery(selectQuery, null);

        if (cursor != null){
            cursor.moveToFirst();
        }
        if (cursor.getCount() > 0){
            do {
                String butt = cursor.getString(0);

            } while (cursor.moveToNext());
        }
        return null;

    }

    /**
     *
     * @return an integer stack containing all numeric grades
     */
    public float[] getAllGrades(){
        Cursor cursor = wholeDB();
        int numRows = cursor.getCount();

        float[] grades = new float[numRows];
        int index = cursor.getColumnIndex(SemesterDatabase.ClassEntry.COLUMN_GRADE);
        cursor.moveToFirst();

        for(int i = 0; i < numRows; i++) {
            grades[i] = cursor.getFloat(index);
            cursor.moveToNext();
        }

        cursor.close();
        return grades;
    }

}




