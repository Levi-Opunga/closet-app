package com.moringaschool.closetapp.ui;

import static com.moringaschool.closetapp.fragments.AllItemsFragment.garments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.closetapp.Constants;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.adapters.SavedItemPagerAdapter;
import com.moringaschool.closetapp.adapters.SavedShoesRecyclerAdapter;
import com.moringaschool.closetapp.models.Garment;
import com.moringaschool.closetapp.models.Shoe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedShoesActivity extends AppCompatActivity {
@BindView(R.id.recyclerviewSavedShoe)
    RecyclerView recyclerView;
    ArrayList<Shoe> savedShoes = new ArrayList<>();
    private DatabaseReference reference;
    SavedShoesRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_shoes);
        ButterKnife.bind(this);
        loadShoes();

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

        }
        recyclerView.setLayoutManager(gridLayoutManager);
 adapter = new SavedShoesRecyclerAdapter(savedShoes,getApplicationContext());
        recyclerView.setAdapter(adapter);

    }


    void loadShoes() {
        for (int i = 0; i <= 0; i++) {
            reference = FirebaseDatabase.getInstance().getReference("Shoes").child(Constants.uid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    savedShoes.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Shoe shoe =dataSnapshot.getValue(Shoe.class);
                        savedShoes.add(shoe);
                        adapter.notifyDataSetChanged();
                    }
                    ProgressBar progressBar = findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}