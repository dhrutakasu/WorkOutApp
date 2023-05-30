package com.out.workout.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

    private View TraningView;
    private RecyclerView RvTraining;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TraningView = inflater.inflate(R.layout.fragment_training, container, false);

        initViews();
        initListeners();
        initActions();
        return TraningView;
    }
    private void initViews() {
        if (TraningView != null) {
            RvTraining = TraningView.findViewById(R.id.RvTraining);
        }
    }

    private void initListeners() {

    }

    private void initActions() {
        ArrayList<WorkOutTypeModel> workOutModels = new ArrayList<>();

        WorkOutTypeModel workOutModel = new WorkOutTypeModel(getString(R.string.buttocks_title), R.drawable.ic_arms);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel("Weight Loss Workout", R.drawable.ic_weight_los);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getString(R.string.abs_title), R.drawable.ic_abs);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getString(R.string.fatburn_title), R.drawable.ic_chest);
        workOutModels.add(workOutModel);
        RvTraining.setLayoutManager(new GridLayoutManager(getContext(),2));
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