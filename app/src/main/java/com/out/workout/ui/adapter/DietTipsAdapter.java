package com.out.workout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.out.workout.BuildConfig;
import com.out.workout.R;
import com.out.workout.model.DietTipsCategory;
import com.out.workout.ui.activity.ArticleActivity;
import com.out.workout.ui.activity.CategorySubItemListActivity;
import com.out.workout.ui.activity.FitnessCalculatorsActivity;
import com.out.workout.utils.Constants;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DietTipsAdapter extends RecyclerView.Adapter<DietTipsAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<DietTipsCategory> categories;

    public DietTipsAdapter(Context context, ArrayList<DietTipsCategory> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diet_tips, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TvDietName.setText(categories.get(position).getName());
        holder.IvDietImg.setImageResource(context.getResources().getIdentifier(categories.get(position).getIcon().toString().substring(0,categories.get(position).getIcon().toString().lastIndexOf(".png")), "drawable", BuildConfig.APPLICATION_ID));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCategoryActivity(categories.get(position), v);
            }
        });
    }

    private void startCategoryActivity(DietTipsCategory categories, View view) {
        int layoutType = categories.getLayoutType();
        if (layoutType == 1) {
            Intent intent = new Intent(context, CategorySubItemListActivity.class);
            intent.putExtra(Constants.DIET_NAME, categories.getName());
            intent.putExtra(Constants.DIET_SLUG, categories.getSlug());
            intent.putExtra(Constants.DIET_IMG, categories.getIcon());
            context.startActivity(intent);
        } else if (layoutType == 2) {
            Intent intentFit = new Intent(context, FitnessCalculatorsActivity.class);
            intentFit.putExtra(Constants.DIET_IMG, categories.getIcon());
            context.startActivity(intentFit);
        } else if (layoutType == 3) {
        } else if (layoutType != 4) {
        } else {
            Intent intent4 = new Intent(context, ArticleActivity.class);
            intent4.putExtra(Constants.DIET_NAME, categories.getName());
            intent4.putExtra(Constants.DIET_SLUG, categories.getSlug());
            context.startActivity(intent4);
        }
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
            IvDietImg = itemView.findViewById(R.id.IvDietImg);
            TvDietName = itemView.findViewById(R.id.TvDietName);
        }
    }
}
