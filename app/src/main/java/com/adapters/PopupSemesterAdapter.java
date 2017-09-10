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

    private static final String TAG = PopupSemesterAdapter.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger("PopupSemesterAdapater Logger");
    private SemesterItemClickListener clickListener;

    private Context mContext;

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

    @Override
    public void onBindViewHolder(SemesterViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(SemesterDatabase.ClassEntry.COLUMN_SEMESTER));
        holder.semesterNameItemView.setText(name);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    class SemesterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        protected TextView semesterNameItemView;

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
