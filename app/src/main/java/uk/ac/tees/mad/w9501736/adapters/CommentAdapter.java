package uk.ac.tees.mad.w9501736.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.models.Comment;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Holder> {

    ArrayList<Comment> items;

    public CommentAdapter(ArrayList<Comment> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.content_comment_view, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Comment comment = items.get(position);

        // Set the data to the views here
        if (comment != null) {
            if (comment.getImgUrl() != null) {
                Glide.with(holder.itemView.getContext())
                        .load(comment.getImgUrl())
                        .centerCrop()
                        .placeholder(R.drawable.image1)
                        .into(holder.ivUser);
            }
            if (comment.getUserName() != null) {
                holder.tvName.setText(comment.getUserName());
            }
            if (comment.getComment() != null) {
                holder.tvComment.setText(comment.getComment());
            }
        }

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        AppCompatTextView tvComment, tvName;
        AppCompatImageView ivUser;

        public Holder(View grid) {
            super(grid);

            tvName = grid.findViewById(R.id.tvName);
            tvComment = grid.findViewById(R.id.tvComment);
            ivUser = grid.findViewById(R.id.ivUser);
        }
    }

}
