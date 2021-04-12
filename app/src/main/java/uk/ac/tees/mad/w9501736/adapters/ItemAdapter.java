package uk.ac.tees.mad.w9501736.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.models.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.holder> {


    ArrayList<Item> items;
    private Context mContext;


    public ItemAdapter(Context c, ArrayList<Item> items) {
        mContext = c;
        this.items = items;

    }

    public static void applyBlink(View view, long duration) {

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(duration); //You can manage the blinking time with this parameter
//        anim.setStartOffset(20);
//        anim.setRepeatMode(Animation.REVERSE);
//        anim.setRepeatCount(1);
        view.startAnimation(anim);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.list_layout, parent, false);
        return new holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {


        final Item contact = items.get(position);

        applyBlink(holder.layout, 1000);


        // Set the data to the views here

        holder.setData(mContext, contact.getName(), contact.getQty());


    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public static class holder extends RecyclerView.ViewHolder {

        TextView txtName, txtQty;
        CardView layout;


        public holder(View grid) {
            super(grid);

            txtName = grid.findViewById(R.id.item);
            txtQty = grid.findViewById(R.id.qty);
            layout = grid.findViewById(R.id.cardView);
//            edit=grid.findViewById(R.id.editBtn);
        }


        public void setData(Context context, String name, String qty) {

            txtName.setText(name);
            txtQty.setText(qty);

        }
    }
}
