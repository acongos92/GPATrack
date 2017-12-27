package com.GuiExtensions;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.adapters.DisplayIndividualSemesterAdapter;
import com.backend_code.DatabaseDTO;
import com.database.SemesterDatabaseQuery;
import com.gui.DisplayIndivudalSemester;

/**
 * Created by Andy on 12/24/2017. handles listener functionality for swipe to delete
 */

public class DisplayIndividualSemesterClassDeletion extends DisplayIndivudalSemester implements DialogInterface.OnClickListener {
        private String className;
        private String semName;
        private Context context;
        private DisplayIndividualSemesterAdapter displaySemesterAdapter;

        public DisplayIndividualSemesterClassDeletion(Context context, DisplayIndividualSemesterAdapter ad){
            this.context = context;
            this.displaySemesterAdapter = ad;

        }
        @Override
        public void onClick(DialogInterface dialog, int which){
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    deleteClass(this.className, this.semName);
                    makeToast("Class was removed");
                    dialog.dismiss();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    makeToast("class was not removed");
                    dialog.dismiss();
                    break;
            }
        }
        public void setClassName(String className){
            this.className = className;
        }
        public void setSemName(String semName){
            this.semName = semName;
        }
        public void setContext(Context context) {this.context = context;}
        /*
         * helpers
         */
        private void makeToast(String message){
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this.context, message, duration);
            toast.show();
        }
    public void deleteClass(String className, String semesterName){
        SemesterDatabaseQuery SDQWrite = new SemesterDatabaseQuery(this.context, true);
        DatabaseDTO data = new DatabaseDTO(semesterName, className, 0, 0);
        SDQWrite.removeFromDatabase(data);
        displaySemesterAdapter.deleteClassFromAdapter(className);
        SDQWrite.closeConnection();
    }

}
