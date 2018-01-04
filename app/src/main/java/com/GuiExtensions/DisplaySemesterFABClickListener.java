package com.GuiExtensions;


import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.gui.AddClassToSemester;
import com.gui.DisplayIndividualSemester;

import java.util.logging.Logger;


/**
 * Created by Andy on 12/28/2017.
 */

public class DisplaySemesterFABClickListener extends DisplayIndividualSemester implements View.OnClickListener {
    private String semName;
    private Logger logger;
    private DisplayIndividualSemester parent;
    /*
     * constructor
     */
    public DisplaySemesterFABClickListener(DisplayIndividualSemester parent, String semName){
        logger = Logger.getLogger("fabListener");
        logger.info("fabClickListener constructor start");
        this.parent = parent;
        this.semName = semName;

    }
    @Override
    public void onClick(View view){
        logger.info("Start fab on click");
        Intent i = new Intent(parent.getApplicationContext(), AddClassToSemester.class);
        //TODO: semName really should be some kind of constant
        i.putExtra("semName", semName);
        startActivity(i);
    }
}
