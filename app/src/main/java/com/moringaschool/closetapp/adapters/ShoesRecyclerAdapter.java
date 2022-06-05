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
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoesRecyclerAdapter extends RecyclerView.Adapter<ShoesRecyclerAdapter.myHolders>  {
    ArrayList<String> list;
    Context context;

    public ShoesRecyclerAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ShoesRecyclerAdapter.myHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.display_item,parent,false);
        return new ShoesRecyclerAdapter.myHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoesRecyclerAdapter.myHolders holder, int position) {
        Picasso.get().load("https://revery-e-commerce-images.s3.us-east-2.amazonaws.com/"+list.get(position)).into(holder.itemImg);
        holder.text.setText(String.valueOf(position));
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
