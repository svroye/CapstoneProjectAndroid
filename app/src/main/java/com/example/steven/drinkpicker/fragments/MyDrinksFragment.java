package com.example.steven.drinkpicker.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.steven.drinkpicker.R;
import com.example.steven.drinkpicker.adapters.DrinksMyListRecyclerViewAdapter;
import com.example.steven.drinkpicker.adapters.MyDrinkRecyclerViewAdapter;
import com.example.steven.drinkpicker.objects.Drink;
import com.example.steven.drinkpicker.objects.DrinkDiscovery;
import com.example.steven.drinkpicker.utils.FirebaseUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MyDrinksFragment extends Fragment {

    public final String LOG_TAG = "MyDrinksFragment";

    @BindView(R.id.mydrinks_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.fab_mydrinks) FloatingActionButton fab;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.emptyView) TextView emptyTextView;

    private OnListFragmentInteractionListener mListener;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private DatabaseReference db;

    private MyDrinkRecyclerViewAdapter adapter;

    private List<DrinkDiscovery> dataList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyDrinksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_drinks, container, false);
        ButterKnife.bind(this, view);
        showLoadingIndicator();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                        false);
        adapter = new MyDrinkRecyclerViewAdapter(mListener, getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        initializeScreen();
        return view;
    }

    @OnClick(R.id.fab_mydrinks)
    public void addDrink() {
        mListener.onFabMyDrinksClicked();
    }

    private void initializeScreen() {
        db = mDatabase.child(FirebaseUtils.CHILD_USERS).child(mAuth.getCurrentUser().getUid());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataList = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    DrinkDiscovery drinkDiscovery = snapshot.getValue(DrinkDiscovery.class);
                    Log.d(LOG_TAG, drinkDiscovery.getImageUri());
                    dataList.add(drinkDiscovery);
                }

                if (dataList.size() == 0) {
                    emptyTextView.setVisibility(View.VISIBLE);
                } else {
                    emptyTextView.setVisibility(View.INVISIBLE);
                }
                adapter.swapData(dataList);
                hideLoadingIndicator();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(LOG_TAG, "An error occurred when loading the data: " + databaseError);
                emptyTextView.setVisibility(View.INVISIBLE);
                Snackbar.make(recyclerView, R.string.error_message_loading_data, Snackbar.LENGTH_LONG).show();
                hideLoadingIndicator();
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onFabMyDrinksClicked();
    }

    @SuppressLint("RestrictedApi")
    private void showLoadingIndicator(){
        Log.d(LOG_TAG, "Show ProgressBar");

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
    }

    @SuppressLint("RestrictedApi")
    private void hideLoadingIndicator(){
        Log.d(LOG_TAG, "Hide ProgressBar");
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
    }
}
