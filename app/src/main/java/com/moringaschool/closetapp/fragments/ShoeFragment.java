package com.moringaschool.closetapp.fragments;

import static com.moringaschool.closetapp.Constants.SECRET_KEY;

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
import com.moringaschool.closetapp.adapters.ShoesRecyclerAdapter;
import com.moringaschool.closetapp.interfaces.ReveryApi;
import com.moringaschool.closetapp.models.Example;
import com.moringaschool.closetapp.models.ShoePathsDict;
import com.moringaschool.closetapp.network.ReveryClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;


public class ShoeFragment extends Fragment {

    ReveryApi reveryApi;
    Example responses;
    @BindView(R.id.recyclerviewS)
    RecyclerView recyclerView;
    long time;
    @BindView(R.id.swiperefresh3)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    String gender = "male";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        refresh();
        return inflater.inflate(R.layout.fragment_shoe, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
       });
        refresh();
        progressBar.setVisibility(View.GONE);
    }


    private void refresh() {
        for (int i = 0; i <= 1; i++) {
            time = System.currentTimeMillis() / 1000;

            String derivedKey = Encryption.pbkdf2(SECRET_KEY, String.valueOf(time), 128, 32);
            Log.d("thekeyisatCreate", derivedKey);

            ReveryApi reveryApi = ReveryClient.getClient();
            Call<Example> call = reveryApi.getShoes(gender, derivedKey, String.valueOf(time));
            call.enqueue(new Callback<Example>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {
                    if (response.isSuccessful()) {
                        responses = response.body();
                        ArrayList<String> shoepaths = new ArrayList<>();
                        ShoePathsDict allShoes = responses.getShoePathsDict();
                        Collections.addAll(shoepaths, allShoes.getModel13208347(), allShoes.getModel12241518(),
                                allShoes.getModel12982074(), allShoes.getModel14059215(), allShoes.getModel14118877(),
                                allShoes.getModel13903163(), allShoes.getModel15138790(), allShoes.getModel13208328(),
                                allShoes.getModel13319225(), allShoes.getModel12900360(), allShoes.getModel13590102(),
                                allShoes.getModel13920494(), allShoes.getModel13590127(), allShoes.getModel13786388(),
                                allShoes.getModel13654244(), allShoes.getModel15147224(), allShoes.getModel14038438(),
                                allShoes.getModel13919837(), allShoes.getModel15006661(), allShoes.getModel15024291(),
                                allShoes.getModel15189033(), allShoes.getModel14038461(), allShoes.getModel13743996(),
                                allShoes.getModel13944054(), allShoes.getModel13738916(), allShoes.getModel13654211(),
                                allShoes.getModel13903123(), allShoes.getModel13862238(), allShoes.getModel13952035(),
                                allShoes.getModel14127779(), allShoes.getModel15016762(), allShoes.getModel14786919());
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        //  ArrayList<Garment> garments = (ArrayList<Garment>) responses.getGarments().stream().filter(garment -> garment.getTryon().getCategory().equals(category)).collect(Collectors.toList());
                        recyclerView.setAdapter(new ShoesRecyclerAdapter(shoepaths, getContext()));
                        Log.d("Successtttttttt", "Suuuuuccccceeessssss");
//                            FragmentManager fragmentManager = getSupportFragmentManager();
//                            AllItemsFragment fragment = new AllItemsFragment();
//                            fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

                    }
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    Log.d("failtttttttt", "faillllllllllllllll");

                }
            });
        }

    }
}