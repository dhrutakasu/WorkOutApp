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
import com.out.workout.ui.activity.BodyFatCalculatorActivity;
import com.out.workout.ui.activity.CalorieCalculatorActivity;
import com.out.workout.ui.activity.CategoryItemDetailsActivity;
import com.out.workout.ui.activity.CategorySubItemListActivity;
import com.out.workout.ui.activity.FatBodyCalculatorActivity;
import com.out.workout.ui.activity.FatCalorieCalculatorActivity;
import com.out.workout.ui.activity.FatProteinCalculatorActivity;
import com.out.workout.ui.activity.FatWaterIntakeCalculatorActivity;
import com.out.workout.ui.activity.WaterIntakeCalculatorActivity;
import com.out.workout.utils.Constants;

import java.util.ArrayList;

public class FitnessAdapter extends RecyclerView.Adapter<FitnessAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<String> categories;

    public FitnessAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.categories = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TvDietName.setText(categories.get(position).toString());
        holder.IvDietImg.setImageResource(R.drawable.cat_vegeterian);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCategoryActivity(position);
            }
        });
    }

    private void startCategoryActivity(int pos) {
        if (pos == 0) {
            Intent intent = new Intent(context, FatCalorieCalculatorActivity.class);
            intent.putExtra(Constants.BMR, true);
            context.startActivity(intent);
        } else if (pos == 1) {
            Intent intent2 = new Intent(context, FatCalorieCalculatorActivity.class);
            context.startActivity(intent2);
        } else if (pos == 2) {
            Intent intent3 = new Intent(context, FatBodyCalculatorActivity.class);
            context.startActivity(intent3);
        } else if (pos == 3) {
            Intent intent4 = new Intent(context, FatProteinCalculatorActivity.class);
            context.startActivity(intent4);
        } else if (pos == 4) {
            Intent intent5 = new Intent(context, FatBodyCalculatorActivity.class);
            intent5.putExtra(Constants.BMR, true);
            context.startActivity(intent5);
        } else if (pos != 5) {
        } else {
            Intent intent6 = new Intent(context, FatWaterIntakeCalculatorActivity.class);
            context.startActivity(intent6);
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
