package com.out.workout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.out.workout.R;
import com.out.workout.model.NutrientModel;
import com.out.workout.model.ValueModel;
import com.out.workout.ui.activity.FatBodyCalculatorActivity;
import com.out.workout.ui.activity.FatCalorieCalculatorActivity;
import com.out.workout.ui.activity.FatProteinCalculatorActivity;
import com.out.workout.ui.activity.FatWaterIntakeCalculatorActivity;
import com.out.workout.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NavigableMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NutrientAdapter extends RecyclerView.Adapter<NutrientAdapter.MyViewHolder> {
    private final Context context;
    private final List<ValueModel> categories;
    private final String DietName;

    public NutrientAdapter(Context context, List<ValueModel> list, String dietName) {
        this.context = context;
        this.categories = list;
        this.DietName = dietName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nutrient, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String value = categories.get(position).getAmount();
        if (value != null && value.contains("%")) {
            holder.tvValue.setText(value);
        } else {
            String value2 = categories.get(position).getName();
            List<String> split = Arrays.asList(value2.split(" ", 0));
            holder.tvValue.setText(split.get(position).toString());
            if (split.size() > 1) {
                holder.tvUnit.setText(split.get(position).toString());
            }
        }
        holder.tvName.setText(categories.get(position).getName());

        holder.tvUnit.setText(categories.get(position).getAmount());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startCategoryActivity(position);
//            }
//        });
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
            intent5.putExtra("bmr", true);
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
        private TextView tvUnit,tvName,tvValue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvValue = itemView.findViewById(R.id.tvValue);
            tvUnit = itemView.findViewById(R.id.tvUnit);
        }
    }
}
