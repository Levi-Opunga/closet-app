package com.moringaschool.closetapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.closetapp.Constants;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.ShareData;
import com.moringaschool.closetapp.fragments.AllItemsFragment;
import com.moringaschool.closetapp.fragments.TopFragment;
import com.moringaschool.closetapp.models.Garment;
import com.moringaschool.closetapp.ui.MainActivity;
import com.moringaschool.closetapp.ui.OneItemActivity;
import com.squareup.picasso.Picasso;

import java.security.AccessControlContext;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomsRecyclerAdapter extends RecyclerView.Adapter<BottomsRecyclerAdapter.myHolders> {
    List<Garment> list;
    Context context;
    View view;

    public BottomsRecyclerAdapter(List<Garment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.display_item, parent, false);
        myHolders holder = new myHolders(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull myHolders holder, int position) {
        Picasso.get().load(list.get(position).getImageUrls().getProductImage()).into(holder.itemImg);
        holder.text.setText(list.get(position).getBrand());
        // holder.card.setOnClickListener(ItemRecyclerAdapter.showPopupMenu(view,list.get(position).getImageUrls().getProductImage()));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomsRecyclerAdapter.this.showPopupMenu(v, holder.getAdapterPosition(), holder.selected);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myHolders extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
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
            ButterKnife.bind(this, itemView);
            view.setOnCreateContextMenuListener(this);

            // card.setOnClickListener(ItemRecyclerAdapter.this::showPopupMenu);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null
            menu.add(0, v.getId(), 0, "Share Link");
            menu.add(0, v.getId(), 1, "");
            menu.add(0, v.getId(), 2, "More Details");
        }

    }


    void showPopupMenu(View view, int position, ImageView itemImg) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        if (Constants.saved) {
            inflater.inflate(R.menu.popup_menusaved, popup.getMenu());
        } else {
            inflater.inflate(R.menu.popup_menu, popup.getMenu());
        }
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
                    sendIntent.putExtra(Intent.EXTRA_TEXT, list.get(position).getImageUrls().getProductImage());
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    context.startActivity(shareIntent);
                    Toast.makeText(context, "shared", Toast.LENGTH_SHORT).show();
                } else if (menu.getItem(2).isChecked()) {
                    Intent intent = new Intent(context, OneItemActivity.class);
                    intent.putExtra("item", list.get(position));
                    context.startActivity(intent);
                } else {
                    ItemRecyclerAdapter.save(position, itemImg, list);
                }


                return false;
            }
        });

    }
}
