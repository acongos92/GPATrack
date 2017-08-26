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
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + ClassEntry.TABLE_NAME + " (" +
                ClassEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ClassEntry.CLASS_NAME + " TEXT NOT NULL, " +
                ClassEntry.COLUMN_GRADE + " INTEGER NOT NULL, " +
                ClassEntry.COLUMN_SEMESTER + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        // COMPLETED (9) Inside, execute a drop table query, and then call onCreate to re-create it
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WaitlistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }











}


