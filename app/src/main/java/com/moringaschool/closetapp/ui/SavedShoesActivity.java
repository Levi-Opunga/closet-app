package com.moringaschool.closetapp.ui;

import static com.moringaschool.closetapp.fragments.AllItemsFragment.garments;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.closetapp.Constants;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.adapters.ItemPagerAdapter;
import com.moringaschool.closetapp.adapters.SavedItemPagerAdapter;
import com.moringaschool.closetapp.adapters.SavedShoesRecyclerAdapter;
import com.moringaschool.closetapp.fragments.GenderFilterFragment;
import com.moringaschool.closetapp.models.Garment;
import com.moringaschool.closetapp.models.Shoe;

import java.util.ArrayList;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedShoesActivity extends AppCompatActivity {
    @BindView(R.id.recyclerviewSavedShoe)
    RecyclerView recyclerView;
    ArrayList<Shoe> savedShoes = new ArrayList<>();
    private DatabaseReference reference;
    SavedShoesRecyclerAdapter adapter;
    @BindView(R.id.swiperefresh3)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBarShoe)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_shoes);
        ButterKnife.bind(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadShoes();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        loadShoes();

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

        }
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new SavedShoesRecyclerAdapter(savedShoes, getApplicationContext());
        recyclerView.setAdapter(adapter);

    }


    void loadShoes() {
        for (int i = 0; i <= 0; i++) {
            reference = FirebaseDatabase.getInstance().getReference("Shoes").child(Constants.uid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    savedShoes.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Shoe shoe = dataSnapshot.getValue(Shoe.class);
                        savedShoes.add(shoe);
                        adapter.notifyDataSetChanged();
                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        ButterKnife.bind(this);
        Constants.saved = true;

        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setVisible(false);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Enter Filter Phrase");
        searchView.setVisibility(View.GONE);
        MenuItem more = menu.findItem(R.id.more);
        ImageView image = (ImageView) more.getActionView();
        Drawable res = getResources().getDrawable(R.drawable.ic_baseline_more_vert_24);
        image.setImageDrawable(res);
        image.setOnClickListener(this::showPopupMenu);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextSubmit(String query) {

                progressBar.setVisibility(View.VISIBLE);

//                Constants.GARMENTS = garments.stream()
//                        .filter(g -> g.getBrand().toLowerCase().contains(query.toLowerCase()))
//                        .collect(Collectors.toList());
//
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                ItemPagerAdapter itemPagerAdapter = new ItemPagerAdapter(fragmentManager, getLifecycle());
//                viewPager2.setAdapter(itemPagerAdapter);
//                progressBar.setVisibility(View.GONE);
                // Constants.GARMENTS= restore;
                return false;
            }


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
                progressBar.setVisibility(View.VISIBLE);
                Constants.GARMENTS = garments.stream()
                        .filter(g -> g.getBrand().toLowerCase().contains(newText.toLowerCase()))
                        .collect(Collectors.toList());

//                FragmentManager fragmentManager = getSupportFragmentManager();
//                ItemPagerAdapter itemPagerAdapter = new ItemPagerAdapter(fragmentManager, getLifecycle());
//                viewPager2.setAdapter(itemPagerAdapter);
//                progressBar.setVisibility(View.GONE);
                return false;
            }
        });

        return true;
    }

    static boolean state = true;

    void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(getApplicationContext(), view
        );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menushoes, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Menu menu = popup.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    menu.getItem(i).setChecked(false);
                }
                item.setChecked(true);
                if (menu.getItem(0).isChecked()) {

                    if (state) {

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        state = false;
                        return true;
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        state = true;
                        return true;
                    }


                } else {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(SavedShoesActivity.this, SignInOrUpActivity.class);
                    startActivity(intent);
                    finish();
                }


                return false;
            }
        });

    }
}