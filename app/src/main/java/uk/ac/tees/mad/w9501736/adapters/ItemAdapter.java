package uk.ac.tees.mad.w9501736.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.models.Item;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.holder> {


    ArrayList<Item> items;
    public AdapterInterface buttonListener;
    public Boolean status;

    public ItemAdapter(ArrayList<Item> items, AdapterInterface buttonListener, Boolean status) {
        this.items = items;
        this.buttonListener = buttonListener;
        this.status = status;

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

        // Set the data to the views here

        holder.setData(contact.getName(), contact.getQty());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonListener.onDeleteCtaClicked(contact.getListitem_id());
            }
        });
        if (!status) {
            holder.edit.setEnabled(false);
            holder.ok.setEnabled(false);
            holder.delete.setEnabled(false);
        } else {
            holder.edit.setEnabled(true);
            holder.ok.setEnabled(true);
            holder.delete.setEnabled(true);
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.editItem.setVisibility(View.VISIBLE);
                holder.editQuantity.setVisibility(View.VISIBLE);
                holder.edit.setVisibility(View.GONE);
                holder.ok.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.GONE);
                holder.txtName.setVisibility(View.GONE);
                holder.txtQty.setVisibility(View.GONE);
                holder.editItem.setText(holder.txtName.getText().toString());
                holder.editQuantity.setText(holder.txtQty.getText().toString());

            }
        });

        holder.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.editItem.setVisibility(View.GONE);
                holder.editQuantity.setVisibility(View.GONE);
                holder.edit.setVisibility(View.VISIBLE);
                holder.ok.setVisibility(View.GONE);
                holder.delete.setVisibility(View.VISIBLE);
                holder.txtName.setVisibility(View.VISIBLE);
                holder.txtQty.setVisibility(View.VISIBLE);
                buttonListener.setEditableText(contact.getListitem_id(), contact.getName());
                holder.txtName.setText(holder.editItem.getText().toString());
                holder.txtQty.setText(holder.editQuantity.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public static class holder extends RecyclerView.ViewHolder {

        TextView txtName, txtQty;
        CardView layout;
        ImageView delete, edit, ok;
        EditText editItem, editQuantity;

        public holder(View grid) {
            super(grid);

            txtName = grid.findViewById(R.id.item);
            txtQty = grid.findViewById(R.id.qty);
            layout = grid.findViewById(R.id.cardView);
            delete = grid.findViewById(R.id.delete);
            edit = grid.findViewById(R.id.edit);
            ok = grid.findViewById(R.id.ok);
            editItem = grid.findViewById(R.id.edit_item);
            editQuantity = grid.findViewById(R.id.edit_quantity);
//            edit=grid.findViewById(R.id.editBtn);
        }


        public void setData(String name, String qty) {

            txtName.setText(name);
            txtQty.setText(qty);

        }
    }
}
