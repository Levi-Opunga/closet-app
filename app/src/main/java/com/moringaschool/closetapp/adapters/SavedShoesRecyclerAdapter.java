package com.moringaschool.closetapp.adapters;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.closetapp.Constants;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.fragments.TopFragment;
import com.moringaschool.closetapp.models.Shoe;
import com.moringaschool.closetapp.ui.OneShoeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedShoesRecyclerAdapter extends RecyclerView.Adapter<SavedShoesRecyclerAdapter.myHolders> {
    static ArrayList<Shoe> list;
    static Context context;

    public SavedShoesRecyclerAdapter(ArrayList<Shoe> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public SavedShoesRecyclerAdapter.myHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.display_item, parent, false);
        return new SavedShoesRecyclerAdapter.myHolders(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SavedShoesRecyclerAdapter.myHolders holder, int position) {
        Picasso.get().load(list.get(position).getUrl()).into(holder.itemImg);
        holder.text.setText(String.valueOf(position + 1));
        holder.card.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedShoesRecyclerAdapter.showPopupMenu(v, holder.getAdapterPosition(), holder.selected);
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
            ButterKnife.bind(this, itemView);

        }
    }

    static void showPopupMenu(View view, int position, ImageView itemImg) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menushoe, popup.getMenu());

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
                    sendIntent.putExtra(Intent.EXTRA_TEXT, list.get(position).getUrl());
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(shareIntent);
                    Toast.makeText(context, "shared", Toast.LENGTH_SHORT).show();
                } else if (menu.getItem(2).isChecked()) {
                    Intent intent = new Intent(context, OneShoeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.putExtra("shoes", list.get(position).getUrl());
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "saving", Toast.LENGTH_SHORT).show();

                    save(position, itemImg);
                }


                return false;
            }
        });

    }

    public static void save(int position, ImageView itemImg) {
        if (context != null) {
            //SELECT
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Shoes").child(Constants.uid);
            DatabaseReference pushRef = reference.push();
            String pushId = pushRef.getKey();
            Shoe newShoe = list.get(position);
            newShoe.setPushId(pushId);
            pushRef.setValue(newShoe).addOnCompleteListener(new OnCompleteListener() {

                @Override
                public void onComplete(@NonNull Task task) {

                    if (task.isSuccessful()) {
                        itemImg.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Succsessfully Saved", Toast.LENGTH_SHORT).show();
                        //  ShareData.OutFit.dress = list.get(position).getId();
                    } else {
                        itemImg.setVisibility(View.INVISIBLE);
                        Toast.makeText(context, "Unable Save Try Again", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
