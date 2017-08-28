package com.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.adapters.PopupSemesterAdapter;
import com.backend_code.DatabaseDTO;
import com.database.SemesterDatabaseQuery;
import com.example.android.gpatrack.R;
import com.database.SemesterDatabaseQuery;

import java.util.logging.Logger;

public class SelectSemesterPopup extends Activity {
    private static final Logger logger = Logger.getLogger("AddNewClass log");

    private static final int NUM_TO_LIST = 100;
    private PopupSemesterAdapter mPopupAdapter;
    private RecyclerView mNumbersList;

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
        mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);
        mPopupAdapter = new PopupSemesterAdapter(NUM_TO_LIST);
        mNumbersList.setAdapter(mPopupAdapter);

        SemesterDatabaseQuery sdq = new SemesterDatabaseQuery(this, false);
        String [] array = sdq.queryAllSemester();
        logger.info(array.toString());


        Button addClass = (Button) findViewById(R.id.tempButton);
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(SelectSemesterPopup.this, AddToSemester.class));
            }
        });

    }







}
