package com.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.backend_code.GPACalculation;
import com.database.SemesterDatabase;
import com.example.android.gpatrack.R;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Andy on 9/10/2017.
 */

public class DisplaySemesterGPAAdapter extends RecyclerView.Adapter<DisplaySemesterGPAAdapter.SemesterAndGPAViewHolder>{
    /**
     * nested interface define how click listeners will behave within this view
     */
    public interface DisplaySemesterGPAClickListener {
        void onSemesterGPAClick(String semesterItemClicked);
    }

    //adapter variable declarations
    private static final String TAG = PopupSemesterAdapter.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger("DisplaySemesterGPAAdapter Logger");
    private Context mContext;
    private List<GPACalculation> semestersAndGPA;
    private PopupSemesterAdapter.SemesterItemClickListener clickListener;


    /**
     * Constructor
     *
     * Click Listener is defined in interface and implemented at the end of the class
     *
     */
    public DisplaySemesterGPAAdapter(Context context, List<GPACalculation> list) {
        this.mContext = context;

        this.semestersAndGPA = list;
    }


    /**
     * we override onCreateViewHolder in order to use our own view definitions in implementation
     * by default implementation this would be incorrectly done
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public DisplaySemesterGPAAdapter.SemesterAndGPAViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.homescreen_recycler_view_content;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        DisplaySemesterGPAAdapter.SemesterAndGPAViewHolder viewHolder = new DisplaySemesterGPAAdapter.SemesterAndGPAViewHolder(view);

        return viewHolder;
    }

    /**
     * we override onBindViewHolder in order to properly assign data to be displayed in the view
     * using the class cursor object
     * @param holder the view holder we want to put data in
     * @param position the position data will be assigned to in the scrollable list
     */
    @Override
    public void onBindViewHolder(DisplaySemesterGPAAdapter.SemesterAndGPAViewHolder holder, int position) {
        LOGGER.info("DisplaySemesterGPAAdapter start onBindViewHolder");
        if ((semestersAndGPA.size() < position)){
            return;
        }
        String name = semestersAndGPA.get(position).getSemesterOrClassName();
        double gpa = semestersAndGPA.get(position).calculateGPA();
        String gpas = Double.toString(gpa);
        holder.semesterNameAndGpaNameView.append(name);
        //control display of gpa digits
        if(gpas.length() > 4){
            holder.semesterNameAndGpaGPAview.append(gpas.substring(0,4));
        }else {
            holder.semesterNameAndGpaGPAview.append(gpas);
        }
    }

    /**
     * we override getItemCount again so we can use the class cursor object to properly define
     * the total number of items in the list we plan to display
     * @return items in the cursor
     */
    @Override
    public int getItemCount() {
        return semestersAndGPA.size();
    }

    /**
     * nested class implements the onClickListener so we can define what is done on clicks to items.
     * this viewHolder essentially tracks each individual text view column which contains our data
     * (so in this view holder its tracking just the semester names but could of course hold
     *  multiple views)
     */
    class SemesterAndGPAViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //define the views this viewHolder will be tracking
        protected TextView semesterNameAndGpaNameView;
        protected TextView semesterNameAndGpaGPAview;

        /**
         * constructor for the view holder
         * @param itemView the view which will hold an item of data in the recycler view (single row)
         */
        public SemesterAndGPAViewHolder(View itemView) {

            super(itemView);

            semesterNameAndGpaNameView = (TextView) itemView.findViewById(R.id.tv_semester_name_display);
            semesterNameAndGpaGPAview = (TextView) itemView.findViewById(R.id.tv_semester_grade_point);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view){
            //TODO: this will still work here just want the semester name for intent purpose
            LOGGER.info("RECYCLERVIEWCLICKLISTENER start onClick ");
            int clickedPosition = getAdapterPosition();

            LOGGER.info("RECYCLERVIEWCLICKLISTENER made it passed clicked position");
            clickListener.onSemesterItemClick(String.valueOf(semesterNameAndGpaNameView.getText()));
        }
    }
}

