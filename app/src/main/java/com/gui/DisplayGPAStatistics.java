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

public class DisplayGPAStatistics extends AppCompatActivity {

    private TextView mCumulativeGPA;
    private SemesterDatabaseQuery SDQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SDQ = new SemesterDatabaseQuery(this, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa_statisctics_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mCumulativeGPA = (TextView) findViewById(R.id.cumulative_gpa_display);

        setCumulativeGpa(mCumulativeGPA, SDQ);


    }

    //set the content of cumulative gpa
    private void setCumulativeGpa(TextView view, SemesterDatabaseQuery query){

        double gpa = query.getCumulativeGpa();

        String newText = (" " + Double.toString(gpa));
        if(newText.length() > 5){
            view.append(newText.substring(0,5));
        }else {
            view.append(newText);
        }


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
