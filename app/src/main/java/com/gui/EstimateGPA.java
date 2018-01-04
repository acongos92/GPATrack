package com.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.backend_code.GPACalculation;
import com.database.SemesterDatabaseQuery;
import com.example.android.gpatrack.R;

import java.util.Iterator;
import java.util.Map;

public class EstimateGPA extends AppCompatActivity {

    private TextView mCumulativeGPA;
    private SemesterDatabaseQuery SDQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa_estimate_screen);

    }


    //TODO: add best semester, worst semester, all A range classes, all B range classes all c range etc
    @Override
    public void onDestroy(){
        if(SDQ != null){
            SDQ.closeConnection();
        }
        super.onDestroy();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        startActivity(getIntent());
    }




}
