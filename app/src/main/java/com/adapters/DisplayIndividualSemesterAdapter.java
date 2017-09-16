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

public class DisplayIndividualSemesterAdapter {

    //Nested Interface to define click listeners
    public interface ClassItemClickListener {
        void onClassItemClick(String classItemClicked);
    }

    //adapter variable declarations
    private static final String TAG = PopupSemesterAdapter.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger("DisplayIndiSemesterAdapter Logger");
    private Context mContext;
    private ClassItemClickListener clickListener;
    private Cursor mCursor;


    //Constructor
    void DisplayIndividualSemesterAdapter(Context context, Cursor cursor, ClassItemClickListener listener){
        this.mContext = context;
        clickListener = listener;
        this.mCursor = cursor;
    }

    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();
        int layoutIdForListeItem = R.layout.activity_individual_semester_classes_recycler_view_content;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListeItem, viewGroup, shouldAttachToParentImmediately);
        ClassViewHolder viewHolder = new ClassViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ClassViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(SemesterDatabase.ClassEntry.COLUMN_SEMESTER));

        holder.classNameItemView.setText(name);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

}

class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
    protected TextView classNameItemView;

    public ClassViewHolder(View itemView) {
        super(itemView);
        classNameItemView = (TextView) itemView.findViewById(R.id.tv_class_name);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        clickListener.onClassItemClick(String.valueOf(classNameItemView.getText()));
    }


}
