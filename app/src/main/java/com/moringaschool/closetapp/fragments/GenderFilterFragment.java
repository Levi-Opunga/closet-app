package com.moringaschool.closetapp.fragments;

import static com.moringaschool.closetapp.ui.MainActivity.tabLayout;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moringaschool.closetapp.Constants;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.adapters.BottomsRecyclerAdapter;
import com.moringaschool.closetapp.ui.MainActivity;

import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenderFilterFragment extends DialogFragment implements View.OnClickListener {
    @BindView(R.id.Female)
    Button femaleButton;
    @BindView(R.id.Male)
    Button maleButton;
    @BindView(R.id.AllFits)
    Button All;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gender_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        femaleButton.setOnClickListener(this);
        maleButton.setOnClickListener(this);
        All.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v == femaleButton) {
            Constants.GENDER = "female";

            if (tabLayout.getTabCount() < 6) {
                tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.img_3), 4);
            }
          //  tabLayout.setTabMode(tabLayout.MODE_AUTO);
            Constants.GARMENTS = Constants.RESTORE;
            ShoeFragment.gender = Constants.GENDER;
            Constants.GARMENTS = Constants.GARMENTS.stream()
                    .filter(g -> g.getGender().equals(Constants.GENDER))
                    .collect(Collectors.toList());
            AllItemsFragment.externalRefreshLayout();
            if (BottomFragment.recyclerViewBottom != null) {
                BottomFragment.externalRefreshLayout();
            }
            if (TopFragment.recyclerViewTop != null) {
                TopFragment.externalRefreshLayout();
            }
            if (ShoeFragment.recyclerView != null) {
                ShoeFragment.method(Constants.GENDER);
            }
            if(DressFragment.recyclerView!= null){
                DressFragment.externalRefreshLayout();
            }

            dismiss();
        } else if (v == maleButton) {
            Constants.GENDER = "male";
            if (tabLayout.getTabCount() == 6) {
                tabLayout.removeTabAt(4);
            }
           // tabLayout.setTabMode(tabLayout.MODE_AUTO);
            Constants.GARMENTS = Constants.RESTORE;
            Constants.GARMENTS = Constants.GARMENTS.stream()
                    .filter(g -> g.getGender().equals(Constants.GENDER))
                    .collect(Collectors.toList());
            ShoeFragment.gender = Constants.GENDER;
            AllItemsFragment.externalRefreshLayout();
            if (BottomFragment.recyclerViewBottom != null) {
                BottomFragment.externalRefreshLayout();
            }
            if (TopFragment.recyclerViewTop != null) {
                TopFragment.externalRefreshLayout();
            }
            if (ShoeFragment.recyclerView != null) {
                ShoeFragment.method(Constants.GENDER);
            }
//            ShoeFragment shoe = new ShoeFragment();
//            shoe.refresh();
            dismiss();
        } else {
            Constants.GENDER = "none";
            Constants.GARMENTS = Constants.RESTORE;
            ShoeFragment.gender = Constants.GENDER;
            AllItemsFragment.externalRefreshLayout();
            if (BottomFragment.recyclerViewBottom != null) {
                BottomFragment.externalRefreshLayout();
            }
            if (TopFragment.recyclerViewTop != null) {
                TopFragment.externalRefreshLayout();
            }
            if (ShoeFragment.recyclerView != null) {
                ShoeFragment.method(Constants.GENDER);
            }
            if(DressFragment.recyclerView!= null){
                DressFragment.externalRefreshLayout();
            }
//            ShoeFragment shoe = new ShoeFragment();
//            shoe.refresh();
            dismiss();
        }

    }
}