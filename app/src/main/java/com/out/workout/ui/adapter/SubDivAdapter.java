package com.out.workout.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.out.workout.R;
import com.out.workout.model.subDivModel;

import java.util.List;

public class SubDivAdapter extends RecyclerView.Adapter<SubDivAdapter.MyViewHolder> {
    private final Integer[] bgs = {R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color6, R.color.color7, R.color.color5, R.color.color9, R.color.color8, R.color.color10, R.color.color11, R.color.color12};
    private final Context context;
    private final List<subDivModel> subDivModels;

    public SubDivAdapter(Context context, List<subDivModel> subDivModels) {
        this.context = context;
        this.subDivModels = subDivModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nutrient, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String value = subDivModels.get(position).getAmount();
        if (value != null && value.contains("%")) {
            holder.tvValue.setText(value);
        } else {
            String value2 = subDivModels.get(position).getAmount();
            String[] split = value2.split(" ");
            holder.tvValue.setText(split[0].toString());
            if (split.length > 1) {
                holder.tvUnit.setText(split[1].toString());
            }
        }
        holder.tvName.setText(subDivModels.get(position).getName());
        holder.flValue.setBackgroundResource(bgs[position % bgs.length].intValue());
    }

    public interface setClickItem {
        void onClick(List<subDivModel> divModels, String name);
    }

    @Override
    public int getItemCount() {
        return subDivModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final FrameLayout flValue;
        private TextView tvUnit, tvName, tvValue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.TvItemName);
            tvValue = itemView.findViewById(R.id.TvItemValue);
            tvUnit = itemView.findViewById(R.id.TvItemUnit);
            flValue = itemView.findViewById(R.id.FlItemValue);
        }
    }
}
