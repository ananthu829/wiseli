package com.promatas.wiseli.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.promatas.wiseli.CircleDetails;
import com.promatas.wiseli.R;
import com.promatas.wiseli.models.CircleInfo;

import java.util.ArrayList;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.holder> {


    private Context mContext;
    ArrayList<CircleInfo> items;



    public CircleAdapter(Context c, ArrayList<CircleInfo> items) {
        mContext = c;
        this.items=items;
     //   this.activity=act;
    }




    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.home__recycler_layout, parent, false);
        return new holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {


        final CircleInfo contact = items.get(position);

       applyBlink(holder.layout,  1000);


        // Set the data to the views here

        holder.setData(mContext,contact.getCaption());






    }

    public static void applyBlink(View view, long duration)
    {

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(duration); //You can manage the blinking time with this parameter
//        anim.setStartOffset(20);
//        anim.setRepeatMode(Animation.REVERSE);
//        anim.setRepeatCount(1);
        view.startAnimation(anim);
    }


    @Override
    public int getItemCount() {
        return items == null? 0: items.size();
    }

    public static class holder extends  RecyclerView.ViewHolder {

        TextView txtName;
        ImageView edit, del;
        CardView layout;


        public  holder(View grid)
        {
            super(grid);

            txtName= grid.findViewById(R.id.item);
            layout= grid.findViewById(R.id.cardView);
            edit=grid.findViewById(R.id.editBtn);
            del=grid.findViewById(R.id.deleteBtn);
               }




        public void setData(Context context,String caption) {

            txtName.setText(caption);
            layout.setOnClickListener(v -> {

                Intent i= new Intent(context, CircleDetails.class);
                i.putExtra("caption", caption);
                context.startActivity(i);

            });

        }
    }
}
