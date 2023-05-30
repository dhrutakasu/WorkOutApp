package com.out.workout.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.out.workout.R;
import com.out.workout.model.subDivModel;

import java.util.List;

public class PropsAdapter extends RecyclerView.Adapter<PropsAdapter.MyViewHolder> {
    private final Context context;
    private final List<String> subDivModels;

    public PropsAdapter(Context context, List<String> subDivModels) {
        this.context = context;
        this.subDivModels = subDivModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_props, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       holder.TvItemPoint.setText(subDivModels.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return subDivModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView TvItemPoint;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvItemPoint = itemView.findViewById(R.id.TvItemPoint);
        }
    }
}
