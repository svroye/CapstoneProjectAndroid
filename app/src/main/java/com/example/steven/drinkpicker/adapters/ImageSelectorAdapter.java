package com.example.steven.drinkpicker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.steven.drinkpicker.R;

public class ImageSelectorAdapter extends
        RecyclerView.Adapter<ImageSelectorAdapter.ImageSelectorViewHolder> {

    private final int mItemCount;
    private Context context;
    private OnListItemClickListener listItemClickListener;

    public interface OnListItemClickListener {
        void onListItemClicked(int position);
    }

    public ImageSelectorAdapter(Context context, int mItemCount, OnListItemClickListener listener) {
        this.mItemCount = mItemCount;
        this.context = context;
        this.listItemClickListener = listener;
    }

    @NonNull
    @Override
    public ImageSelectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ImageSelectorViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSelectorViewHolder holder, int i) {
        if (i == 0) {
            holder.icon.setImageResource(R.drawable.ic_action_image);
            holder.title.setText(context.getString(R.string.take_picture));
        } else {
            holder.icon.setImageResource(R.drawable.ic_action_gallery);
            holder.title.setText(context.getString(R.string.select_from_gallery));
        }
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    public class ImageSelectorViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        final TextView title;
        final ImageView icon;


        ImageSelectorViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_item_list_dialog_item, parent, false));
            title = itemView.findViewById(R.id.option_title);
            icon = itemView.findViewById(R.id.option_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listItemClickListener.onListItemClicked(getAdapterPosition());
        }
    }
}
