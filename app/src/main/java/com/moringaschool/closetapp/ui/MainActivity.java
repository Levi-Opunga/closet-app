package com.moringaschool.closetapp.ui;

import static android.content.ContentValues.TAG;
import static com.moringaschool.closetapp.Constants.SECRET_KEY;
import static java.util.Arrays.asList;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
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

Log.d("onCreate","creaaaaaaaaate");
        String[] tabs = {"All Items","Tops", "Bottoms", "Shoes"};
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
}