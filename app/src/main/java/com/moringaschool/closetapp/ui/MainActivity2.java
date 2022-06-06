package com.moringaschool.closetapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;

import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.adapters.ItemRecyclerAdapter;
import com.moringaschool.closetapp.fragments.AllItemsFragment;
import com.moringaschool.closetapp.models.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);


    }


}