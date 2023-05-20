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

public class TrainingFragment extends Fragment {

    private View MainView;
    private RecyclerView RvTraining;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainView = inflater.inflate(R.layout.fragment_training, container, false);

        initViews();
        initListeners();
        initActions();
        return MainView;
    }


    private void initViews() {
        if (MainView != null) {
            RvTraining = MainView.findViewById(R.id.RvTraining);
        }
    }

    private void initListeners() {

    }

    private void initActions() {
        ArrayList<WorkOutTypeModel> workOutModels = new ArrayList<>();

        WorkOutTypeModel workOutModel = new WorkOutTypeModel("Arm Workout", 0);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel("Weight Loss Workout", 0);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel("Abs Workout", 0);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel("Chest Workout", 0);
        workOutModels.add(workOutModel);
        RvTraining.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        TrainingAdapter trainingAdapter = new TrainingAdapter(getContext(), workOutModels, new TrainingAdapter.WorkoutInterface() {
            @Override
            public void setWorkout(int pos) {
                Intent intent = new Intent(getContext(), WorkoutListActivity.class);
                intent.putExtra(Constants.WorkoutType, workOutModels.get(pos).getTrainingName());
                startActivity(intent);
            }
        });
        RvTraining.setAdapter(trainingAdapter);
    }
}