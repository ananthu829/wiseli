package uk.ac.tees.mad.w9501736.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.models.UserFriendsList;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.holder> {

    public AdapterInterface buttonListener;
    List<UserFriendsList> items;
    int checkItem;

    public UserListAdapter(List<UserFriendsList> items, AdapterInterface buttonListener) {
        this.items = items;
        this.buttonListener = buttonListener;
    }

    @NonNull
    @Override
    public UserListAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_list_user, parent, false);
        return new UserListAdapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        final UserFriendsList contact = items.get(position);
        holder.txt.setText(contact.getFullName());

        if (contact.isSelected()) {
            holder.checked.setVisibility(View.VISIBLE);
        } else {
            holder.checked.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (contact.isSelected()) {
                contact.setSelected(false);

                notifyItemChanged(checkItem);
                buttonListener.onItemClicked(contact.getFullName(), contact.getUserId());

            } else {
                items.get(checkItem).setSelected(false);
                contact.setSelected(true);
                notifyItemChanged(checkItem);
                checkItem = position;
                notifyItemChanged(checkItem);
                buttonListener.onItemClicked(holder.txt.getText().toString(), items.get(checkItem).getUserId());
            }

        });

    }


    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public static class holder extends RecyclerView.ViewHolder {

        TextView txt;
        ImageView checked;

        public holder(View view) {
            super(view);
            txt = view.findViewById(R.id.tvTitle);
            checked = view.findViewById(R.id.imgChecked);
        }
    }
}
