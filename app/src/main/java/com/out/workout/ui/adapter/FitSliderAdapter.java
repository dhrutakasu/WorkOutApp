package com.out.workout.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.out.workout.R;

import java.util.ArrayList;

public final class FitSliderAdapter extends RecyclerView.Adapter<FitSliderAdapter.MyViewHolder> {
    private SliderCallback sliderCallback;
    private final ArrayList<String> data = new ArrayList<>();

    public interface SliderCallback {
        void onItemClicked(View view);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setCallback(SliderCallback sliderCallback) {
        this.sliderCallback = sliderCallback;
    }

    public void setData(ArrayList<String> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        holder.tvItem.setText(data.get(i));
        holder.tvItem.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == null || (sliderCallback) == null) {
                    return;
                }
                sliderCallback.onItemClicked(v);
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider, parent, false);
        return new MyViewHolder(inflate);
    }

    public final class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
            if (tvItem != null) {
                tvItem.setTextColor(Color.parseColor("#CBCBCB"));
            }
        }
    }
}
