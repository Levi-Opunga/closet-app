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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.adapters.TopsRecyclerAdapter;
import com.moringaschool.closetapp.interfaces.ReveryApi;
import com.moringaschool.closetapp.models.Garment;
import com.moringaschool.closetapp.models.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TopFragment extends Fragment {
    public static Context topContext;
    private static RecyclerView.LayoutManager gridLayoutManager;
    private static TopsRecyclerAdapter adapter;
    ReveryApi reveryApi;
    Response responses;
    public static RecyclerView recyclerViewTop;
    long time;
    @BindView(R.id.swiperefresh2)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    static String category = "tops";
    static ArrayList<Garment> garments;

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
        garments = (ArrayList<Garment>) GARMENTS.stream().filter(garment -> garment.getTryon().getCategory().equals(category)).collect(Collectors.toList());
      adapter = new TopsRecyclerAdapter(garments, topContext);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewTop);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewTop);
        recyclerViewTop.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void externalRefreshLayout() {

        recyclerViewTop.setLayoutManager(gridLayoutManager);
        garments = (ArrayList<Garment>) GARMENTS.stream().filter(garment -> garment.getTryon().getCategory().equals(category)).collect(Collectors.toList());
       adapter= new TopsRecyclerAdapter(garments, topContext);
        recyclerViewTop.setAdapter(adapter);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(garments, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };


    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            garments.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
        }
    };

}