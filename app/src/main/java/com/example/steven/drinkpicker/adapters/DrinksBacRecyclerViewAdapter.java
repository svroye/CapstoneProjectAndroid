package com.example.steven.drinkpicker.adapters;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.steven.drinkpicker.R;
import com.example.steven.drinkpicker.utils.DateTimeUtils;
import com.example.steven.drinkpicker.database.DrinkBacContract;

public class DrinksBacRecyclerViewAdapter extends
        RecyclerView.Adapter<DrinksBacRecyclerViewAdapter.DrinkViewHolder> {

    private Cursor data;

    public DrinksBacRecyclerViewAdapter(Cursor cursor) {
        this.data = cursor;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bac_drink_list_item, viewGroup, false);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder drinkViewHolder, int i) {
        data.moveToPosition(i);
        drinkViewHolder.nameTextView.setText(data.getString(data.getColumnIndex(
                DrinkBacContract.DrinkBacEntry.COLUMN_DRINK_NAME)));
        drinkViewHolder.percentageTextView.setText(data.getString(data.getColumnIndex(
                DrinkBacContract.DrinkBacEntry.COLUMN_ALCOHOL_PERCENTAGE)));
        drinkViewHolder.volumeTextView.setText(data.getString(data.getColumnIndex(
                DrinkBacContract.DrinkBacEntry.COLUMN_DRINK_VOLUME)));
        String time = DateTimeUtils.getFormattedDate(data.getLong(data.getColumnIndex(
                DrinkBacContract.DrinkBacEntry.COLUMN_START_TIME)));
        drinkViewHolder.timeTextView.setText(time);
    }

    @Override
    public int getItemCount() {
        if (null == data) return 0;
        return data.getCount();
    }

    class DrinkViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView percentageTextView;
        TextView volumeTextView;
        TextView timeTextView;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.drink_name);
            percentageTextView = itemView.findViewById(R.id.drink_percentage);
            volumeTextView = itemView.findViewById(R.id.drink_volume);
            timeTextView = itemView.findViewById(R.id.drink_time);
        }
    }
}
