package com.moringaschool.closetapp.fragments;

import static com.moringaschool.closetapp.Constants.SECRET_KEY;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.moringaschool.closetapp.Encryption;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.adapters.ItemRecyclerAdapter;
import com.moringaschool.closetapp.adapters.TopsRecyclerAdapter;
import com.moringaschool.closetapp.interfaces.ReveryApi;
import com.moringaschool.closetapp.models.Garment;
import com.moringaschool.closetapp.models.Response;
import com.moringaschool.closetapp.network.ReveryClient;

import java.util.ArrayList;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;


public class TopFragment extends Fragment {
    public static Context topContext;

    ReveryApi reveryApi;
    Response responses;
    @BindView(R.id.recyclerviewT)
    RecyclerView recyclerView;
    long time;
    @BindView(R.id.swiperefresh2)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    String category = "tops";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        refresh();
        return inflater.inflate(R.layout.fragment_top, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        progressBar.setVisibility(View.GONE);
    }


    private void refresh() {
        for (int i = 0; i <= 1; i++) {
            time = System.currentTimeMillis() / 1000;

            String derivedKey = Encryption.pbkdf2(SECRET_KEY, String.valueOf(time), 128, 32);
            Log.d("thekeyisatCreate", derivedKey);

            ReveryApi reveryApi = ReveryClient.getClient();
            Call<Response> call = reveryApi.getAllGarments(derivedKey, String.valueOf(time));
            call.enqueue(new Callback<Response>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.isSuccessful()) {
                        responses = response.body();
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        topContext = getContext();
                        ArrayList<Garment> garments = (ArrayList<Garment>) responses.getGarments().stream().filter(garment -> garment.getTryon().getCategory().equals(category)).collect(Collectors.toList());
                        recyclerView.setAdapter(new TopsRecyclerAdapter(garments, topContext));
                        Log.d("Success", "Suuuuuccccceeessssss");
//                            FragmentManager fragmentManager = getSupportFragmentManager();
//                            AllItemsFragment fragment = new AllItemsFragment();
//                            fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.d("fail", "faillllllllllllllll");

                }
            });
        }

    }
}