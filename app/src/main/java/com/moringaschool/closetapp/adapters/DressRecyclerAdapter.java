package com.moringaschool.closetapp.adapters;

import static com.moringaschool.closetapp.fragments.AllItemsFragment.adapter;

import android.app.Notification;
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
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.closetapp.AppUser;
import com.moringaschool.closetapp.Constants;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.ShareData;
import com.moringaschool.closetapp.fragments.AllItemsFragment;
import com.moringaschool.closetapp.fragments.TopFragment;
import com.moringaschool.closetapp.models.Garment;
import com.moringaschool.closetapp.ui.MainActivity;
import com.moringaschool.closetapp.ui.OneItemActivity;
import com.moringaschool.closetapp.util.ItemTouchHelperAdapter;
import com.squareup.picasso.Picasso;

import java.security.AccessControlContext;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DressRecyclerAdapter extends RecyclerView.Adapter<DressRecyclerAdapter.myHolders> implements ItemTouchHelperAdapter {
    static List<Garment> list;
    static Context context;
    View view;
    ItemTouchHelper touchHelper;

    public DressRecyclerAdapter(List<Garment> list, Context context) {
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
        holder.card.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DressRecyclerAdapter.this.showPopupMenu(v, holder.getAdapterPosition(), holder.selected);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Garment fromGarment = list.get(fromPosition);
        list.remove(fromPosition);
        list.add(toPosition, fromGarment);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemSwiped(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void setTouchHelper(ItemTouchHelper touchHelper) {
        this.touchHelper = touchHelper;
    }

    class myHolders extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnTouchListener, GestureDetector.OnGestureListener {
        @BindView(R.id.itemImg)
        ImageView itemImg;
        @BindView(R.id.itemName)
        TextView text;
        @BindView(R.id.itemCard)
        CardView card;
        @BindView(R.id.selected)
        ImageView selected;
        GestureDetector gestureDetector;

        public myHolders(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
           // itemView.setOnCreateContextMenuListener(this);
            itemView.setOnTouchListener(this);
gestureDetector= new GestureDetector(itemView.getContext(),this);
            // card.setOnClickListener(ItemRecyclerAdapter.this::showPopupMenu);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null
            menu.add(0, v.getId(), 0, "Share Link");
            menu.add(0, v.getId(), 1, "");
            menu.add(0, v.getId(), 2, "More Details");
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
gestureDetector.onTouchEvent(event);
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
         touchHelper.startDrag(this);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
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
                    //Share
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, list.get(position).getImageUrls().getProductImage());
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    context.startActivity(shareIntent);
                    Toast.makeText(context, "shared", Toast.LENGTH_SHORT).show();
                } else if (menu.getItem(2).isChecked()) {
                    //MORE INFO
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

