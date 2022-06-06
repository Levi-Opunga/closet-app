package com.moringaschool.closetapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.ShareData;
import com.moringaschool.closetapp.fragments.AllItemsFragment;
import com.moringaschool.closetapp.fragments.TopFragment;
import com.moringaschool.closetapp.models.Garment;
import com.moringaschool.closetapp.ui.OneItemActivity;
import com.moringaschool.closetapp.ui.OneShoeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoesRecyclerAdapter extends RecyclerView.Adapter<ShoesRecyclerAdapter.myHolders>  {
    static ArrayList<String> list;
    static Context context;
    static ArrayList<String> models;
    public ShoesRecyclerAdapter(ArrayList<String> list, Context context,ArrayList<String> models) {
        this.list = list;
        this.context = context;
        this.models = models;
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
        holder.text.setText(String.valueOf(position+1));
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
ShoesRecyclerAdapter.showPopupMenu(v,holder.getAdapterPosition(),holder.selected);
            }
        });
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
        @BindView(R.id.itemCard)
        CardView card;
        @BindView(R.id.selected)
        ImageView selected;
        public myHolders(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

  static   void showPopupMenu(View view, int position, ImageView itemImg) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());

            popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Menu menu = popup.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    menu.getItem(i).setChecked(false);
                }
                item.setChecked(true);
                if (menu.getItem(1).isChecked()) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "https://revery-e-commerce-images.s3.us-east-2.amazonaws.com/"+list.get(position));
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    context.startActivity(shareIntent);
                    Toast.makeText(context, "shared", Toast.LENGTH_SHORT).show();
                } else if (menu.getItem(2).isChecked()) {
                    Intent intent = new Intent(context, OneShoeActivity.class);
                    intent.putExtra("shoes", "https://revery-e-commerce-images.s3.us-east-2.amazonaws.com/"+list.get(position));
                    context.startActivity(intent);
                } else {
                    itemImg.setVisibility(View.VISIBLE);
                    if (context == TopFragment.topContext) {

                        ShareData.OutFit.shoe = models.get(position);
                        Toast.makeText(context,"bottom" + ShareData.OutFit.shoe, Toast.LENGTH_SHORT).show();
                    }

                }


                return false;
            }
        });

    }

}
