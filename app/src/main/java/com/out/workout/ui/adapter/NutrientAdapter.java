package com.out.workout.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.out.workout.R;
import com.out.workout.model.ValueModel;
import com.out.workout.model.subDivModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NutrientAdapter extends RecyclerView.Adapter<NutrientAdapter.MyViewHolder> {
    private final Integer[] bgs = {R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color6, R.color.color7, R.color.color5, R.color.color9, R.color.color8, R.color.color10, R.color.color11, R.color.color12};
    private final Context context;
    private final List<ValueModel> categories;
    private final String DietName;
    private final List<subDivModel> subDivModels;
    private final setClickItem setClick;

    public NutrientAdapter(Context context, List<ValueModel> list, List<subDivModel> subDivModels, String dietName, setClickItem setClickItem) {
        this.context = context;
        this.categories = list;
        this.subDivModels = subDivModels;
        this.DietName = dietName;
        this.setClick = setClickItem;
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
            holder.TvItemValue.setText(value);
        } else {
            String value2 = categories.get(position).getAmount();
            String[] split = value2.split(" ");
            holder.TvItemValue.setText(split[0].toString());
            if (split.length > 1) {
                holder.TvItemUnit.setText(split[1].toString());
            }
        }
        holder.TvItemName.setText(categories.get(position).getName());
        holder.FlItemValue.setBackgroundResource(bgs[position % bgs.length].intValue());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!categories.get(position).getSubDiv().isEmpty()) {
                    setClick.onClick(categories.get(position).getSubDiv(), categories.get(position).getName());
                }
            }
        });
    }

    public interface setClickItem {
        void onClick(List<subDivModel> divModels, String name);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final FrameLayout FlItemValue;
        private TextView TvItemUnit, TvItemName, TvItemValue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvItemName = itemView.findViewById(R.id.TvItemName);
            TvItemValue = itemView.findViewById(R.id.TvItemValue);
            TvItemUnit = itemView.findViewById(R.id.TvItemUnit);
            FlItemValue = itemView.findViewById(R.id.FlItemValue);
        }
    }
}
