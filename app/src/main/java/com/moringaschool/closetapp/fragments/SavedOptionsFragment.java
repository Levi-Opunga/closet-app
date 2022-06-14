package com.moringaschool.closetapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.ui.SavedClothesActivity;
import com.moringaschool.closetapp.ui.SavedShoesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedOptionsFragment extends Fragment {
@BindView(R.id.clothes)
    Button clothes;
    @BindView(R.id.shoes)
    Button shoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        clothes.setOnClickListener(v -> {
           Intent intent = new Intent(getContext(), SavedClothesActivity.class);
           startActivity(intent);
        });
        shoes.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SavedShoesActivity.class);
            startActivity(intent);
        });

    }
}