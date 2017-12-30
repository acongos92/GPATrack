package com.GuiExtensions;


import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.gui.AddClassToSemester;
import com.gui.DisplayIndivudalSemester;

import java.util.logging.Logger;


/**
 * Created by Andy on 12/28/2017.
 */

public class DisplaySemesterFABClickListener extends DisplayIndivudalSemester implements View.OnClickListener {
    private Context context;
    private String semName;
    private Logger logger;
    /*
     * constructor
     */
    public DisplaySemesterFABClickListener(Context context, String semName){
        logger = Logger.getLogger("fabListener");
        logger.info("fabClickListener constructor start");
        this.context = context;
        this.semName = semName;

    }
    @Override
    public void onClick(View view){
        logger.info("Start fab on click");
        Intent i = new Intent(context, AddClassToSemester.class);
        //TODO: semName really should be some kind of constant
        i.putExtra("semName", semName);
        startActivity(i);
    }
}
