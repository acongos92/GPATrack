package com.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adapters.PopupSemesterAdapter;
import com.backend_code.DatabaseDTO;
import com.database.SemesterDatabase;
import com.database.SemesterDatabaseHelper;
import com.database.SemesterDatabaseQuery;
import com.example.android.gpatrack.R;
import com.database.SemesterDatabaseQuery;

import java.util.logging.Logger;

public class AddNewSemesterPopup extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger("AddNewSemester log");

    private EditText semesterCreationEditText;

    private RecyclerView semesterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        LOGGER.info("Start AddNewSemesterPopup view");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_new_semester);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.6));

        semesterCreationEditText = (EditText) findViewById(R.id.new_semester_name_et);





        Button addClass = (Button) findViewById(R.id.create_new_semester_button);
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                LOGGER.info("AddNewSemesterPopup create button clicked");
                if(isValidSemesterName (semesterCreationEditText)) {
                    Intent i = new Intent(AddNewSemesterPopup.this, AddToSemester.class);
                    i.putExtra("semName", String.valueOf(semesterCreationEditText.getText()));
                    startActivity(i);
                }else {
                    makeToast("Semester name was invalid");
                    semesterCreationEditText.setText("");
                }
            }
        });

    }



    private void makeToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private boolean isValidSemesterName(EditText et) {
        return (et.getText().length() > 0) && (et.getText().length() < 20);
    }





}
