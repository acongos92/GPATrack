package com.database;

import android.provider.BaseColumns;

public class SemesterDatabase {

    public static final class ClassEntry implements BaseColumns{
        public static final String TABLE_NAME = "semester_database";
        public static final String CLASS_NAME = "course";
        public static final String COLUMN_GRADE = "grade";
        public static final String COLUMN_SEMESTER = "current semester";
    }

}
