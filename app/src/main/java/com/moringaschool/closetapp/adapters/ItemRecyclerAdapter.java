package com.moringaschool.closetapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.myHolders> {

    @NonNull
    @Override
    public myHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull myHolders holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class myHolders extends RecyclerView.ViewHolder {

        public myHolders(@NonNull View itemView) {
            super(itemView);
        }
    }
}
