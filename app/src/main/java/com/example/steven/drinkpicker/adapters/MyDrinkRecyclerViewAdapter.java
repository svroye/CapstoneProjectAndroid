package com.example.steven.drinkpicker.adapters;

import android.content.Context;
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
import com.example.steven.drinkpicker.utils.GlideApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyDrinkRecyclerViewAdapter extends RecyclerView.Adapter<MyDrinkRecyclerViewAdapter.ViewHolder> {

    private List<DrinkDiscovery> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public MyDrinkRecyclerViewAdapter(OnListFragmentInteractionListener listener, Context context) {
        mListener = listener;
        this.context = context;
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
        holder.percentageTv.setText(String.valueOf(drink.getAlcoholConcentration()));
        StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl(drink.getImageUri());

        GlideApp.with(context)
                .load(gsReference)
                //.load("https://www.thecocktailproject.com/sites/default/files/incredible-thumb-DeKuyper-A-to-Z-Kamikaze.jpg")
                .into(holder.pictureIv);

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
