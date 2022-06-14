package com.moringaschool.closetapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moringaschool.closetapp.Constants;
import com.moringaschool.closetapp.R;

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
        ButterKnife.bind(this,view);
        femaleButton.setOnClickListener(this);
        maleButton.setOnClickListener(this);
        All.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == femaleButton) {
      Constants.GENDER = "female";
      ShoeFragment.gender = Constants.GENDER;
//      ShoeFragment shoe = new ShoeFragment();
//      shoe.refresh();
            dismiss();
        } else if (v== maleButton) {
Constants.GENDER ="male";
            ShoeFragment.gender = Constants.GENDER;
//            ShoeFragment shoe = new ShoeFragment();
//            shoe.refresh();
            dismiss();
        } else {
Constants.GENDER = "none";
            ShoeFragment.gender = Constants.GENDER;
//            ShoeFragment shoe = new ShoeFragment();
//            shoe.refresh();
            dismiss();
        }

    }
}