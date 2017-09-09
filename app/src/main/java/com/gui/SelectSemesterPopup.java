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
import android.widget.Toast;

import com.adapters.PopupSemesterAdapter;
import com.backend_code.DatabaseDTO;
import com.database.SemesterDatabase;
import com.database.SemesterDatabaseHelper;
import com.database.SemesterDatabaseQuery;
import com.example.android.gpatrack.R;
import com.database.SemesterDatabaseQuery;

import java.util.logging.Logger;

public class SelectSemesterPopup extends AppCompatActivity implements PopupSemesterAdapter.SemesterItemClickListener {
    private static final Logger logger = Logger.getLogger("AddNewClass log");

    private PopupSemesterAdapter mPopupAdapter;

    private SQLiteDatabase base;

    private RecyclerView semesterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

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
        SemesterDatabaseHelper dbHelper = new SemesterDatabaseHelper(this);
        base = dbHelper.getReadableDatabase();
        // Sets the layout manager for the recycler view
        semesterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Cursor cursor = getAllSemesters();
        //Finishes setting up the adapter
        mPopupAdapter = new PopupSemesterAdapter(this, cursor, this);
        semesterRecyclerView.setAdapter(mPopupAdapter);



        Button addClass = (Button) findViewById(R.id.tempButton);
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(SelectSemesterPopup.this, AddToSemester.class));
            }
        });

    }

    /**
     * Method that returns all the semesters in a Cursor object the database MUST be open
     * for this transaction to happen correctly.
     *
     *
     * @return Cursor object that contains all the semesters
     */
    private Cursor getAllSemesters(){
        return base.query(SemesterDatabase.ClassEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                SemesterDatabase.ClassEntry.COLUMN_SEMESTER);
    }

    @Override
    public void onSemesterItemClick(int clickedItemIndex){
        logger.info("SELECTSEMESTERPOPUP start onSemesterItemClick");
        makeToast("Item " + clickedItemIndex + " was clicked");
    }

    private void makeToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }





}
