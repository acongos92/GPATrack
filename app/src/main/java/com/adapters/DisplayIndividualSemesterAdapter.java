package com.adapters;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.backend_code.ClassGrade;
import com.constants.Constants;
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
    private ClassGrade classGrade;
    private final String CLASS_NAME_PREFIX = "Class: ";
    private final String CLASS_GRADE_PREFIX  = "Grade: ";


    //Constructor
    public DisplayIndividualSemesterAdapter(Context context, ClassGrade classGrade, ClassItemClickListener listener) {
        this.mContext = context;
        clickListener = listener;
        this.classGrade = classGrade;
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
        if (classGrade.length() < position) {
            return;
        }
        String name = classGrade.getKeyString(position);
        double preGrade = classGrade.getValueDouble(position);
        String grade = convertToLetterGrade(preGrade);
        String classNameString = CLASS_NAME_PREFIX + name;

        holder.classNameItemView.setText(classNameString);
        if (grade.length() > 4){
            String classGradeString  = CLASS_GRADE_PREFIX + grade.substring(0,5);
            holder.classGradeItemView.setText(classGradeString);
        }else {
            String classGradeString = CLASS_GRADE_PREFIX + grade;
            holder.classGradeItemView.setText(classGradeString);
        }
    }

    private String convertToLetterGrade(double x){
        //have to append + 0.1 to handle float tolerance (should be a constant i know)

        if(x >= Constants.A_MINUS +.1) {
            return "A";
        }else if (x <= Constants.A && x > Constants.B_PLUS) {
            return "A-";
        }
        else if (x <= Constants.B_PLUS && x > Constants.B) {
            return "B+";
        }
        else if (x <= Constants.B && x > Constants.B_MINUS +.1 ) {
            return "B";
        }
        else if (x <= Constants.B_MINUS +.1&& x > Constants.C_PLUS) {
            return "B-";
        }
        else if (x <= Constants.C_PLUS && x > Constants.C) {
            return "C+";
        }
        else if (x <= Constants.C && x > Constants.C_MINUS +.1) {
            return "C";
        }
        else if (x <= Constants.C_MINUS+.1 && x > Constants.D_PLUS) {
            return "C-";
        }
        else if (x <= Constants.D_PLUS && x > Constants.D) {
            return "D+";
        }
        else if (x <= Constants.D && x > 1.0) {
            return "D";
        }
        else{
            return "E";
        }

    }
    public String getSwipedName(View view){
        DisplayIndividualSemesterAdapter.ClassViewHolder holder = new ClassViewHolder(view);
        String name = holder.getSwipedItemName(view);

        return name;
    }


    @Override
    public int getItemCount() {
        return classGrade.length();
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

        public String getSwipedItemName(View view){
            String swipedItem = String.valueOf(classNameItemView.getText());
            return swipedItem;
        }




    }
}
