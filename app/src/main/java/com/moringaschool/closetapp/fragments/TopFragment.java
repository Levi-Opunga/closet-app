package com.moringaschool.closetapp.fragments;

import static com.moringaschool.closetapp.Constants.GARMENTS;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.adapters.TopsRecyclerAdapter;
import com.moringaschool.closetapp.interfaces.ReveryApi;
import com.moringaschool.closetapp.models.Garment;
import com.moringaschool.closetapp.models.Response;

import java.util.ArrayList;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TopFragment extends Fragment {
    public static Context topContext;
    private static RecyclerView.LayoutManager gridLayoutManager;

    ReveryApi reveryApi;
    Response responses;
    public static RecyclerView recyclerViewTop;
    long time;
    @BindView(R.id.swiperefresh2)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    static String category = "tops";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_top, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        recyclerViewTop = view.findViewById(R.id.recyclerviewT);
        display();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                display();

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
        recyclerViewTop.setLayoutManager(gridLayoutManager);
        topContext = getContext();
        ArrayList<Garment> garments = (ArrayList<Garment>) GARMENTS.stream().filter(garment -> garment.getTryon().getCategory().equals(category)).collect(Collectors.toList());
        recyclerViewTop.setAdapter(new TopsRecyclerAdapter(garments, topContext));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void externalRefreshLayout() {

        recyclerViewTop.setLayoutManager(gridLayoutManager);
        ArrayList<Garment> garments = (ArrayList<Garment>) GARMENTS.stream().filter(garment -> garment.getTryon().getCategory().equals(category)).collect(Collectors.toList());
        recyclerViewTop.setAdapter(new TopsRecyclerAdapter(garments, topContext));

    }


}