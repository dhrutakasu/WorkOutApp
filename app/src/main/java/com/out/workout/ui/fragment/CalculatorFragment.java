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
import com.out.workout.ui.activity.BloodAlcoholCalculatorActivity;
import com.out.workout.ui.activity.BloodDonationCalculatorActivity;
import com.out.workout.ui.activity.BloodPressureCalculatorActivity;
import com.out.workout.ui.activity.BloodVolumeCalculatorActivity;
import com.out.workout.ui.activity.BmiCalculatorActivity;
import com.out.workout.ui.activity.BodyFatCalculatorActivity;
import com.out.workout.ui.activity.BreathCalculatorActivity;
import com.out.workout.ui.activity.CalorieCalculatorActivity;
import com.out.workout.ui.activity.HeartRateCalculatorActivity;
import com.out.workout.ui.activity.OvulationCalculatorActivity;
import com.out.workout.ui.activity.PregnancyCalculatorActivity;
import com.out.workout.ui.activity.WaterIntakeCalculatorActivity;
import com.out.workout.ui.activity.WeightCalculatorActivity;
import com.out.workout.ui.adapter.CalculatorAdapter;

import java.util.ArrayList;

public class CalculatorFragment extends Fragment {

    private View CalculatorView;
    private RecyclerView RvCalculator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CalculatorView = inflater.inflate(R.layout.fragment_calculator, container, false);

        initViews();
        initListeners();
        initActions();
        return CalculatorView;
    }

    private void initViews() {
        if (CalculatorView != null) {
            RvCalculator = (RecyclerView) CalculatorView.findViewById(R.id.RvCalculator);
        }
    }

    private void initListeners() {

    }

    private void initActions() {
        ArrayList<WorkOutTypeModel> workOutModels = new ArrayList<>();

        WorkOutTypeModel workOutModel = new WorkOutTypeModel(getResources().getString(R.string.str_idealweight), getResources().getString(R.string.str_idealweight_desc), R.drawable.ic_ideal_weight);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getResources().getString(R.string.str_bmi_title), getResources().getString(R.string.str_bmi_desc), R.drawable.ic_body_mass_index);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getResources().getString(R.string.str_heartrate), getResources().getString(R.string.str_heart_desc), R.drawable.ic_heart_reate);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getResources().getString(R.string.str_bloodvol), getResources().getString(R.string.str_bloodvol_desc), R.drawable.ic_blood_volume);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getResources().getString(R.string.str_blood_donate), getResources().getString(R.string.str_blood_don_desc), R.drawable.ic_blood_donate);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getResources().getString(R.string.str_calories), getResources().getString(R.string.str_calorie_desc), R.drawable.ic_weight_loss_gain);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getResources().getString(R.string.str_waterintake), getResources().getString(R.string.str_waterintake_desc), R.drawable.ic_water_intake);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getResources().getString(R.string.str_bodyfat), getResources().getString(R.string.str_bodyfat_desc), R.drawable.ic_body_fat);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getResources().getString(R.string.str_bloodalcohol), getResources().getString(R.string.str_bloodalcohol_desc), R.drawable.ic_blood_alcohol);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getResources().getString(R.string.str_pregnancy), getResources().getString(R.string.str_pregnancy_desc), R.drawable.ic_pregncy_due_date);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getResources().getString(R.string.str_ovulation), getResources().getString(R.string.str_ovulation_desc), R.drawable.ic_ovulation);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getResources().getString(R.string.str_breath_count), getResources().getString(R.string.str_breath_count_desc), R.drawable.ic_breath_count);
        workOutModels.add(workOutModel);
        workOutModel = new WorkOutTypeModel(getResources().getString(R.string.str_blood_pressure), getResources().getString(R.string.str_calc_bp_val), R.drawable.ic_blood_pressure);
        workOutModels.add(workOutModel);
        RvCalculator.setLayoutManager(new LinearLayoutManager(getContext()));
        CalculatorAdapter calculatorAdapter = new CalculatorAdapter(getContext(), workOutModels, new CalculatorAdapter.WorkoutInterface() {
            @Override
            public void setWorkout(int pos) {
                setCalculator(pos);
            }
        });
        RvCalculator.setAdapter(calculatorAdapter);
    }

    private void setCalculator(int pos) {
        switch (pos) {
            case 0:
                GoIntent(WeightCalculatorActivity.class);
                break;
            case 1:
                GoIntent(BmiCalculatorActivity.class);
                break;
            case 2:
                GoIntent(HeartRateCalculatorActivity.class);
                break;
            case 3:
                GoIntent(BloodVolumeCalculatorActivity.class);
                break;
            case 4:
                GoIntent(BloodDonationCalculatorActivity.class);
                break;
            case 5:
                GoIntent(CalorieCalculatorActivity.class);
                break;
            case 6:
                GoIntent(WaterIntakeCalculatorActivity.class);
                break;
            case 7:
                GoIntent(BodyFatCalculatorActivity.class);
                break;
            case 8:
                GoIntent(BloodAlcoholCalculatorActivity.class);
                break;
            case 9:
                GoIntent(PregnancyCalculatorActivity.class);
                break;
            case 10:
                GoIntent(OvulationCalculatorActivity.class);
                break;
            case 11:
                GoIntent(BreathCalculatorActivity.class);
                break;
            case 12:
                GoIntent(BloodPressureCalculatorActivity.class);
                break;
        }
    }

    public void GoIntent(Class cls) {
        startActivity(new Intent(getActivity(), cls));
    }
}