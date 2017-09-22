package com.gui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backend_code.AddNewClass;
import com.backend_code.DatabaseDTO;
import com.backend_code.GPACalculation;
import com.constants.Constants;
import com.database.SemesterDatabaseQuery;
import com.example.android.gpatrack.R;

import java.util.List;
import java.util.logging.Logger;

/**
 * screen to input class name credit hours and grade data
 */
public class AddClassToSemester extends AppCompatActivity {
    private static final Logger logger = Logger.getLogger("AddNewClass log");


    // UI references.
    private EditText mClassName;
    private EditText mCreditHours;
    private EditText letterGrade;
    private View mAddClassView;
    SemesterDatabaseQuery SDQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //instantiate a database connection with this as context and true meaning writeable
        SDQ = new SemesterDatabaseQuery(this, true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_semester);
        mClassName = (EditText) findViewById(R.id.class_name);
        mCreditHours = (EditText) findViewById(R.id.credit_hours);
        letterGrade = (EditText) findViewById(R.id.grade);

        Button addClass = (Button) findViewById(R.id.add_class_button);
        addClass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                if (isValidClass()) {
                    addToClass();
                    mClassName.setText("");
                    mCreditHours.setText("");
                    letterGrade.setText("");
                    makeToast("Class Successfully added");
                } else {
                    makeToast("All Input fields are required");
                }
            }
        });
        mAddClassView = findViewById(R.id.add_class_form);

    }


    private void addToClass() {
        String className;
        String creditHours;
        String grade;

        Intent intent = getIntent();
        String sem = intent.getExtras().getString("semName");
        logger.info("NOTICE ME IN LOGCAT!!!!!!" + sem);
        className = mClassName.getText().toString();
        creditHours = mCreditHours.getText().toString();
        grade = letterGrade.getText().toString();
        int numericHours = Integer.parseInt(creditHours);
        AddNewClass course = new AddNewClass(className, numericHours,grade);
        SDQ.addToDatabase(buildDTO(course, sem));

    }
    private DatabaseDTO buildDTO(AddNewClass newClass, String sem){
       return  new DatabaseDTO(sem, newClass.getClassName(),(float) newClass.getNumericGrade(), newClass.getCreditHours());
    }

    private void makeToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    protected void onDestroy(){
        SDQ.closeConnection();
        super.onDestroy();

    }

    private boolean isValidClass() {
        boolean isGood = true;
        if (mClassName.getText().length() < 1) {
            isGood = false;
        }
        if (mCreditHours.getText().length() < 1) {
            isGood = false;
        }
        if (letterGrade.getText().length() < 1) {
            isGood = false;
        }

        return isGood;
    }


}

