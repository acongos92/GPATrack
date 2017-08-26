package com.database;

<<<<<<< HEAD
public class SemesterDatabaseQuery {


















=======
import com.database.SemesterDatabase.ClassEntry;
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
        
    }

    public void removeFromDatabase (DatabaseDTO data) {

    }

    //private helpers
    private Cursor wholeDB() {
        Cursor cursor = base.query(ClassEntry.TABLE_NAME,

    }

    private DatabaseDTO buildDTO() {

    }
>>>>>>> 55eadbbb73201464f1a895b6fda0ed7bc05571ec


}




