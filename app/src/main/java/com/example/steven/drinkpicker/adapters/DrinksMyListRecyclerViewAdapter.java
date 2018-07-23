package com.example.steven.drinkpicker.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.steven.drinkpicker.R;

import java.util.ArrayList;

public class DrinksMyListRecyclerViewAdapter
        extends RecyclerView.Adapter<DrinksMyListRecyclerViewAdapter.DrinkMyListViewHolder> {

    ArrayList<Integer> elements = new ArrayList<>();

    public DrinksMyListRecyclerViewAdapter() {
        for (int i = 0; i < 10; i++) {
            this.elements.add(i);
        }
    }

    @NonNull
    @Override
    public DrinkMyListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_drinks_drink_item, viewGroup, false);
        DrinkMyListViewHolder viewHolder = new DrinkMyListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkMyListViewHolder drinkMyListViewHolder, int i) {
        drinkMyListViewHolder.percentageTextView.setText("" + i);
    }

    @Override
    public int getItemCount() {
        if (null == elements) return 0;
        return elements.size();
    }

    class DrinkMyListViewHolder extends RecyclerView.ViewHolder {

        ImageView drinkImageView;
        TextView nameTextView;
        TextView percentageTextView;

        public DrinkMyListViewHolder(@NonNull View itemView) {
            super(itemView);
            drinkImageView = itemView.findViewById(R.id.mydrinks_item_image);
            nameTextView = itemView.findViewById(R.id.mydrinks_item_name);
            percentageTextView = itemView.findViewById(R.id.mydrinks_item_percentage);
        }
    }
}
