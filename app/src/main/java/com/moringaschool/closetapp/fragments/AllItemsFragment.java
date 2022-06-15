package com.moringaschool.closetapp.fragments;

import static com.moringaschool.closetapp.Constants.SECRET_KEY;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.moringaschool.closetapp.Constants;
import com.moringaschool.closetapp.Encryption;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.adapters.BottomsRecyclerAdapter;
import com.moringaschool.closetapp.adapters.ItemRecyclerAdapter;
import com.moringaschool.closetapp.interfaces.ReveryApi;
import com.moringaschool.closetapp.models.Garment;
import com.moringaschool.closetapp.models.Response;
import com.moringaschool.closetapp.network.ReveryClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;


public class AllItemsFragment extends Fragment {
    private static RecyclerView.LayoutManager gridLayoutManager;

    public static Context allContext;
    public static Response responses;
    static RecyclerView AllItemsRecyclerView;
    long time;
    public static ItemRecyclerAdapter adapter;
    public static List<Garment> garments;

    //    @BindView(R.id.swiperefresh)
//    public static
    public static SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Intent intent = getActivity().getIntent();
//        response = (Response) intent.getSerializableExtra("Response");
        return inflater.inflate(R.layout.fragment_all_items, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        AllItemsRecyclerView = (RecyclerView) view.findViewById(R.id.all_itemRA);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);

        display();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Constants.GARMENTS = Constants.RESTORE;
                display();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        progressBar.setVisibility(View.GONE);
    }


    void display() {

        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager = new GridLayoutManager(getContext(), 3);

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(getContext(), 2);

        }
        AllItemsRecyclerView.setLayoutManager(gridLayoutManager);
        allContext = getContext();
        garments = Constants.GARMENTS;
        adapter = new ItemRecyclerAdapter(garments, allContext);
        AllItemsRecyclerView.setAdapter(adapter);

    }

    public static void externalRefreshLayout() {
        AllItemsRecyclerView.setLayoutManager(gridLayoutManager);
        garments = Constants.GARMENTS;
        adapter = new ItemRecyclerAdapter(garments, allContext);
        AllItemsRecyclerView.setAdapter(adapter);
    }

}