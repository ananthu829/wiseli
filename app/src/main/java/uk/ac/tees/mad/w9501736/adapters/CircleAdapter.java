package uk.ac.tees.mad.w9501736.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.common.CommonEditableTextView;
import uk.ac.tees.mad.w9501736.models.CircleInfo;
import uk.ac.tees.mad.w9501736.models.CircleModel;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.holder> {


    public AdapterInterface buttonListener;
    ArrayList<CircleModel.CircleData> items;


    public CircleAdapter(ArrayList<CircleModel.CircleData> items, AdapterInterface buttonListener) {
        this.items = items;
        this.buttonListener = buttonListener;
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
        final CircleModel.CircleData contact = items.get(position);
        holder.commonEditableTextView.setEditableText(contact.getCircle_name());
        holder.commonEditableTextView.setOnDeleteClickListener(buttonListener,contact.getCircle_id());
        holder.commonEditableTextView.setOnEditClickListener(buttonListener,contact.getCircle_id());

        holder.commonEditableTextView.setOnClickListener(v -> {
            if (holder.commonEditableTextView.getTextTextVisibility()) {
                buttonListener.onItemClicked(holder.commonEditableTextView.getEditableText());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public static class holder extends RecyclerView.ViewHolder {

        CommonEditableTextView commonEditableTextView;

        public holder(View view) {
            super(view);
            commonEditableTextView = view.findViewById(R.id.commonEditableTextView);
        }
    }
}
