package com.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SemesterDatabaseHelper extends SQLiteOpenHleper {

    private static final String DATABASE_NAME = "sem.db";
    private static final int DATABASE_VERSION = 1;

    public SemesterDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

    }











}


