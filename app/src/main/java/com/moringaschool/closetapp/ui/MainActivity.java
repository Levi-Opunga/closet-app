package com.moringaschool.closetapp.ui;

import static android.content.ContentValues.TAG;
import static com.moringaschool.closetapp.Constants.SECRET_KEY;
import static java.security.AccessController.getContext;
import static java.util.Arrays.asList;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.moringaschool.closetapp.Encryption;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.adapters.ItemPagerAdapter;
import com.moringaschool.closetapp.fragments.AllItemsFragment;
import com.moringaschool.closetapp.interfaces.ReveryApi;
import com.moringaschool.closetapp.models.Response;
import com.moringaschool.closetapp.network.ReveryClient;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.io.Serializable;
import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    public static Context mainContext;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager2 viewPager2;


    long time;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaterialToolbar myToolbar = (MaterialToolbar) findViewById(R.id.materialToolbar);
        setSupportActionBar(myToolbar);
        mainContext = getApplicationContext();

        Log.d("onCreate", "creaaaaaaaaate");
        String[] tabs = {"All Items", "Tops", "Bottoms", "Shoes", "Dresses"};
        ButterKnife.bind(this);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                time = System.currentTimeMillis()/1000;
//
//                String derivedKey = Encryption.pbkdf2(SECRET_KEY,String.valueOf(time),128,32);
//                Log.d("thekeyisatCreate", derivedKey);
//
//                ReveryApi reveryApi = ReveryClient.getClient();
//                Call<Response> call = reveryApi.getAllGarments(derivedKey, String.valueOf(time));
//                call.enqueue(new Callback<Response>() {
//                    @Override
//                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                        if(response.isSuccessful()){
//                            getIntent().putExtra("Response",response.body());
//                            Log.d("Success", "Suuuuuccccceeessssss");
//                            FragmentManager fragmentManager = getSupportFragmentManager();
//                            AllItemsFragment fragment = new AllItemsFragment();
//                            fragmentManager.beginTransaction().replace(R.id.frame,fragment).commit();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Response> call, Throwable t) {
//                        Log.d("fail", "faillllllllllllllll");
//
//                    }
//                });
//
//            }
//        });
        Arrays.stream(tabs).forEach(s -> tabLayout.addTab(tabLayout.newTab().setText(s)));

        FragmentManager fragmentManager = getSupportFragmentManager();
        ItemPagerAdapter itemPagerAdapter = new ItemPagerAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(itemPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

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


    }

    @Override
    protected void onResume() {
        super.onResume();

//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                time = System.currentTimeMillis()/1000;
//
//                String derivedKey = Encryption.pbkdf2(SECRET_KEY,String.valueOf(time),128,32);
//
//                Log.d("thekeyisatResume", derivedKey);
//
//                ReveryApi reveryApi = ReveryClient.getClient();
//                Call<Response> call = reveryApi.getAllGarments(derivedKey, String.valueOf(time));
//                call.enqueue(new Callback<Response>() {
//                    @Override
//                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                        if(response.isSuccessful()){
//
//                        getIntent().putExtra("Response",response.body());
//                        Log.d("Success", "Suuuuuccccceeessssss");
//                        FragmentManager fragmentManager = getSupportFragmentManager();
//                        AllItemsFragment fragment = new AllItemsFragment();
//                        fragmentManager.beginTransaction().replace(R.id.frame,fragment).commit();
//                       }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<Response> call, Throwable t) {
//                        Log.d("fail", "faillllllllllllllll");
//
//                    }
//                });
//
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }


    static boolean state = true;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.more:

                if (state) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    state = false;
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                state= true;
                }
              //  Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT).show();



                break;
            // action with ID action_settings was selected
            case R.id.search:
                Toast.makeText(this, "Search will be available soon", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return true;
    }


    void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();
    }
}