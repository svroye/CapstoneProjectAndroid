package com.example.steven.drinkpicker.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.steven.drinkpicker.R;
import com.example.steven.drinkpicker.adapters.DrinksBacRecyclerViewAdapter;
import com.example.steven.drinkpicker.asycntasks.AsyncTaskCompleteListener;
import com.example.steven.drinkpicker.asycntasks.DrinkBacAsyncTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BloodAlcoholConcentrationFragment extends Fragment
                implements AsyncTaskCompleteListener<Cursor>{


    @BindView(R.id.fab_bac) FloatingActionButton fab;
    @BindView(R.id.bac_value) TextView bacValue;
    @BindView(R.id.bac_recyclerView) RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;
    private DrinksBacRecyclerViewAdapter adapter;
    private Cursor cursor;

    public BloodAlcoholConcentrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blood_alcohol_concentration, container, false);
        ButterKnife.bind(this, view);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFabBacClicked();
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        (new DrinkBacAsyncTask(getContext(), this)).execute();
        return view;
    }

    @Override
    public void onTaskComplete(Cursor result) {
        cursor = result;
        adapter = new DrinksBacRecyclerViewAdapter(result);
        recyclerView.setAdapter(adapter);
        Log.d(getClass().getSimpleName().toString(), "CURSOR COUNT: " + result.getCount());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onFabBacClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
