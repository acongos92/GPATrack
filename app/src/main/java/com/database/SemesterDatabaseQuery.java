package com.database;


import com.backend_code.DatabaseDTO;
import com.database.SemesterDatabase.ClassEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.backend_code.DatabaseDTO;

import java.util.ArrayList;


/**
 * Created by selle on 8/26/2017.
 */

public class SemesterDatabaseQuery {
    SQLiteDatabase base;
    SemesterDatabaseHelper dBHelper;
    public SemesterDatabaseQuery(Context context , Boolean write) {
        dBHelper = new SemesterDatabaseHelper(context);
        if (write) {
            base = dBHelper.getWritableDatabase();
        }else{
            base = dBHelper.getReadableDatabase();
        }
    }


    /**
     * adds a particular data transfer object to the database
     * @param data data transfer object containing data to be removed from the database
     */
    public void addToDatabase (DatabaseDTO data){
        ContentValues cv = new ContentValues();

        cv.put(SemesterDatabase.ClassEntry.CLASS_NAME, data.getClassName());
        cv.put(ClassEntry.COLUMN_GRADE, data.getGrade());
        cv.put(ClassEntry.COLUMN_SEMESTER,data.getSemester());
        base.insert(ClassEntry.TABLE_NAME, null, cv);
    }

    /**
     * removes a particular item from the database, currently uses only class name to determine
     * which column is going to be deleted but should be able to add or remove based on any
     * item in the data DTO
     * @param data data transfer object containing data to be removed from the database
     */
    public void removeFromDatabase (DatabaseDTO data) {
        base.delete(ClassEntry.TABLE_NAME, ClassEntry.CLASS_NAME, new String[] {"=", data.getClassName(), null});
    }

    //private helpers
    private Cursor wholeDB() {

        return null;
    }

    private DatabaseDTO buildDTO() {
        return new DatabaseDTO("wut" ,"wut", 10);
    }

    /**
     * Returns an array of strings that contain all the current semesters
     */
    public String[] queryAllSemester(){
        Cursor cursor = base.query(ClassEntry.TABLE_NAME,null,null,null,null,null,null,ClassEntry.CLASS_NAME);
        return base.query(ClassEntry.TABLE_NAME,null,null,null,null,null,null,ClassEntry.CLASS_NAME).getColumnNames();
    }


}




