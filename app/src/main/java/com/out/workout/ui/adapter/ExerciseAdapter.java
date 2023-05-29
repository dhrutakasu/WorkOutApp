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
    private int selectedItemPosition;
    private final String[] dataTitle = {"Sedentary", "Light", "Moderate", "Active", "Very Active", "Extra Active"};
    private final String[] dataDetails = {"Little or no exercise", "Exercise 1–3 times/week", "Exercise 4–5 times/week", "Daily exercise or intense exercise 3–4 times/week", "Intense exercise 6–7 times/week", "Very intense exercise daily"};
    private final Integer[] icons = {Integer.valueOf((int) R.drawable.ic_exercise_1), Integer.valueOf((int) R.drawable.ic_exercise_1), Integer.valueOf((int) R.drawable.ic_exercise_3), Integer.valueOf((int) R.drawable.ic_exercise_4), Integer.valueOf((int) R.drawable.ic_exercise_5), Integer.valueOf((int) R.drawable.ic_exercise_6)};

    public static final class EViewHolder extends RecyclerView.ViewHolder {
        final ExerciseAdapter this$0;
        private final View v;

        public EViewHolder(ExerciseAdapter this$0, View v) {
            super(v);
            this.this$0 = this$0;
            this.v = v;
        }

        public void m111bindView$lambda0(ExerciseAdapter this$0, int i, View view) {
            if (this$0.getSelectedItemPosition() != i) {
                int selectedItemPosition = this$0.getSelectedItemPosition();
                this$0.setSelectedItemPosition(i);
                this$0.notifyItemChanged(selectedItemPosition);
                this$0.notifyItemChanged(this$0.getSelectedItemPosition());
            }
        }

        public final void bindView(final int i) {
            int i2;
            FrameLayout frameLayout = (FrameLayout) this.v.findViewById(R.id.flContainer);
            if (i == 0) {
                Context context = this.v.getContext();
                i2 = Constants.dpToPx(context,20);
            } else {
                i2 = 0;
            }
            Context context2 = this.v.getContext();
            frameLayout.setPadding(i2, 0, Constants.dpToPx(context2,10 ), 0);
            ((TextView) this.v.findViewById(R.id.tvExerciseTypeTitle)).setText(this.this$0.dataTitle[i]);
            ((TextView) this.v.findViewById(R.id.tvExerciseTypeDetails)).setText(this.this$0.dataDetails[i]);
            ((ImageView) this.v.findViewById(R.id.ivExercise)).setImageResource(this.this$0.icons[i].intValue());
            ((ImageView) this.v.findViewById(R.id.ivExerciseSelected)).setVisibility(i != this.this$0.getSelectedItemPosition() ? View.GONE : View.VISIBLE);
            final ExerciseAdapter exerciseAdapter = this.this$0;
            ((FrameLayout) this.v.findViewById(R.id.flExerciseContainer)).setOnClickListener(new View.OnClickListener() { // from class: com.own.menworkout.healthyfood.view.adapter.ExerciseAdapter.EViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    EViewHolder.this.m111bindView$lambda0(exerciseAdapter, i, view);
                }
            });
        }
    }

    @Override 
    public int getItemCount() {
        return this.dataTitle.length;
    }

    public final int getSelectedItemPosition() {
        return this.selectedItemPosition;
    }

    public final void setSelectedItemPosition(int i) {
        this.selectedItemPosition = i;
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
