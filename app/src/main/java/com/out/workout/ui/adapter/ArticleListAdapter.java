package com.out.workout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.out.workout.R;
import com.out.workout.model.ArticleModel;
import com.out.workout.model.SubCategories;
import com.out.workout.ui.activity.ArticleDetailsActivity;
import com.out.workout.ui.activity.CategoryItemDetailsActivity;
import com.out.workout.ui.activity.CategorySubItemListActivity;
import com.out.workout.utils.Constants;

import java.util.ArrayList;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<ArticleModel> categories;

    public ArticleListAdapter(Context context, ArrayList<ArticleModel> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TvDietName.setText(categories.get(position).getName());
        holder.CardImg.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(v -> {
            context.startActivity(new Intent(context, ArticleDetailsActivity.class)
                    .putExtra(Constants.DIET_SLUG, categories.get(position).getSlug())
                    .putExtra(Constants.DIET_NAME, categories.get(position).getName())
                    .putExtra(Constants.DIET_IMG, categories.get(position).getIcon()));
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView IvDietImg;
        private CardView CardImg;
        private TextView TvDietName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IvDietImg = itemView.findViewById(R.id.IvDietImg);
            CardImg = itemView.findViewById(R.id.CardImg);
            TvDietName = itemView.findViewById(R.id.TvDietName);
        }
    }
}
