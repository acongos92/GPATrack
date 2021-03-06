package com.gui;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adapters.PopupSemesterAdapter;
import com.constants.Constants;
import com.database.SemesterDatabaseQuery;
import com.example.android.gpatrack.R;

import java.util.logging.Logger;

public class SelectSemesterPopup extends AppCompatActivity  {
    private static final Logger logger = Logger.getLogger("AddNewClass log");

    private PopupSemesterAdapter mPopupAdapter;

    private SQLiteDatabase base;

    private RecyclerView semesterRecyclerView;

    private SemesterDatabaseQuery SDQ;

    /*
     * click listener implementation
     */
    private PopupSemesterAdapter.SemesterItemClickListener semItemListener = new PopupSemesterAdapter.SemesterItemClickListener() {
        @Override
        public void onSemesterItemClick(String semesterItemName) {
            logger.info("SELECTSEMESTERPOPUP start onSemesterItemClick");
            Intent i = new Intent(SelectSemesterPopup.this, AddClassToSemester.class);
            //Gives semester name to the new activity as extra data
            i.putExtra(Constants.SEM_NAME, semesterItemName);
            startActivity(i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SDQ = new SemesterDatabaseQuery(this , false);
        setContentView(R.layout.select_semester);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.8), (int)(height*0.6));

        /*
         * Recycler view setup
         */
        //Setup the recycler view based on the id in the xml
        semesterRecyclerView = (RecyclerView) this.findViewById(R.id.rv_numbers);
        //Gets database ready and opens a readable connection

        // Sets the layout manager for the recycler view
        semesterRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Finishes setting up the adapter
        mPopupAdapter = new PopupSemesterAdapter(this, SDQ.getUniqueSemesters(), semItemListener);

        semesterRecyclerView.setAdapter(mPopupAdapter);



        Button addClass = (Button) findViewById(R.id.tempButton);
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(SelectSemesterPopup.this, AddNewSemesterPopup.class));
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
