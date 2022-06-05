package com.moringaschool.closetapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.models.Garment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DressRecyclerAdapter extends RecyclerView.Adapter<DressRecyclerAdapter.myHolders> {
    List<Garment> list;
    Context context;

    public DressRecyclerAdapter(List<Garment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.display_item,parent,false);
        return new myHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolders holder, int position) {
        Picasso.get().load(list.get(position).getImageUrls().getProductImage()).into(holder.itemImg);
        holder.text.setText(list.get(position).getBrand());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myHolders extends RecyclerView.ViewHolder {
        @BindView(R.id.itemImg)
        ImageView itemImg;
        @BindView(R.id.itemName)
        TextView text;
        public myHolders(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
