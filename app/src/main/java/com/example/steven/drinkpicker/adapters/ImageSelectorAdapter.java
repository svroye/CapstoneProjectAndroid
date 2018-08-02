//package com.example.steven.drinkpicker.adapters;
//
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.steven.drinkpicker.R;
//import com.example.steven.drinkpicker.fragments.ImageSelectionFragment;
//
//public class ImageSelectorAdapter extends RecyclerView.Adapter<ImageSelectorAdapter.ImageSelectorViewHolder> {
//
//    int mItemCount;
//
//    public ImageSelectorAdapter(int mItemCount) {
//        this.mItemCount = mItemCount;
//    }
//
//    @NonNull
//    @Override
//    public ImageSelectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//        return new ImageSelectorViewHolder(LayoutInflater.from(parent.getContext()), parent);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ImageSelectorViewHolder holder, int i) {
//        if (i == 0) {
//            holder.icon.setImageResource(R.drawable.ic_action_image);
//            holder.title.setText(getString(R.string.take_picture));
//        } else {
//            holder.icon.setImageResource(R.drawable.ic_action_gallery);
//            holder.title.setText(getString(R.string.select_from_gallery));
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    public class ImageSelectorViewHolder extends RecyclerView.ViewHolder {
//
//
//        public ImageSelectorViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//}
