package com.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.backend_code.GPACalculation;
import com.backend_code.GPAStatistics;
import com.database.SemesterDatabaseQuery;
import com.example.android.gpatrack.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

public class DisplayGPAStatistics extends AppCompatActivity {

    private TextView mCumulativeGPA;
    private TextView mBestSemester;
    private TextView mTotalHours;
    private SemesterDatabaseQuery SDQ;
    private GPACalculation totalHoursAndGpa;
    private Logger logger = Logger.getLogger("DisplayGpaStatistics logger");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logger.info("Start DisplayGpaStatistics onCreate");
        SDQ = new SemesterDatabaseQuery(this, false);
        buildHoursAndGpa(SDQ);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa_statisctics_screen);
        mCumulativeGPA = (TextView) findViewById(R.id.cumulative_gpa_display);
        mBestSemester = (TextView) findViewById(R.id.tv_best_semester);
        mTotalHours = (TextView) findViewById(R.id.tv_total_credit_hours);

        GPAStatistics stats = new GPAStatistics(this);

        String bestSemester = getBestSemesterName(stats);
        mBestSemester.append(bestSemester);

        String totalHours = Integer.toString(getTotalCreditHours(stats));
        mTotalHours.append(totalHours);

        setCumulativeGpa(mCumulativeGPA);

        stats.close();
        SDQ.closeConnection();

        logger.info("End DisplayGpaStatistics onCreate");
    }
    /*
     * private helpers
     */
    private void buildHoursAndGpa(SemesterDatabaseQuery query){
        totalHoursAndGpa = query.getHoursAndGrades();
    }
    //set the content of cumulative gpa
    private void setCumulativeGpa(TextView view){
        double gpa = totalHoursAndGpa.calculateGPA();

        String newText = (" " + Double.toString(gpa));
        if(newText.length() > 5){
            view.append(newText.substring(0,5));
        }else {
            view.append(newText);
        }


    }
    //TODO: add best semester, worst semester, all A range classes, all B range classes all c range etc
    private String getBestSemesterName(GPAStatistics stats){
        ArrayList<GPACalculation> calcs = stats.getBestSemester();
        return calcs.get(0).getSemesterOrClassName();
    }
    private int getTotalCreditHours(GPAStatistics stats){
        return totalHoursAndGpa.getHours();
    }
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
