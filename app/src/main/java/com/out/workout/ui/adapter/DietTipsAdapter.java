package com.out.workout.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.out.workout.R;
import com.out.workout.model.DietTipsCategory;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DietTipsAdapter extends RecyclerView.Adapter<DietTipsAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<DietTipsCategory> categories;

    public DietTipsAdapter(Context context, ArrayList<DietTipsCategory> categories) {
        this.context=context;
        this.categories=categories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diet_tips, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView IvDietImg;
        private TextView TvDietName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IvDietImg=itemView.findViewById(R.id.IvDietImg);
            TvDietName=itemView.findViewById(R.id.TvDietName);
        }
    }
}
