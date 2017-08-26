package com.example.android.gpatrack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.android.gpatrack.AddNewClass;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static android.Manifest.permission.READ_CONTACTS;

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
    private View mProgressView;
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


    }







}

