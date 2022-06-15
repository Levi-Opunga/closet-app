package com.moringaschool.closetapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.common.collect.Table;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.adapters.SignInPagerAdapater;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInOrUpActivity extends AppCompatActivity {
    FirebaseAuth auth;

    @BindView(R.id.tabLayout2)
    TabLayout tabLayout;
    @BindView(R.id.viewPagerSignedIn)
    ViewPager2 viewPager;
    FirebaseAuth.AuthStateListener AuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_or_up);
        ButterKnife.bind(this);


        auth = FirebaseAuth.getInstance();

        AuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Intent intent = new Intent(SignInOrUpActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        };


        tabLayout.addTab(tabLayout.newTab().setText("Sign In"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
        viewPager.setAdapter(new SignInPagerAdapater(getSupportFragmentManager(), getLifecycle()));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(AuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (AuthStateListener != null) {
            auth.removeAuthStateListener(AuthStateListener);
        }
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