package uk.ac.tees.mad.w9501736.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    public ItemAdapter(ArrayList<Item> items, AdapterInterface buttonListener) {
        this.items = items;
        this.buttonListener = buttonListener;


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
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public static class holder extends RecyclerView.ViewHolder {

        TextView txtName, txtQty;
        CardView layout;
        ImageView delete;

        public holder(View grid) {
            super(grid);

            txtName = grid.findViewById(R.id.item);
            txtQty = grid.findViewById(R.id.qty);
            layout = grid.findViewById(R.id.cardView);
            delete = grid.findViewById(R.id.delete);
//            edit=grid.findViewById(R.id.editBtn);
        }


        public void setData(String name, String qty) {

            txtName.setText(name);
            txtQty.setText(qty);

        }
    }
}
