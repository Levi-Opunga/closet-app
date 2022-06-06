package com.moringaschool.closetapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.models.Garment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OneItemActivity extends AppCompatActivity {
    @BindView(R.id.brand)
    TextView brand;
    @BindView(R.id.category)
    TextView category;
    @BindView(R.id.sub_category)
    TextView sub_category;
    @BindView(R.id.itemOneImage)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_item);
        MaterialToolbar myToolbar = (MaterialToolbar) findViewById(R.id.materialToolbar);
        setSupportActionBar(myToolbar);
        ButterKnife.bind(this);
        Garment garment = (Garment) getIntent().getSerializableExtra("item");
        Picasso.get().load(garment.getImageUrls().getProductImage()).into(image);
        brand.setText(garment.getBrand().toUpperCase());
        category.setText(garment.getTryon().getCategory().toUpperCase());
        if (garment.getTryon().getBottoms_sub_category() == null) {
            sub_category.setVisibility(View.GONE);
            TextView sub = findViewById(R.id.subH);
            sub.setVisibility(View.INVISIBLE);
        } else {
            sub_category.setText(garment.getTryon().getBottoms_sub_category().toUpperCase());
        }
        registerForContextMenu(image);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // you can set menu header with title icon etc
        menu.setHeaderTitle("Choose an option");
        // add menu items
        menu.add(0, v.getId(), 0, "Share");

    }
}