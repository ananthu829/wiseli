package uk.ac.tees.mad.w9501736.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.models.AvailableUserList;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.holder> {

    public AdapterInterface buttonListener;
    ArrayList<AvailableUserList> items;
    int checkItem;

    public UserListAdapter(ArrayList<AvailableUserList> items, AdapterInterface buttonListener) {
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
        final AvailableUserList contact = items.get(position);
        holder.txt.setText(contact.getCaption());

        if (contact.getSelected()) {
            holder.checked.setVisibility(View.VISIBLE);
        } else {
            holder.checked.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (contact.getSelected()) {
                items.get(position).setIsSelected(false);

                notifyItemChanged(checkItem);
                buttonListener.onItemClicked("", 0);

            } else {
                items.get(checkItem).setIsSelected(false);
                items.get(position).setIsSelected(true);
                notifyItemChanged(checkItem);
                checkItem = position;
                notifyItemChanged(checkItem);
                buttonListener.onItemClicked(holder.txt.getText().toString(), 0);
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
