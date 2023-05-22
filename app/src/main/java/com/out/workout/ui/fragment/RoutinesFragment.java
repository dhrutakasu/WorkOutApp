package com.out.workout.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.out.workout.R;
import com.out.workout.model.WorkOutTypeModel;
import com.out.workout.ui.activity.WorkoutListActivity;
import com.out.workout.ui.adapter.TrainingAdapter;
import com.out.workout.utils.Constants;

import java.util.ArrayList;

public class RoutinesFragment extends Fragment {

    private View RoutinesView;
    private RecyclerView RvRoutines;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RoutinesView = inflater.inflate(R.layout.fragment_routines, container, false);

        initViews();
        initListeners();
        initActions();
        return RoutinesView;
    }


    private void initViews() {
        if (RoutinesView != null) {
            RvRoutines = RoutinesView.findViewById(R.id.RvRoutines);
        }
    }

    private void initListeners() {

    }

    private void initActions() {
        ArrayList<WorkOutTypeModel> workOutModels = new ArrayList<>();

        WorkOutTypeModel workOutModel = new WorkOutTypeModel(getString(R.string.morning_title), R.drawable.arm_workout_banner);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getString(R.string.evening_title), R.drawable.arm_workout_banner);
        workOutModels.add(workOutModel);
        RvRoutines.setLayoutManager(new LinearLayoutManager(getContext()));
        TrainingAdapter trainingAdapter = new TrainingAdapter(getContext(), workOutModels, new TrainingAdapter.WorkoutInterface() {
            @Override
            public void setWorkout(int pos) {
                Intent intent = new Intent(getContext(), WorkoutListActivity.class);
                intent.putExtra(Constants.WorkoutType, workOutModels.get(pos).getTrainingName());
                startActivity(intent);
            }
        });
        RvRoutines.setAdapter(trainingAdapter);
    }
}