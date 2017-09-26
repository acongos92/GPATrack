package com.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.gpatrack.R;

import java.util.logging.Logger;

public class ConfirmDeletePopup extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger("AddNewSemester log");

    private EditText semesterCreationEditText;

    private RecyclerView semesterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        LOGGER.info("Start AddNewSemesterPopup view");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirm_delete_class);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.3));






        Button yes = (Button) findViewById(R.id.yes_button);
        Button no = (Button) findViewById(R.id.no_button);


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
    private void intentWithResponse(boolean confirm){







}
