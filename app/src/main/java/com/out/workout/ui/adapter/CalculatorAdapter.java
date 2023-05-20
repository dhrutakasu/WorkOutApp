package com.out.workout.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.out.workout.R;
import com.out.workout.model.WorkOutTypeModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalculatorAdapter extends RecyclerView.Adapter<CalculatorAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<WorkOutTypeModel> workOutModel;
    private final WorkoutInterface workoutInterface;

    public CalculatorAdapter(Context context, ArrayList<WorkOutTypeModel> workOutModel, WorkoutInterface workoutInterface) {
        this.context = context;
        this.workOutModel = workOutModel;
        this.workoutInterface = workoutInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calculator, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.IvCalculatorBanner.setImageResource(workOutModel.get(position).getTrainingImg());
        holder.TvCalculatorName.setText(workOutModel.get(position).getTrainingName());
        holder.TvCalculatorDesc.setText(workOutModel.get(position).getDescription());
        holder.itemView.setOnClickListener(view -> workoutInterface.setWorkout(position));
    }

    public interface WorkoutInterface {
        void setWorkout(int pos);
    }

    @Override
    public int getItemCount() {
        return workOutModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView IvCalculatorBanner;
        private final TextView TvCalculatorName;
        private final TextView TvCalculatorDesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IvCalculatorBanner = itemView.findViewById(R.id.IvCalculatorBanner);
            TvCalculatorName = itemView.findViewById(R.id.TvCalculatorName);
            TvCalculatorDesc = itemView.findViewById(R.id.TvCalculatorDesc);
        }
    }
}
