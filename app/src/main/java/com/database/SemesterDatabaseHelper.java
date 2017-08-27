package com.database;

import com.database.SemesterDatabase.ClassEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SemesterDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sem.db";
    private static final int DATABASE_VERSION = 1;

    public SemesterDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        final String SQL_CREATE_SEMESTER_TABLE = "CREATE TABLE " + ClassEntry.TABLE_NAME + " (" +
                ClassEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                ClassEntry.CLASS_NAME + " TEXT NOT NULL , " +
                ClassEntry.COLUMN_GRADE + " REAL NOT NULL , " +
                ClassEntry.COLUMN_SEMESTER + " TEXT NOT NULL" +
                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_SEMESTER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ClassEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }











}


