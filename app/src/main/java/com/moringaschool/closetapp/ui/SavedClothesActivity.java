package com.moringaschool.closetapp.ui;

import static com.moringaschool.closetapp.Constants.SECRET_KEY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.closetapp.Constants;
import com.moringaschool.closetapp.Encryption;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.adapters.ItemPagerAdapter;
import com.moringaschool.closetapp.adapters.SavedItemPagerAdapter;
import com.moringaschool.closetapp.fragments.GenderFilterFragment;
import com.moringaschool.closetapp.interfaces.ReveryApi;
import com.moringaschool.closetapp.models.Garment;
import com.moringaschool.closetapp.models.Response;
import com.moringaschool.closetapp.network.ReveryClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class SavedClothesActivity extends AppCompatActivity {

    public static Context mainContext;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager2 viewPager2;
    @Nullable
    @BindView(R.id.mainprogress)
    ProgressBar progressBar;

    List<Garment> garments = new ArrayList<Garment>();

    private long pressedTime;
    DatabaseReference reference;

    long time;
    private Response responses;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_clothes);
        ButterKnife.bind(this);
        Constants.saved = true;
        String[] tabs = {"All Items", "Tops", "Bottoms", "Dresses"};
        int[] icons = new int[]{R.drawable.img_2, R.drawable.img, R.drawable.img_3};
        Arrays.stream(tabs).forEach(tab -> tabLayout.addTab(tabLayout.newTab().setText(tab)));

        for (int i = 0; i < icons.length; i++) {
            tabLayout.getTabAt(i + 1).setText("");
            tabLayout.getTabAt(i + 1).setIcon(icons[i]);

        }

        tabLayout.setTabIndicatorFullWidth(true);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tabLayout.getSelectedTabPosition());
                tab.setText(tabs[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                if (tab.getPosition() != 0)
                    tab.setText("");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        viewPager2.setUserInputEnabled(false);

        refresh();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        ButterKnife.bind(this);
        Constants.saved = true;

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Enter Filter Phrase");
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
                Constants.GARMENTS = garments.stream()
                        .filter(g -> g.getBrand().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());
                FragmentManager fragmentManager = getSupportFragmentManager();
                ItemPagerAdapter itemPagerAdapter = new ItemPagerAdapter(fragmentManager, getLifecycle());
                viewPager2.setAdapter(itemPagerAdapter);
                progressBar.setVisibility(View.GONE);
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
                FragmentManager fragmentManager = getSupportFragmentManager();
                ItemPagerAdapter itemPagerAdapter = new ItemPagerAdapter(fragmentManager, getLifecycle());
                viewPager2.setAdapter(itemPagerAdapter);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });

        return true;
    }


    static boolean state = true;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    void refresh() {
        progressBar.setVisibility(View.VISIBLE);


        for (int i = 0; i <= 0; i++) {
            reference = FirebaseDatabase.getInstance().getReference(Constants.SAVED_CLOTHES).child(Constants.uid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    garments.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Garment garment = dataSnapshot.getValue(Garment.class);
                        garments.add(garment);
                    }
                    Constants.RESTORE_SAVED = garments;
                    Constants.GARMENTS = garments;
                    mainContext = getApplicationContext();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    SavedItemPagerAdapter saveditemPagerAdapter = new SavedItemPagerAdapter(fragmentManager, getLifecycle());
                    viewPager2.setAdapter(saveditemPagerAdapter);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }


    void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(getApplicationContext(), view
        );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menumain, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Menu menu = popup.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    menu.getItem(i).setChecked(false);
                }
                item.setChecked(true);
                if (menu.getItem(1).isChecked()) {
                    GenderFilterFragment fragment = new GenderFilterFragment();
                    fragment.show(getSupportFragmentManager(), "gender");

                } else if (menu.getItem(2).isChecked()) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(SavedClothesActivity.this, SignInOrUpActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (state) {

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        state = false;
                        return true;
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        state = true;
                        return true;
                    }
                }


                return false;
            }
        });

    }


}