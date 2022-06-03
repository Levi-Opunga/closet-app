package com.moringaschool.closetapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.moringaschool.closetapp.fragments.BottomFragment;
import com.moringaschool.closetapp.fragments.ShoeFragment;
import com.moringaschool.closetapp.fragments.TopFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemPagerAdapter extends FragmentStateAdapter {


//    public ItemPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
//        super(fragmentActivity);
//    }

//    public ItemPagerAdapter(@NonNull Fragment fragment) {
//        super(fragment);
//    }

    public ItemPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
     if(position == 1){
         return new TopFragment();
     }else if(position ==2){
         return new BottomFragment();
        }
        return new ShoeFragment();
    }

    @Override
    public int getItemCount() {

        return 3;
    }
}
