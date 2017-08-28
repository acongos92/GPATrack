package com.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.gpatrack.R;

public class PopupSemesterAdapter extends RecyclerView.Adapter<PopupSemesterAdapter.NumberViewHolder> {

    private static final String TAG = PopupSemesterAdapter.class.getSimpleName();

    private int mNumberItems;

    /**
     * Constructor
     *
     * @param num Number of items to display in list
     */
    public PopupSemesterAdapter(int num) {
        mNumberItems = num;
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
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }


    class NumberViewHolder extends RecyclerView.ViewHolder {

        TextView listItemNumberView;
        public NumberViewHolder(View itemView) {
            // COMPLETED (15) Within the constructor, call super(itemView) and then find listItemNumberView by ID
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
        }

        void bind(int listIndex) {
            listItemNumberView.setText(String.valueOf(listIndex));
        }
    }
}
