package com.gui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.backend_code.AddNewClass;
import com.backend_code.DatabaseDTO;
import com.backend_code.GPACalculation;
import com.constants.Constants;
import com.database.SemesterDatabaseQuery;
import com.example.android.gpatrack.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * screen to input class name credit hours and grade data
 */
public class AddClassToSemester extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final Logger logger = Logger.getLogger("AddNewClass log");


    // UI references.
    private EditText mClassName;
    private EditText mCreditHours;
    private Spinner letterGrade;
    private SemesterDatabaseQuery SDQ;
    private String GRADES = "Grade";
    /*
     * Click Listener implementations
     */
    private View.OnClickListener addClassListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            if (isValidClass()) {
                addToClass();
                mClassName.setText("");
                mCreditHours.setText("");
                makeToast("Class Successfully added");
            } else {
                return;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_semester);
        mClassName = (EditText) findViewById(R.id.class_name);
        mCreditHours = (EditText) findViewById(R.id.credit_hours);
        letterGrade = (Spinner) findViewById(R.id.spinner_select_grade);
        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item , buildGradesList());
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Button addClass = (Button) findViewById(R.id.add_class_button);
        addClass.setOnClickListener(addClassListener);

        letterGrade.setAdapter(gradeAdapter);
        letterGrade.setPrompt("Grade");

    }


    private void addToClass() {
        String className;
        String creditHours;
        String grade;

        //instantiate a database connection with this as context and true meaning writeable
        SDQ = new SemesterDatabaseQuery(this, true);

        Intent intent = getIntent();
        String sem = intent.getExtras().getString("semName");

        grade = letterGrade.getSelectedItem().toString();
        className = mClassName.getText().toString();
        creditHours = mCreditHours.getText().toString();

        int numericHours = Integer.parseInt(creditHours);
        AddNewClass course = new AddNewClass(className, numericHours, grade);

        SDQ.addToDatabase(buildDTO(course, sem));
        SDQ.closeConnection();

    }
    private DatabaseDTO buildDTO(AddNewClass newClass, String sem){
       return  new DatabaseDTO(sem, newClass.getClassName(),(float) newClass.getNumericGrade(), newClass.getCreditHours());
    }


    @Override
    protected void onDestroy(){
        if(SDQ != null) {
            SDQ.closeConnection();
        }
        super.onDestroy();

    }

    private boolean isValidClass() {
        boolean isGood = true;

        if (mClassName.getText().length() < 1) {
            isGood = false;
            makeToast("Please Input Class Name");
        }
        else if (mCreditHours.getText().length() < 1) {
            isGood = false;
            makeToast("Please Input Credit Hours");
        }
        else if (letterGrade.getSelectedItem().toString().equals(GRADES)){
            isGood = false;
            makeToast("Please Select Grade");
        }


        return isGood;
    }

    private List<String> buildGradesList() {
        List<String> grades = new ArrayList<String>();
        grades.add(GRADES);
        grades.add("A");
        grades.add("A-");
        grades.add("B+");
        grades.add("B");
        grades.add("B-");
        grades.add("C+");
        grades.add("C");
        grades.add("C-");
        grades.add("D+");
        grades.add("D");
        grades.add("E");


        return grades;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    private void makeToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

}

