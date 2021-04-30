package uk.ac.tees.mad.w9501736.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.common.CommonEditableTextView;
import uk.ac.tees.mad.w9501736.models.UserFriendsList;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.holder> {


    public AdapterInterface buttonListener;
    ArrayList<UserFriendsList> items;


    public FriendsAdapter(ArrayList<UserFriendsList> items, AdapterInterface buttonListener) {
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
        final UserFriendsList contact = items.get(position);
        holder.commonEditableTextView.setEditableText(contact.getFullName());
        holder.commonEditableTextView.setOnDeleteClickListener(buttonListener, contact.getUserId());
        holder.commonEditableTextView.setOnEditClickListener(buttonListener, contact.getUserId());

        holder.commonEditableTextView.setOnClickListener(v -> {
            if (holder.commonEditableTextView.getTextTextVisibility()) {
                buttonListener.onItemClicked(holder.commonEditableTextView.getEditableText(), contact.getUserId());
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
