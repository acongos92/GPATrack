package com.gui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.adapters.PopupSemesterAdapter;
import com.backend_code.DatabaseDTO;
import com.database.SemesterDatabase;
import com.database.SemesterDatabaseHelper;
import com.database.SemesterDatabaseQuery;
import com.example.android.gpatrack.R;
import com.database.SemesterDatabaseQuery;

import java.util.logging.Logger;

public class SelectSemesterPopup extends Activity {
    private static final Logger logger = Logger.getLogger("AddNewClass log");

    private PopupSemesterAdapter mPopupAdapter;

    private SQLiteDatabase base;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_semester);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.8), (int)(height*0.6));

        //Recycler View
        RecyclerView semesterRecyclerView;
        semesterRecyclerView = (RecyclerView) this.findViewById(R.id.rv_numbers);
        SemesterDatabaseHelper dbHelper = new SemesterDatabaseHelper(this);
        base = dbHelper.getReadableDatabase();
        semesterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Cursor cursor = getAllSemesters();
        mPopupAdapter = new PopupSemesterAdapter(this, cursor);
        semesterRecyclerView.setAdapter(mPopupAdapter);



        Button addClass = (Button) findViewById(R.id.tempButton);
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(SelectSemesterPopup.this, AddToSemester.class));
            }
        });

    }

    private Cursor getAllSemesters(){
        return base.query(SemesterDatabase.ClassEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                SemesterDatabase.ClassEntry.COLUMN_SEMESTER);
    }






}
