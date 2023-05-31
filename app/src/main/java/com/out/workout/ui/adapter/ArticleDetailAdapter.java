package com.out.workout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.out.workout.R;
import com.out.workout.model.ArticleModel;
import com.out.workout.model.DetailModel;
import com.out.workout.ui.activity.ArticleDetailsActivity;
import com.out.workout.utils.Constants;

import java.util.ArrayList;

public class ArticleDetailAdapter extends RecyclerView.Adapter<ArticleDetailAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<DetailModel> categories;

    public ArticleDetailAdapter(Context context, ArrayList<DetailModel> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (categories.get(position).getHeader().equalsIgnoreCase("")){
            holder.TvDietName.setVisibility(View.GONE);
        }else {
            holder.TvDietName.setText(categories.get(position).getHeader());
        }
        holder.TvDietTitle.setText(categories.get(position).getFullDescription());
        holder.IvDietImg.setImageResource(R.drawable.cat_vegeterian);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView IvDietImg;
        private TextView TvDietName,TvDietTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IvDietImg = itemView.findViewById(R.id.IvDietImg);
            TvDietName = itemView.findViewById(R.id.TvDietName);
            TvDietTitle = itemView.findViewById(R.id.TvDietTitle);
        }
    }
}
