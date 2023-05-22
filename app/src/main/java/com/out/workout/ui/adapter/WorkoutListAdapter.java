package com.out.workout.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.getkeepsafe.android.multistateanimation.MultiStateAnimation;
import com.out.workout.R;
import com.out.workout.model.WorkoutExerciseModel;

import java.util.ArrayList;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.MyViewHolder> {
    private final Context context;
    private final String countDays;
    private final ArrayList<WorkoutExerciseModel> exerciseModels;
    private final WorkoutListInterface listInterface;

    public WorkoutListAdapter(Context context,String count, ArrayList<WorkoutExerciseModel> workoutExerciseModels,WorkoutListInterface workoutListInterface) {
        this.context = context;
        this.countDays = count;
        this.exerciseModels = workoutExerciseModels;
        this.listInterface = workoutListInterface;
    }

    @NonNull
    @Override
    public WorkoutListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position == exerciseModels.size() - 1) {
            holder.View_divider.setVisibility(View.GONE);
        } else {
            holder.View_divider.setVisibility(View.VISIBLE);
        }
        if (position < exerciseModels.size()) {
            holder.CvItem.setVisibility(View.VISIBLE);
            WorkoutExerciseModel exerciseModel = exerciseModels.get(position);
            MultiStateAnimation.SectionBuilder sectionBuilder = new MultiStateAnimation.SectionBuilder("pending");
            for (int i = 0; i < exerciseModel.getExerciseImg().length(); i++) {
                sectionBuilder.addFrame(exerciseModel.getExerciseImg().getResourceId(i,0));
            }
            sectionBuilder.setOneshot(false);
            sectionBuilder.setFrameDuration(800);
            MultiStateAnimation stateAnimation = new MultiStateAnimation.Builder(holder.IvExerciseAnimation).addSection(sectionBuilder).build(context);
            stateAnimation.transitionNow("pending");
            holder.TvExerciseName.setText(exerciseModels.get(position).getExerciseName().replace("_", " ").toUpperCase());
            for (int i = 0; i < exerciseModel.getExerciseType().length; i++) {
                StringBuilder builder;
                if (exerciseModel.getExerciseImg().length() > 1) {
                    builder = new StringBuilder();
                    builder.append("x");
                    builder.append(exerciseModel.getExerciseType()[i]);
                } else {
                    builder = new StringBuilder();
                    builder.append(exerciseModel.getExerciseType()[i]);
                    builder.append(" Sec");
                }
                holder.TvExerciseRotate.setText(builder.toString());
            }
        } else {
            holder.CvItem.setVisibility(View.GONE);
        }
        holder.CvItem.setOnClickListener(v -> {
            WorkoutExerciseModel exerciseModel=exerciseModels.get(position);
            listInterface.setWorkoutList(exerciseModel,position,countDays);
        });
    }

    @Override
    public int getItemCount() {
        return exerciseModels.size();
    }
    public interface WorkoutListInterface {
        void setWorkoutList(WorkoutExerciseModel exerciseModel, int position, String countDays);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView TvExerciseName;
        private final TextView TvExerciseRotate;
        private final ImageView IvExerciseAnimation;
        private final CardView CvItem;
        private final View View_divider;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            TvExerciseName = (TextView) itemView.findViewById(R.id.TvExerciseName);
            TvExerciseRotate = (TextView) itemView.findViewById(R.id.TvExerciseRotate);
            IvExerciseAnimation = (ImageView) itemView.findViewById(R.id.IvExerciseAnimation);
            CvItem = (CardView) itemView.findViewById(R.id.CvItem);
            View_divider = (View) itemView.findViewById(R.id.View_divider);
        }
    }
}
