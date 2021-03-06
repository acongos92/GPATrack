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

import com.constants.Constants;
import com.example.android.gpatrack.R;

import java.util.logging.Logger;

public class AddNewSemesterPopup extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger("AddNewSemester log");

    private EditText semesterCreationEditText;

    /*
     * Click listener implementation
     */
    private View.OnClickListener addClassListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            LOGGER.info("AddNewSemesterPopup create button clicked");
            if(isValidSemesterName (semesterCreationEditText)) {
                Intent i = new Intent(AddNewSemesterPopup.this, AddClassToSemester.class);
                i.putExtra(Constants.SEM_NAME, String.valueOf(semesterCreationEditText.getText()));
                startActivity(i);
            }else {
                makeToast("Semester name was invalid");
                semesterCreationEditText.setText("");
            }
        }
    }
    ;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        LOGGER.info("Start AddNewSemesterPopup view");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_new_semester);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.6));

        semesterCreationEditText = (EditText) findViewById(R.id.new_semester_name_et);





        Button addClass = (Button) findViewById(R.id.create_new_semester_button);
        addClass.setOnClickListener(addClassListener);

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

    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        startActivity(getIntent());
    }






}
