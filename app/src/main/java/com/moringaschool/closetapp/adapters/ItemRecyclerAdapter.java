package com.moringaschool.closetapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.myHolders> {
    static List<Garment> list;
    static Context context;
    View view;

    public ItemRecyclerAdapter(List<Garment> list, Context context) {
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

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OneItemActivity.class);
                intent.putExtra("item", list.get(position));
                context.startActivity(intent);
            }
        });
        //}
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


    public static void save(int position, ImageView itemImg, List<Garment> list) {
        if (context == TopFragment.topContext) {
            //SELECT
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.SAVED_CLOTHES).child(Constants.uid);
            DatabaseReference pushRef = reference.push();
            String pushId = pushRef.getKey();
            list.get(position).setPushId(pushId);

            pushRef.setValue(list.get(position)).addOnCompleteListener(new OnCompleteListener() {

                @Override
                public void onComplete(@NonNull Task task) {

                    if (task.isSuccessful()) {
                        itemImg.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Succsessfully Saved", Toast.LENGTH_SHORT).show();
                        ShareData.OutFit.dress = list.get(position).getId();
                    } else {
                        itemImg.setVisibility(View.INVISIBLE);
                        Toast.makeText(context, "Unable Save Try Again", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
