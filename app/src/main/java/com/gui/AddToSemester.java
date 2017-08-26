package com.gui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backend_code.AddNewClass;
import com.constants.Constants;
import com.example.android.gpatrack.R;

import java.util.logging.Logger;

/**
 * screen toinput class name credit hours and grade data
 */
public class AddToSemester extends AppCompatActivity {





    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */


    // UI references.
    private EditText mClassName;
    private EditText mCreditHours;
    private EditText letterGrade;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger logger = Constants.LOGGER;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_semester);
        mClassName = (EditText) findViewById(R.id.class_name);
        mCreditHours = (EditText) findViewById(R.id.credit_hours);
        letterGrade = (EditText) findViewById(R.id.grade);

        Button addClass = (Button) findViewById(R.id.add_class_button);
        addClass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                addToClass();
            }
        });

        mLoginFormView = findViewById(R.id.add_class_form);

    }


    private void addToClass() {
        String className = "temp";
        String creditHours = "1";
        String grade = "E";
        if (mClassName != null) {
             className = mClassName.getText().toString();
        }
        if (mCreditHours != null) {
             creditHours = mCreditHours.getText().toString();
        }
        if(letterGrade != null) {
            grade = letterGrade.getText().toString();

        }
        int numericHours = Integer.parseInt(creditHours);

        AddNewClass course = new AddNewClass(className, numericHours, grade);
        makeToast("Course Successfully Added");



    }
    private void makeToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }







}

