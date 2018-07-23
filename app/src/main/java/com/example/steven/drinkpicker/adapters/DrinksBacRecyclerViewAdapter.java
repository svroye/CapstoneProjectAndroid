package com.example.steven.drinkpicker.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.steven.drinkpicker.R;

import java.util.ArrayList;

public class DrinksBacRecyclerViewAdapter extends
        RecyclerView.Adapter<DrinksBacRecyclerViewAdapter.DrinkViewHolder> {

    ArrayList<Integer> numbers = new ArrayList<>();

    public DrinksBacRecyclerViewAdapter() {
        for (int i = 0; i < 10; i++) {
            this.numbers.add(i);
        }
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
        Log.d("ADAPTER", "POS= " + this.numbers.get(i));
        drinkViewHolder.timeTextView.setText("" + this.numbers.get(i));
    }

    @Override
    public int getItemCount() {
        if (null == numbers) return 0;
        return numbers.size();
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
