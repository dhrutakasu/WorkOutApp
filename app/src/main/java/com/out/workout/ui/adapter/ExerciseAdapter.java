package com.out.workout.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.out.workout.R;
import com.out.workout.utils.Constants;

public final class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.EViewHolder> {
    private int ItemPosition;
    private final String[] dataTitle = {"Sedentary", "Light", "Moderate", "Active", "Very Active", "Extra Active"};
    private final String[] dataDetails = {"Little or no exercise", "Exercise 1–3 times/week", "Exercise 4–5 times/week", "Daily exercise or intense exercise 3–4 times/week", "Intense exercise 6–7 times/week", "Very intense exercise daily"};
    private final Integer[] icons = { R.drawable.ic_exercise_1,  R.drawable.ic_exercise_1,  R.drawable.ic_exercise_3,  R.drawable.ic_exercise_4,  R.drawable.ic_exercise_5,  R.drawable.ic_exercise_6};

    public static final class EViewHolder extends RecyclerView.ViewHolder {
        final ExerciseAdapter context;
        private final View view;

        public EViewHolder(ExerciseAdapter adapter, View view) {
            super(view);
            this.context = adapter;
            this.view = view;
        }

        public final void bindView(final int i) {
            int i2;
            FrameLayout frameLayout = (FrameLayout) this.view.findViewById(R.id.flContainer);
            if (i == 0) {
                Context context = this.view.getContext();
                i2 = Constants.dpToPx(context, 20);
            } else {
                i2 = 0;
            }
            Context context2 = this.view.getContext();
            frameLayout.setPadding(i2, 0, Constants.dpToPx(context2, 10), 0);
            ((TextView) this.view.findViewById(R.id.tvExerciseTypeTitle)).setText(context.dataTitle[i]);
            ((TextView) this.view.findViewById(R.id.tvExerciseTypeDetails)).setText(context.dataDetails[i]);
            ((ImageView) this.view.findViewById(R.id.ivExercise)).setImageResource(context.icons[i].intValue());
            ((ImageView) this.view.findViewById(R.id.ivExerciseSelected)).setVisibility(i != context.getItemPosition() ? View.GONE : View.VISIBLE);
            ((FrameLayout) this.view.findViewById(R.id.flExerciseContainer)).setOnClickListener(view -> {
                if (context.getItemPosition() != i) {
                    int selectedItemPosition = context.getItemPosition();
                    context.setItemPosition(i);
                    context.notifyItemChanged(selectedItemPosition);
                    context.notifyItemChanged(context.getItemPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.dataTitle.length;
    }

    public final int getItemPosition() {
        return this.ItemPosition;
    }

    public final void setItemPosition(int i) {
        this.ItemPosition = i;
    }

    @Override
    public void onBindViewHolder(EViewHolder holder, int i) {
        holder.bindView(i);
    }

    @Override
    public EViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_type, parent, false);
        return new EViewHolder(this, inflate);
    }
}
