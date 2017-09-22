package com.gui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adapters.DisplayIndividualSemesterAdapter;
import com.adapters.DisplaySemesterGPAAdapter;
import com.adapters.PopupSemesterAdapter;
import com.backend_code.DatabaseDTO;
import com.database.SemesterDatabaseQuery;
import com.example.android.gpatrack.R;

import java.util.logging.Logger;

/**
 * Created by selle on 9/16/2017.
 */

public class DisplayIndivudalSemester extends AppCompatActivity implements DisplayIndividualSemesterAdapter.ClassItemClickListener {

    private static final Logger logger = Logger.getLogger("AddNewClass log");

    private DisplayIndividualSemesterAdapter displaySemesterAdapter;

    private SQLiteDatabase base;

    private RecyclerView semesterRecyclerView;

    private SemesterDatabaseQuery SDQ;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SDQ = new SemesterDatabaseQuery(this , true);
        setContentView(R.layout.activity_individual_semester_classes);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        Intent intent = getIntent();
        String semesterName = intent.getExtras().getString("semName");

        /*
         * Recycler view setup
         */
        //Setup the recycler view based on the id in the xml
        semesterRecyclerView = (RecyclerView) this.findViewById(R.id.rv_individual_semester_view);
        //Gets database ready and opens a readable connection

        // Sets the layout manager for the recycler view
        semesterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*
         * item touch helper setup to handle swipe to deletel
         */
        ItemTouchHelper.SimpleCallback swipe = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            public boolean onMove(RecyclerView recyclerView,
                                           RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();
                // move item in `fromPos` to `toPos` in adapter.
                return true;// true if moved, false otherwise
            }

            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir){
                makeToast("lel just got swiped bro");
                //deleteClass("a");
                //TODO: need to shift recycler views to using arrays or lists, using cursor objects sucks for deleting 
                displaySemesterAdapter.notifyDataSetChanged();

            }


        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipe);

        itemTouchHelper.attachToRecyclerView(semesterRecyclerView);
        //Finishes setting up the adapter

        displaySemesterAdapter = new DisplayIndividualSemesterAdapter(this, SDQ.getAllClassesInASemesters(semesterName) , this);

        semesterRecyclerView.setAdapter(displaySemesterAdapter);


    }

    @Override
    public void onClassItemClick(String semesterItemName){
        logger.info("DisplayIndividualSemester start onSemesterItemClick");
        Intent i = new Intent(DisplayIndivudalSemester.this, AddToSemester.class);
        //Gives semester name to the new activity as extra data
        i.putExtra("semName", semesterItemName);
        startActivity(i);
    }

    private void makeToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void deleteClass(String className){
        DatabaseDTO data = new DatabaseDTO(null, className, 0, 0);
        SDQ.removeFromDatabase(data);
    }
}
