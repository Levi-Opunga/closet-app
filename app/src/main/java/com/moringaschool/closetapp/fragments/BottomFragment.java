package com.moringaschool.closetapp.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.moringaschool.closetapp.Constants;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.adapters.BottomsRecyclerAdapter;
import com.moringaschool.closetapp.interfaces.ReveryApi;
import com.moringaschool.closetapp.models.Garment;
import com.moringaschool.closetapp.models.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BottomFragment extends Fragment {
    private static BottomsRecyclerAdapter adapter;
    private static Context context;
    ReveryApi reveryApi;
    Response responses;
    //    @BindView(R.id.recyclerviewB)recyclerviewB
    public static
    RecyclerView recyclerViewBottom;
    long time;
    @BindView(R.id.swiperefresh2)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    static String category = "bottoms";
    static List<Garment> garments;
    static GridLayoutManager gridLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_bottom, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        recyclerViewBottom = (RecyclerView) view.findViewById(R.id.recyclerviewB);
        display();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //  garments =(ArrayList<Garment>) Constants.GARMENTS;
                externalRefreshLayout();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        progressBar.setVisibility(View.GONE);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    void display() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager = new GridLayoutManager(getContext(), 3);

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(getContext(), 2);

        }
        recyclerViewBottom.setLayoutManager(gridLayoutManager);
        context = getContext();
        garments = (ArrayList<Garment>) Constants.GARMENTS.stream().filter(garment -> garment.getTryon().getCategory().equals(category)).collect(Collectors.toList());
        adapter = new BottomsRecyclerAdapter(garments, getContext());
        recyclerViewBottom.setAdapter(adapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void externalRefreshLayout() {
        recyclerViewBottom.setLayoutManager(gridLayoutManager);
        garments = (ArrayList<Garment>) Constants.GARMENTS.stream().filter(garment -> garment.getTryon().getCategory().equals(category)).collect(Collectors.toList());
        Log.d("LOG", "CALLLED IT");
        adapter = new BottomsRecyclerAdapter(garments, context);
        recyclerViewBottom.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}