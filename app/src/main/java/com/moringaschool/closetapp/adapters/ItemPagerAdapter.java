package com.moringaschool.closetapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.moringaschool.closetapp.fragments.AllItemsFragment;
import com.moringaschool.closetapp.fragments.BottomFragment;
import com.moringaschool.closetapp.fragments.ShoeFragment;
import com.moringaschool.closetapp.fragments.TopFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    ArrayList<Fragment> list = new ArrayList<Fragment>();
    Collections.addAll(list,new AllItemsFragment(),new TopFragment(),new BottomFragment(),new ShoeFragment());
        return list.get(position);
    }

    @Override
    public int getItemCount() {

        return 4;
    }
}
