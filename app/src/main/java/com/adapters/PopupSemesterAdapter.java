package com.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.database.SemesterDatabase;
import com.example.android.gpatrack.R;

import java.util.logging.Logger;

public class PopupSemesterAdapter extends RecyclerView.Adapter<PopupSemesterAdapter.NumberViewHolder> {
    /**
     * nested interface define how click listeners will behave within this view
     */
    public interface SemesterItemClickListener {
        void onSemesterItemClick(int itemClicked);
    }

    private static final String TAG = PopupSemesterAdapter.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger("PopupSemesterAdapater Logger");
    private SemesterItemClickListener clickListener;
    private Context mContext;

    private Cursor mCursor;

    /**
     * Constructor
     *
     * @param cursor Number of items to display in list
     */
    public PopupSemesterAdapter(Context context, Cursor cursor, SemesterItemClickListener listener) {
        this.mContext = context;
        clickListener = listener;
        this.mCursor = cursor;
    }


    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(SemesterDatabase.ClassEntry.COLUMN_SEMESTER));
        holder.listItemNumberView.setText(name);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView listItemNumberView;

        public NumberViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
            itemView.setOnClickListener(this);

        }

        void bind(int listIndex) {
            listItemNumberView.setText(String.valueOf(listIndex));
        }

        @Override
        public void onClick(View view){
            LOGGER.info("RECYCLERVIEWCLICKLISTENER start onClick ");
            int clickedPosition = getAdapterPosition();
            LOGGER.info("RECYCLERVIEWCLICKLISTENER made it passed clicked position");
            clickListener.onSemesterItemClick(clickedPosition);
        }
    }
}
