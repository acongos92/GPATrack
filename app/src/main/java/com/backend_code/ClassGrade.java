package com.backend_code;


import android.database.Cursor;

import java.util.logging.Logger;

public class ClassGrade {

    private static final Logger logger = Logger.getLogger("ClassGrade Logger");
    ClassGrade(Cursor cursor){
        logger.info(cursor.toString());
    }



}
