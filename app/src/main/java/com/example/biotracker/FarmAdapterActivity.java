package com.example.biotracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FarmAdapterActivity extends RecyclerView.Adapter<FarmAdapterActivity.FarmViewHolder>{

    private Context mCtx;
    private List<Farm> farmList;

    public FarmAdapterActivity(Context mCtx, List<Farm> farmList) {
        this.mCtx = mCtx;
        this.farmList = farmList;
    }


    @NonNull
    @Override
    public FarmAdapterActivity.FarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.layout_farm_lists, null);

        return new FarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmAdapterActivity.FarmViewHolder holder, int position) {

        Farm farm = farmList.get(position);

        holder.tv_fish_name.setText(farm.getFish_type());
        holder.tv_fish_count.setText(farm.getFish_count());
        double vol = Double.parseDouble(farm.getTank_volume());
        double vol_in_l = vol / 1000;
        holder.tv_tank_volume.setText(String.valueOf(vol_in_l));
    }

    @Override
    public int getItemCount() {
        return farmList.size();
    }

    class FarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private final Context context;

        TextView tv_fish_name, tv_fish_count, tv_tank_volume;

        public FarmViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            itemView.setOnClickListener(this);
            tv_fish_name = itemView.findViewById(R.id.tv_farm_fish_name);
            tv_fish_count = itemView.findViewById(R.id.tv_farm_fish_count);
            tv_tank_volume = itemView.findViewById(R.id.tv_farm_fish_volume);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent();

            for (int i=0;i<getItemCount();i++)
            {
                if (v == itemView)
                {
                    intent = new Intent(context, FarmDetailsActivity.class);
                    intent.putExtra("farm_id", farmList.get(getLayoutPosition()).farm_id);
                    intent.putExtra("fish_type", farmList.get(getLayoutPosition()).fish_type);
                    intent.putExtra("fish_count", farmList.get(getLayoutPosition()).fish_count);
                    intent.putExtra("tank_volume", String.valueOf(Double.parseDouble(farmList.get(getLayoutPosition()).tank_volume) / 1000));
                    intent.putExtra("start_date", farmList.get(getLayoutPosition()).start_date);
                    intent.putExtra("est_time", farmList.get(getLayoutPosition()).est_time);
                    context.startActivity(intent);
                    break;
                }
            }
        }
    }

}
