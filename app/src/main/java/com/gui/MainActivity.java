package com.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.adapters.DisplaySemesterGPAAdapter;
import com.backend_code.ClassGrade;
import com.backend_code.GPACalculation;
import com.database.SemesterDatabaseQuery;
import com.example.android.gpatrack.R;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DisplaySemesterGPAAdapter.DisplaySemesterGPAClickListener{

    private RecyclerView homeScreenRecylcerView;

    private SemesterDatabaseQuery SDQ;

    private DisplaySemesterGPAAdapter recylcerViewAdapter;

    private Logger logger = Logger.getLogger("MainActivity logger");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_semesters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //setup database connection and grab required list from the database
        SDQ = new SemesterDatabaseQuery (this , false);
        List<GPACalculation> gpaCalcs = new LinkedList<>();
        gpaCalcs  = SDQ.getAllSemesterNamesAndGPA();

        /*
         * Recycler view setup
         */
        //Setup the recycler view based on the id in the xml
        homeScreenRecylcerView = (RecyclerView) this.findViewById(R.id.rv_homeScreen);

        // Sets the layout manager for the recycler view
        homeScreenRecylcerView.setLayoutManager(new LinearLayoutManager(this));

        //Finishes setting up the adapter
        recylcerViewAdapter = new DisplaySemesterGPAAdapter(this, gpaCalcs, this);

        homeScreenRecylcerView.setAdapter(recylcerViewAdapter);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SDQ.closeConnection();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.all_semesters, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (id == R.id.add_new_class) {
            startActivity(new Intent(MainActivity.this, SelectSemesterPopup.class));
        } else if (id == R.id.add_new_semester) {
            startActivity(new Intent(MainActivity.this, AddNewSemesterPopup.class));
        } else if (id == R.id.current_gpa) {
            startActivity(new Intent(MainActivity.this, DisplayGPAStatistics.class));
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_share) {
            makeToast("Share Feature Coming soon");
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_send) {
            makeToast("Send Feature Coming soon");
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onSemesterGPAClick(String semesterItemName){
        logger.info("MAINACTIVITY start onSemesterItemClick");
        Intent i = new Intent(MainActivity.this, DisplayIndivudalSemester.class);
        logger.info(semesterItemName);
        i.putExtra("semName", String.valueOf(semesterItemName));
        startActivity(i);
    }

    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        startActivity(getIntent());

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
}
