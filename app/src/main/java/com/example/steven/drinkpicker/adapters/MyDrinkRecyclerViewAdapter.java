package com.example.steven.drinkpicker.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.steven.drinkpicker.R;
import com.example.steven.drinkpicker.fragments.MyDrinksFragment.OnListFragmentInteractionListener;
import com.example.steven.drinkpicker.objects.DrinkDiscovery;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyDrinkRecyclerViewAdapter extends RecyclerView.Adapter<MyDrinkRecyclerViewAdapter.ViewHolder> {

    private List<DrinkDiscovery> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyDrinkRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_drinks_drink_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DrinkDiscovery drink = mValues.get(position);

        holder.nameTv.setText(drink.getName());
        holder.percentageTv.setText("" + drink.getAlcoholConcentration());

    }

    @Override
    public int getItemCount() {
        if (null == mValues) return 0;
        return mValues.size();
    }

    public void addItem(DrinkDiscovery drinkDiscovery) {
        mValues.add(drinkDiscovery);
        notifyDataSetChanged();
    }

    public void swapData(List<DrinkDiscovery> dataList) {
        mValues = dataList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mydrinks_item_image) ImageView pictureIv;
        @BindView(R.id.mydrinks_item_name) TextView nameTv;
        @BindView(R.id.mydrinks_item_percentage) TextView percentageTv;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
