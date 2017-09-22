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

public class DisplayIndividualSemesterAdapter extends RecyclerView.Adapter<DisplayIndividualSemesterAdapter.ClassViewHolder> {

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
    private final String CLASS_NAME_PREFIX = "Class: ";
    private final String CLASS_GRADE_PREFIX  = "Grade: ";


    //Constructor
    public DisplayIndividualSemesterAdapter(Context context, Cursor cursor, ClassItemClickListener listener) {
        this.mContext = context;
        clickListener = listener;
        this.mCursor = cursor;
    }

    @Override
    public DisplayIndividualSemesterAdapter.ClassViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListeItem = R.layout.activity_individual_semester_classes_recycler_view_content;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListeItem, viewGroup, shouldAttachToParentImmediately);
        DisplayIndividualSemesterAdapter.ClassViewHolder viewHolder = new ClassViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DisplayIndividualSemesterAdapter.ClassViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(SemesterDatabase.ClassEntry.COLUMN_CLASS_NAME));
        //TODO: needs to be converted to letter for display, too lazy to do that as of now
        double preGrade = mCursor.getDouble(mCursor.getColumnIndex(SemesterDatabase.ClassEntry.COLUMN_GRADE));
        String grade = String.valueOf(preGrade);

        holder.classNameItemView.setText(CLASS_NAME_PREFIX + name);
        if (grade.length() > 4){
            holder.classGradeItemView.append(CLASS_GRADE_PREFIX + grade.substring(0,5));
        }else {
            holder.classGradeItemView.append(CLASS_GRADE_PREFIX + grade);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView classNameItemView;
        protected TextView classGradeItemView;

        public ClassViewHolder(View itemView) {
            super(itemView);
            classNameItemView = (TextView) itemView.findViewById(R.id.tv_class_name);
            classGradeItemView = (TextView) itemView.findViewById(R.id.tv_class_grade);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
             clickListener.onClassItemClick(String.valueOf(classNameItemView.getText()));
        }

        public void delete(){
            //wonder what this does
        }


    }
}
