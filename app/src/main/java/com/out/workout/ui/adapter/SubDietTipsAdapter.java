package com.out.workout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.out.workout.R;
import com.out.workout.model.SubCategories;
import com.out.workout.ui.activity.CategoryItemDetailsActivity;
import com.out.workout.ui.activity.CategorySubItemListActivity;
import com.out.workout.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SubDietTipsAdapter extends RecyclerView.Adapter<SubDietTipsAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<SubCategories> categories;
    private final String dietName;

    public SubDietTipsAdapter(Context context, ArrayList<SubCategories> categories, String dietName) {
        this.context = context;
        this.categories = categories;
        this.dietName = dietName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diet_tips, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TvDietTitle.setVisibility(View.GONE);
        holder.IvArrow.setVisibility(View.GONE);
        holder.TvDietName.setLines(1);
        holder.TvDietName.setText(categories.get(position).getName());
        try {
            InputStream ims = context.getAssets().open("DietImg/" + categories.get(position).getIcon());
            Bitmap bitmap = BitmapFactory.decodeStream(ims);
            holder.IvDietImg.setImageBitmap(bitmap);
            ims.close();
        } catch (IOException ex) {
            System.out.println("----- catch : " + ex.getMessage());
            return;
        }
        holder.itemView.setOnClickListener(v -> startCategoryActivity(categories.get(position)));
    }

    private void startCategoryActivity(SubCategories categories) {
        Intent intent = new Intent(context, categories.isSingleItem() ? CategoryItemDetailsActivity.class : CategorySubItemListActivity.class);
        intent.putExtra(Constants.DIET_NAME, categories.getName());
        intent.putExtra(Constants.DIET_SLUG, categories.getSlug());
        intent.putExtra(Constants.DIET_IMG, categories.getIcon());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView IvDietImg,IvArrow;
        private TextView TvDietName, TvDietTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IvDietImg = itemView.findViewById(R.id.IvDietImg);
            IvArrow = itemView.findViewById(R.id.IvArrow);
            TvDietName = itemView.findViewById(R.id.TvDietName);
            TvDietTitle = itemView.findViewById(R.id.TvDietTitle);
        }
    }
}
