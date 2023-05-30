package com.out.workout.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.out.workout.R;
import com.out.workout.model.ValueModel;

import java.util.List;

public class VitaminsAdapter extends RecyclerView.Adapter<VitaminsAdapter.MyViewHolder> {
    private final Context context;
    private final List<ValueModel> categories;

    public VitaminsAdapter(Context context, List<ValueModel> list) {
        this.context = context;
        this.categories = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vitamins, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ValueModel model=categories.get(position);
        String value = model.getAmount();
        if (value == null) {
            value = "";
        }
        holder.TvItemValue.setText(value);
        String name = model.getName();
        holder.TvItemValueName.setText(name != null ? name : "");
        holder.TvItemValuePercentage.setText(model.getDailyValue());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView TvItemValue, TvItemValueName, TvItemValuePercentage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvItemValueName = itemView.findViewById(R.id.TvItemValueName);
            TvItemValuePercentage = itemView.findViewById(R.id.TvItemValuePercentage);
            TvItemValue = itemView.findViewById(R.id.TvItemValue);
        }
    }
}
