package com.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.database.SemesterDatabase;
import com.example.android.gpatrack.R;





import java.util.logging.Logger;

public class PopupSemesterAdapter extends RecyclerView.Adapter<PopupSemesterAdapter.SemesterViewHolder> {

    /**
     * nested interface define how click listeners will behave within this view
     */
    public interface SemesterItemClickListener {
        void onSemesterItemClick(String semesterItemClicked);
    }

    //adapter variable declarations
    private static final String TAG = PopupSemesterAdapter.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger("PopupSemesterAdapater Logger");
    private Context mContext;
    private SemesterItemClickListener clickListener;
    private Cursor mCursor;

    /**
     * Constructor
     *
     * Click Listener is defined in interface and implemented at the end of the class
     *
     */
    public PopupSemesterAdapter(Context context, Cursor cursor, SemesterItemClickListener listener) {
        this.mContext = context;
        clickListener = listener;
        this.mCursor = cursor;
    }


    /**
     * we override onCreateViewHolder in order to use our own view definitions in implementation
     * by default implementation this would be incorrectly done
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public SemesterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.semester_name_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        SemesterViewHolder viewHolder = new SemesterViewHolder(view);

        return viewHolder;
    }

    /**
     * we override onBindViewHolder in order to properly assign data to be displayed in the view
     * using the class cursor object
     * @param holder the view holder we want to put data in
     * @param position the position data will be assigned to in the scrollable list
     */
    @Override
    public void onBindViewHolder(SemesterViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(SemesterDatabase.ClassEntry.COLUMN_SEMESTER));
        holder.semesterNameItemView.setText(name);
    }

    /**
     * we override getItemCount again so we can use the class cursor object to properly define
     * the total number of items in the list we plan to display
     * @return
     */
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    /**
     * nested class implements the onClickListener so we can define what is done on clicks to items.
     * this viewHolder essentially tracks each individual text view column which contains our data
     * (so in this view holder its tracking just the semester names but could of course hold
     *  multiple views)
     */
    class SemesterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //define the views this viewHolder will be tracking
        protected TextView semesterNameItemView;

        /**
         * constructor for the view holder
         * @param itemView the view which will hold an item of data in the recycler view (single row)
         */
        public SemesterViewHolder(View itemView) {

            super(itemView);

            semesterNameItemView = (TextView) itemView.findViewById(R.id.tv_semester_name);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view){
            LOGGER.info("RECYCLERVIEWCLICKLISTENER start onClick ");
            int clickedPosition = getAdapterPosition();

            LOGGER.info("RECYCLERVIEWCLICKLISTENER made it passed clicked position");
            clickListener.onSemesterItemClick(String.valueOf(semesterNameItemView.getText()));
        }
    }
}
