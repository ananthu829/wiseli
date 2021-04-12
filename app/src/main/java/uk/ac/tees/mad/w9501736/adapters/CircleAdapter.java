package uk.ac.tees.mad.w9501736.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.camerash.toggleedittextview.ToggleEditButton;
import com.camerash.toggleedittextview.ToggleEditTextView;
import com.promatas.wiseli.R;

import java.util.ArrayList;

import uk.ac.tees.mad.w9501736.models.CircleInfo;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.holder> {


    public AdapterInterface buttonListener;
    ArrayList<CircleInfo> items;


    public CircleAdapter(ArrayList<CircleInfo> items, AdapterInterface buttonListener) {
        this.items = items;
        this.buttonListener = buttonListener;
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
        View view = layoutInflater.inflate(R.layout.home__recycler_layout, parent, false);
        return new holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {


        final CircleInfo contact = items.get(position);

        applyBlink(holder.layout, 1000);


        // Set the data to the views here

        holder.setData(contact.getCaption());
        holder.txtName.setEditTextEnabled(false);
        Toast.makeText(holder.itemView.getContext(), holder.txtName.getEditTextEnabled() + " : onBindViewHolder State", Toast.LENGTH_SHORT).show();
        holder.layout.setOnClickListener(v -> {
            buttonListener.buttonPressed(contact.getCaption());
        });
        holder.editBtn.bind(holder.txtName);
        holder.editBtn.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), holder.txtName.getEditTextEnabled() + " : State", Toast.LENGTH_SHORT).show();
            if (holder.txtName.getEditTextEnabled()) {
                holder.layout.setClickable(false);
                holder.txtName.getText();
                Toast.makeText(v.getContext(), holder.txtName.getText() + " : click", Toast.LENGTH_SHORT).show();
            } else {
                holder.layout.setClickable(true);
            }
        });


    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public static class holder extends RecyclerView.ViewHolder {

        ToggleEditTextView txtName;
        ImageView edit, del;
        ToggleEditButton editBtn;
        CardView layout;
        View view;


        public holder(View view) {
            super(view);
            this.view = view;

            txtName = view.findViewById(R.id.item);
            layout = view.findViewById(R.id.cardView);
            editBtn = view.findViewById(R.id.editBtn);
            del = view.findViewById(R.id.deleteBtn);
        }


        public void setData(String caption) {

            txtName.setText(caption);

        }
    }
}
