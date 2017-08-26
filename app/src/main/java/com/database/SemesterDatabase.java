package com.database;

import android.provider.BaseColumns;

public class SemesterDatabase {

    public static final class ClassEntry implements BaseColumns{
        public static final String CLASS_NAME = "class";
        public static final String COLUMN_GRADE = "grade";
        public static final String COLUMN_SEMESTER = "current semester";
    }

}