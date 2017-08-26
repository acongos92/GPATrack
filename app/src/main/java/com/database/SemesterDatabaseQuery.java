package com.database;


import com.backend_code.DatabaseDTO;
import com.database.SemesterDatabase.ClassEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.backend_code.DatabaseDTO;


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


    //does not require writable database

    /**
     *
     * @return cursor containing just the semesters listed in a database
     */
    public void addToDatabase (DatabaseDTO data){
        ContentValues cv = new ContentValues();

        cv.put(SemesterDatabase.ClassEntry.CLASS_NAME, data.getClassName());
        cv.put(ClassEntry.COLUMN_GRADE, data.getGrade());
        cv.put(ClassEntry.COLUMN_SEMESTER,data.getSemester());
        base.insert(ClassEntry.TABLE_NAME, null, cv);
    }

    public void removeFromDatabase (DatabaseDTO data) {

    }

    //private helpers
    private Cursor wholeDB() {

        return null;
    }

    private DatabaseDTO buildDTO() {
        return new DatabaseDTO("wut" ,"wut", 10);
    }



}




