package com.example.biotracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TutorialAdapterActivity extends RecyclerView.Adapter<TutorialAdapterActivity.TutorialViewHolder>{

    private Context mCtx;
    private List<Tutorial> tutorialList;

    public TutorialAdapterActivity(Context mCtx, List<Tutorial> tutorialList) {
        this.mCtx = mCtx;
        this.tutorialList = tutorialList;
    }

    @NonNull
    @Override
    public TutorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_tutorials, null);

        return new TutorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialViewHolder holder, int position)
    {
        Tutorial tutorial = tutorialList.get(position);

        holder.textViewTitle.setText(tutorial.getTutorialTitle());
        holder.textViewText.setText(tutorial.getTutorialText());
        if (tutorial.getTutorialLink().equals("null"))
        {
            holder.textViewVideo.setVisibility(View.GONE);
        }
        else
        {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent videoIntent = new Intent(mCtx,VideoTutorialActivity.class);
                    videoIntent.putExtra("video_link", tutorial.getTutorialLink());
                    mCtx.startActivity(videoIntent);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return tutorialList.size();
    }

    class TutorialViewHolder extends RecyclerView.ViewHolder
    {

        private final Context context;
        TextView textViewTitle, textViewText, textViewVideo;
        CardView cardView;

        public TutorialViewHolder(@NonNull View itemView)
        {
            super(itemView);

            context = itemView.getContext();
            textViewTitle = itemView.findViewById(R.id.text_tut_title);
            textViewText = itemView.findViewById(R.id.text_tut_text);
            textViewVideo = itemView.findViewById(R.id.video_tut_text);
            cardView = itemView.findViewById(R.id.card_tutorial);
        }

    }
}
