package uk.ac.tees.mad.w9501736.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.common.CommonEditableTextView;
import uk.ac.tees.mad.w9501736.models.User;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.holder> {


    public AdapterInterface buttonListener;
    public Boolean showDeleteBtn;
    ArrayList<User> items;

    public UserAdapter(ArrayList<User> items, Boolean showDeleteBtn, AdapterInterface buttonListener) {
        this.items = items;
        this.buttonListener = buttonListener;
        this.showDeleteBtn = showDeleteBtn;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.circle_list_layout, parent, false);
        return new holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        final User contact = items.get(position);
        holder.commonEditableTextView.setEditableText(contact.getCaption());
        holder.commonEditableTextView.setOnDeleteClickListener(buttonListener,"");
        holder.commonEditableTextView.setOnClickListener(v -> {
            if (holder.commonEditableTextView.getTextTextVisibility()) {
                buttonListener.onItemClicked(holder.commonEditableTextView.getEditableText(), 0);
            }
        });
        if (!showDeleteBtn) {
            holder.commonEditableTextView.hideImageDeleteBtn(true);
            holder.commonEditableTextView.hideImageEditBtn(true);
        }
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
