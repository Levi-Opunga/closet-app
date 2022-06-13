package com.moringaschool.closetapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.moringaschool.closetapp.fragments.SignInFragment;
import com.moringaschool.closetapp.fragments.SignUpFragment;


import java.util.ArrayList;
import java.util.Collections;

public class SignInPagerAdapater  extends FragmentStateAdapter {


    public SignInPagerAdapater(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public SignInPagerAdapater(@NonNull Fragment fragment) {
        super(fragment);
    }

    public SignInPagerAdapater(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment[] array = {new SignInFragment(),new SignUpFragment()};
        return array[position];
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
