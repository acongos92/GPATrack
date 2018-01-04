package com.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.GuiExtensions.DisplayIndividualSemesterClassDeletion;

import com.adapters.DisplayIndividualSemesterAdapter;
import com.database.SemesterDatabaseQuery;
import com.example.android.gpatrack.R;

import java.util.logging.Logger;


/**
 * Created by selle on 9/16/2017.
 */

public class DisplayIndividualSemester extends AppCompatActivity implements DisplayIndividualSemesterAdapter.ClassItemClickListener {

    private static final Logger logger = Logger.getLogger("AddNewClass log");

    private DisplayIndividualSemesterAdapter displaySemesterAdapter;

    private RecyclerView semesterRecyclerView;

    private SemesterDatabaseQuery SDQRead;

    private String SEMESTER_NAME;

    private FloatingActionButton fab;

    private View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent i = new Intent(DisplayIndividualSemester.this, AddClassToSemester.class);
            i.putExtra("semName", SEMESTER_NAME);
            startActivity(i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        logger.info("Start DiusplayIndividualSemester creation");
        super.onCreate(savedInstanceState);

        SDQRead = new SemesterDatabaseQuery(this , false);
        setContentView(R.layout.activity_individual_semester_classes);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        String semesterName = getExtras();
        //writing in for proper database deletion method
        SEMESTER_NAME = semesterName;

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
        ItemTouchHelper.SimpleCallback swipe = setupSwipeCallback();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipe);

        itemTouchHelper.attachToRecyclerView(semesterRecyclerView);
        //Finishes setting up the adapter

        displaySemesterAdapter = new DisplayIndividualSemesterAdapter(this, SDQRead.getAllClassesInASemesters(semesterName) , this);
        SDQRead.closeConnection();
        semesterRecyclerView.setAdapter(displaySemesterAdapter);
        createFAB();



    }

    /*
     * convenience methods
     */
    private void createFAB(){
        logger.info("Start FAB creation");
        fab = (FloatingActionButton) findViewById(R.id.fab_quick_add);
        fab.setSize(FloatingActionButton.SIZE_AUTO);
        fab.setOnClickListener(fabClickListener);
        fab.setClickable(true);
    }
    private ItemTouchHelper.SimpleCallback setupSwipeCallback (){
        return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return true;// true if moved, false otherwise
            }

            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir){
                int pos = viewHolder.getAdapterPosition();
                View view  = semesterRecyclerView.getChildAt(pos);
                String className = displaySemesterAdapter.getTrimmedSwipedName(view);
                confirmSwipeDelete(className, SEMESTER_NAME);

                displaySemesterAdapter.notifyDataSetChanged();

            }
        };
    }
    private String getExtras(){
        Intent intent = getIntent();
        String semesterName = "No Name Found";
            try {
                semesterName = intent.getExtras().getString("semName");
            }catch (NullPointerException e){
                logger.warning("Semester name not placed in intent, whoops");
            }
        return semesterName;
    }

    @Override
    public void onClassItemClick(String semesterItemName){
        //logger.info("DisplayIndividualSemester start onSemesterItemClick");
        //Intent i = new Intent(DisplayIndividualSemester.this, AddClassToSemester.class);
        //Gives semester name to the new activity as extra data
        //i.putExtra("semName", semesterItemName);
        //startActivity(i);
    }

    /**
     * simple toast convenience method
     * @param message
     */
    private void makeToast(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }

    /**
     * ensure database connection closed if it was in use
     */
    @Override
    public void onDestroy(){
        if(SDQRead != null){
            SDQRead.closeConnection();
        }
        super.onDestroy();
    }

    /**
     * handle swipe to delete on individual classes
     * @param className
     * @param semesterName
     */
    public void confirmSwipeDelete(String className , String semesterName){
        String deletePrompt = "Do you wish to delete " + className + "?";
        String yes = "yes";
        String no  ="no";
        DisplayIndividualSemesterClassDeletion listener = new DisplayIndividualSemesterClassDeletion(getApplicationContext(), displaySemesterAdapter);
        listener.setClassName(className);
        listener.setSemName(semesterName);
        AlertDialog dia = new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).
                setTitle("Confirm Delete").setMessage(deletePrompt).setPositiveButton(yes , listener).
                setNegativeButton(no, listener).show();



    }

    /**
     * ensure information from intent is collected
     */
    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
