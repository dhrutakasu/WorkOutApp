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

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<WorkOutTypeModel> workOutModel;
    private final WorkoutInterface workoutInterface;

    public TrainingAdapter(Context context, ArrayList<WorkOutTypeModel> workOutModel, WorkoutInterface workoutInterface) {
        this.context = context;
        this.workOutModel = workOutModel;
        this.workoutInterface = workoutInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TvTrainingName.setText(workOutModel.get(position).getTrainingName());
        holder.IvTrainingBanner.setImageResource(workOutModel.get(position).getTrainingImg());
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
        private final ImageView IvTrainingBanner;
        private final TextView TvTrainingName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IvTrainingBanner = itemView.findViewById(R.id.IvTrainingBanner);
            TvTrainingName = itemView.findViewById(R.id.TvTrainingName);
        }
    }
}
