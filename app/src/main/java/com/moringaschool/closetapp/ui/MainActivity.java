package com.moringaschool.closetapp.ui;

import static com.moringaschool.closetapp.Constants.SECRET_KEY;
import static com.moringaschool.closetapp.fragments.AllItemsFragment.allContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.moringaschool.closetapp.Constants;
import com.moringaschool.closetapp.Encryption;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.ShareData;
import com.moringaschool.closetapp.adapters.ItemPagerAdapter;

import com.moringaschool.closetapp.fragments.GenderFilterFragment;
import com.moringaschool.closetapp.fragments.TopFragment;
import com.moringaschool.closetapp.interfaces.ReveryApi;
import com.moringaschool.closetapp.models.Garment;
import com.moringaschool.closetapp.models.Response;
import com.moringaschool.closetapp.network.ReveryClient;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    public static Context mainContext;
    //    @BindView(R.id.tabLayout)
    public static TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager2 viewPager2;
    @Nullable
    @BindView(R.id.mainprogress)
    ProgressBar progressBar;
    List<Garment> garments;
    List<Garment> restore;
    private long pressedTime;
    private FirebaseAuth mAuth;


    long time;
    private Response responses;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Constants.saved = false;
        tabLayout = this.findViewById(R.id.tabLayout);
        Log.d("onCreate", "creaaaaaaaaate");

        String[] tabs = {"All Items", "Tops", "Bottoms", "Shoes", "Dresses", "Saved"};
        int[] icons = new int[]{R.drawable.img_2, R.drawable.img, R.drawable.img_1, R.drawable.img_3};
        Arrays.stream(tabs).forEach(tab -> tabLayout.addTab(tabLayout.newTab().setText(tab)));

        for (int i = 0; i < icons.length; i++) {
            tabLayout.getTabAt(i + 1).setText("");
            tabLayout.getTabAt(i + 1).setIcon(icons[i]);

        }

        tabLayout.setTabIndicatorFullWidth(true);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (Constants.GENDER.equals("male") && tab.getPosition() == 4) {
                    tab.setText("saved");
                    viewPager2.setCurrentItem(tabLayout.getSelectedTabPosition() + 1);

                    return;
                }
                viewPager2.setCurrentItem(tabLayout.getSelectedTabPosition());
                tab.setText(tabs[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (Constants.GENDER.equals("male") && tab.getPosition() == 4) {
                    tab.setText("saved");
                    return;
                }
                if (tab.getPosition() != 0 && tab.getPosition() < 5)
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

        refresh();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //createFirebaseUserProfile(user,"levi");
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome to " + user.getDisplayName() + "'s closet");
                } else {

                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        Constants.saved = false;
        // refresh();
        mildRefresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
        Constants.saved = false;
        //refresh();
        mildRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        // applyFontToMenu(menu,getApplicationContext());
        ButterKnife.bind(this);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/designer.ttf");

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
            time = System.currentTimeMillis() / 1000;

            String derivedKey = Encryption.pbkdf2(SECRET_KEY, String.valueOf(time), 128, 32);
            Log.d("thekeyisatCreateMainAct", derivedKey);

            ReveryApi reveryApi = ReveryClient.getClient();
            Call<Response> call = reveryApi.getAllGarments(derivedKey, String.valueOf(time));
            call.enqueue(new Callback<Response>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.isSuccessful()) {
                        responses = response.body();
                        garments = responses.getGarments();
                        Constants.RESTORE = garments;
                        Constants.GARMENTS = garments;
                        mainContext = getApplicationContext();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        ItemPagerAdapter itemPagerAdapter = new ItemPagerAdapter(fragmentManager, getLifecycle());
                        viewPager2.setAdapter(itemPagerAdapter);
                        progressBar.setVisibility(View.GONE);

                        Log.d("Success", "Suuuuuccccceeessssss in main");

                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.d("fail", "faillllllllllllllll");

                }
            });
        }
    }

    void mildRefresh() {
        Constants.GARMENTS = Constants.RESTORE;
        FragmentManager fragmentManager = getSupportFragmentManager();
        ItemPagerAdapter itemPagerAdapter = new ItemPagerAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(itemPagerAdapter);
    }


    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Constants.GARMENTS = Constants.RESTORE;
            FragmentManager fragmentManager = getSupportFragmentManager();
            ItemPagerAdapter itemPagerAdapter = new ItemPagerAdapter(fragmentManager, getLifecycle());
            viewPager2.setAdapter(itemPagerAdapter);
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_LONG).show();
        }
        pressedTime = System.currentTimeMillis();
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
                    Intent intent = new Intent(MainActivity.this, SignInOrUpActivity.class);
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


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    public static void applyFontToMenu(Menu m, Context mContext) {
        for (int i = 0; i < m.size(); i++) {
            applyFontToMenuItem(m.getItem(i), mContext);
        }
    }

    public static void applyFontToMenuItem(MenuItem mi, Context mContext) {
        if (mi.hasSubMenu())
            for (int i = 0; i < mi.getSubMenu().size(); i++) {
                applyFontToMenuItem(mi.getSubMenu().getItem(i), mContext);
            }
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "font/designer.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font, mContext), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private void createFirebaseUserProfile(final FirebaseUser user, String name) {

        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(addProfileName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("creating user", Objects.requireNonNull(user.getDisplayName()));
                        } else {
                            Log.d("ffffaaaaaiiilll", Objects.requireNonNull(user.getDisplayName()));

                        }
                    }

                });
    }
}