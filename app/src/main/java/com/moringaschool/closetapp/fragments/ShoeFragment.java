package com.moringaschool.closetapp.fragments;

import static com.moringaschool.closetapp.Constants.SECRET_KEY;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.moringaschool.closetapp.Constants;
import com.moringaschool.closetapp.Encryption;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.adapters.ShoesRecyclerAdapter;
import com.moringaschool.closetapp.interfaces.ReveryApi;
import com.moringaschool.closetapp.models.Example;
import com.moringaschool.closetapp.models.ShoePathsDict;
import com.moringaschool.closetapp.models.female.FemaleShoe;
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
    static Example responses;
    static FemaleShoe fResponse;
    //    @BindView(R.id.recyclerviewS)
    public static RecyclerView recyclerView;
    static long time;
    @BindView(R.id.swiperefresh3)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    static String gender = Constants.GENDER;
    static List<String> shoePaths = new ArrayList<>();
    static ShoePathsDict allShoes;
    static com.moringaschool.closetapp.models.female.ShoePathsDict allShoesFemale;
    static List<String> strings = new ArrayList<>();
    static GridLayoutManager gridLayoutManager;
    private static Context context;
    static ShoesRecyclerAdapter adapter;

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
        recyclerView = view.findViewById(R.id.recyclerviewS);
        context = getContext();
        gridLayoutManager = null;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager = new GridLayoutManager(getContext(), 3);

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(getContext(), 2);

        }
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


    public void refresh() {
        if (gender.equals("none")) {
            method("none");
        }

        if (gender.equals("male")) {
            method("male");

        } else if (gender.equals("female")) {
            method("female");

        }


    }

    public static void method(String gender) {
        for (int i = 0; i <= 0; i++) {
            time = System.currentTimeMillis() / 1000;

            String derivedKey = Encryption.pbkdf2(SECRET_KEY, String.valueOf(time), 128, 32);
            Log.d("thekeyisatTommmmmy", derivedKey);
            ReveryApi reveryApi = ReveryClient.getClient();

            if (gender.equals("male")) {
                //  MALE
                Call<Example> call = reveryApi.getShoes(gender, derivedKey, String.valueOf(time));
                call.enqueue(new Callback<Example>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {
                        if (response.isSuccessful()) {
                            Log.d("thekeyisatTommmmmy", "iiinnnnnnTommmm");
                            responses = response.body();
                            allShoes = responses.getShoePathsDict();
                            shoePaths.clear();
                            Collections.addAll(shoePaths, allShoes.getModel13208347(), allShoes.getModel12241518(),
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

                            recyclerView.setLayoutManager(gridLayoutManager);

                            strings = responses.getShoeModelIds();
                           adapter= new ShoesRecyclerAdapter(shoePaths, context,strings);
                            ItemTouchHelper secondItemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
                            secondItemTouchHelper.attachToRecyclerView(recyclerView);
                           // new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
                            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                            itemTouchHelper.attachToRecyclerView(recyclerView);
                            recyclerView.setAdapter(adapter);
                            Log.d("Successtttttttt", "Suuuuuccccceeessssss");
                        }


                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Log.d("failtttttttt", "faillllllllllllllll");

                    }
                });


            }


            if (gender.equals("none")) {
                strings.clear();
                Call<FemaleShoe> call2 = reveryApi.getFemaleShoes("female", derivedKey, String.valueOf(time));
                call2.enqueue(new Callback<FemaleShoe>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<FemaleShoe> call2, retrofit2.Response<FemaleShoe> response) {
                        if (response.isSuccessful()) {
                            fResponse = response.body();
                            allShoesFemale = fResponse.getShoePathsDict();

                            shoePaths.clear();
                            Collections.addAll(shoePaths, allShoesFemale.getModel13300343(),
                                    allShoesFemale.getModel13417676(), allShoesFemale.getModel13483572(), allShoesFemale.getModel12466865(), allShoesFemale.getModel12949144(), allShoesFemale.getModel13440398(), allShoesFemale.getModel14196619(), allShoesFemale.getModel13160864(), allShoesFemale.getModel14235837(),
                                    allShoesFemale.getModel13136326(), allShoesFemale.getModel14421519(), allShoesFemale.getModel13346777(), allShoesFemale.getModel14881242(),
                                    allShoesFemale.getModel14108606(), allShoesFemale.getModel12876015(), allShoesFemale.getModel13229201(), allShoesFemale.getModel14143445(), allShoesFemale.getModel12742869(), allShoesFemale.getModel13015229(), allShoesFemale.getModel13647165(), allShoesFemale.getModel13086347(), allShoesFemale.getModel13104825(), allShoesFemale.getModel13565475(), allShoesFemale.getModel13447432(),
                                    allShoesFemale.getModel14402253(), allShoesFemale.getModel13459469(), allShoesFemale.getModel15033006(), allShoesFemale.getModel13279604(), allShoesFemale.getModel13131087(), allShoesFemale.getModel13440366(), allShoesFemale.getModel14285769(), allShoesFemale.getModel13591062(), allShoesFemale.getModel13241867(), allShoesFemale.getModel13139372(), allShoesFemale.getModel13012154(), allShoesFemale.getModel13109470(), allShoesFemale.getModel13201269(), allShoesFemale.getModel12778777(), allShoesFemale.getModel13466933());

                            Call<Example> call = reveryApi.getShoes("male", derivedKey, String.valueOf(time));
                            call.enqueue(new Callback<Example>() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {
                                    if (response.isSuccessful()) {
                                        responses = response.body();
                                        allShoes = responses.getShoePathsDict();
                                        Collections.addAll(shoePaths, allShoes.getModel13208347(), allShoes.getModel12241518(),
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
//

                                        recyclerView.setLayoutManager(gridLayoutManager);
                                        strings = fResponse.getShoeModelIds();
                                        strings.addAll(responses.getShoeModelIds());
                                        adapter= new ShoesRecyclerAdapter(shoePaths, context,strings);
                                        ItemTouchHelper secondItemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
                                        secondItemTouchHelper.attachToRecyclerView(recyclerView);
                                     //   new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
                                        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                                        itemTouchHelper.attachToRecyclerView(recyclerView);
                                        recyclerView.setAdapter(adapter);
                                        Log.d("Successtoooooommmyyyy", "Suuuuutttttttttttttttttttttttttt");
//
                                    }


                                }

                                @Override
                                public void onFailure(Call<Example> call, Throwable t) {
                                    Log.d("failtttttttt", "faillllllllllllllll");

                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<FemaleShoe> call, Throwable t) {

                    }


                });


            }

            if (gender.equals("female")) {

                Call<FemaleShoe> call = reveryApi.getFemaleShoes(gender, derivedKey, String.valueOf(time));
                call.enqueue(new Callback<FemaleShoe>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<FemaleShoe> call, retrofit2.Response<FemaleShoe> response) {
                        if (response.isSuccessful()) {
                            fResponse = response.body();
                            shoePaths.clear();
                            strings.clear();
                            allShoesFemale = fResponse.getShoePathsDict();
                            Collections.addAll(shoePaths, allShoesFemale.getModel13300343(),
                                    allShoesFemale.getModel13417676(), allShoesFemale.getModel13483572(), allShoesFemale.getModel12466865(), allShoesFemale.getModel12949144(), allShoesFemale.getModel13440398(), allShoesFemale.getModel14196619(), allShoesFemale.getModel13160864(), allShoesFemale.getModel14235837(),
                                    allShoesFemale.getModel13136326(), allShoesFemale.getModel14421519(), allShoesFemale.getModel13346777(), allShoesFemale.getModel14881242(),
                                    allShoesFemale.getModel14108606(), allShoesFemale.getModel12876015(), allShoesFemale.getModel13229201(), allShoesFemale.getModel14143445(), allShoesFemale.getModel12742869(), allShoesFemale.getModel13015229(), allShoesFemale.getModel13647165(), allShoesFemale.getModel13086347(), allShoesFemale.getModel13104825(), allShoesFemale.getModel13565475(), allShoesFemale.getModel13447432(),
                                    allShoesFemale.getModel14402253(), allShoesFemale.getModel13459469(), allShoesFemale.getModel15033006(), allShoesFemale.getModel13279604(), allShoesFemale.getModel13131087(), allShoesFemale.getModel13440366(), allShoesFemale.getModel14285769(), allShoesFemale.getModel13591062(), allShoesFemale.getModel13241867(), allShoesFemale.getModel13139372(), allShoesFemale.getModel13012154(), allShoesFemale.getModel13109470(), allShoesFemale.getModel13201269(), allShoesFemale.getModel12778777(), allShoesFemale.getModel13466933());

                            recyclerView.setLayoutManager(gridLayoutManager);
                            strings = fResponse.getShoeModelIds();
                            adapter = new ShoesRecyclerAdapter(shoePaths, context,strings);
                            //  ArrayList<Garment> garments = (ArrayList<Garment>) responses.getGarments().stream().filter(garment -> garment.getTryon().getCategory().equals(category)).collect(Collectors.toList());
                         ItemTouchHelper secondItemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
                           secondItemTouchHelper.attachToRecyclerView(recyclerView);
                            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                            itemTouchHelper.attachToRecyclerView(recyclerView);
                            recyclerView.setAdapter(adapter);
                            Log.d("Successtttttttt", "Suuuuuccccceeessssss");
//
                        }

                    }

                    @Override
                    public void onFailure(Call<FemaleShoe> call, Throwable t) {

                    }


                });

            }


        }
    }
    static ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(shoePaths, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    static ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            shoePaths.remove(viewHolder.getAdapterPosition());
            strings.remove(viewHolder.getAdapterPosition());
            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

        }
    };
}