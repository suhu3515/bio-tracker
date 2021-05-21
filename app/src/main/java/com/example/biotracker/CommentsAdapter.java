package com.example.biotracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>{

    private Context mCtx;
    private List<Comments> commentsList;

    public CommentsAdapter(Context mCtx, List<Comments> commentsList) {
        this.mCtx = mCtx;
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public CommentsAdapter.CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_comments, null);
        return new CommentsAdapter.CommentsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.CommentsViewHolder holder, int position)
    {
        Comments comment = commentsList.get(position);

        holder.textViewCommenter.setText(comment.getCommentedUser());
        holder.textViewComment.setText(comment.getCommentText());
    }

    @Override
    public int getItemCount()
    {
        return commentsList.size();
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewComment, textViewCommenter;
        CardView cardView;
        public CommentsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textViewComment = itemView.findViewById(R.id.textViewCommentText);
            textViewCommenter = itemView.findViewById(R.id.textViewCommentedUser);
            cardView = itemView.findViewById(R.id.cardview_comments);
        }
    }
}
