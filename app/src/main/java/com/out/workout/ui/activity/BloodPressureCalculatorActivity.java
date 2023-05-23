package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.out.workout.R;

public class BloodPressureCalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private EditText EdtSystolicBloodPressure, EdtDiastolicBloodPressure;
    private Button BtnWeightBloodPressure, BtnResetBloodPressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtSystolicBloodPressure = (EditText) findViewById(R.id.EdtSystolicBloodPressure);
        EdtDiastolicBloodPressure = (EditText) findViewById(R.id.EdtDiastolicBloodPressure);
        BtnWeightBloodPressure = (Button) findViewById(R.id.BtnWeightBloodPressure);
        BtnResetBloodPressure = (Button) findViewById(R.id.BtnResetBloodPressure);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnWeightBloodPressure.setOnClickListener(this);
        BtnResetBloodPressure.setOnClickListener(this);
    }

    private void initActions() {
        TvTitle.setText(getString(R.string.blood_pressure));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnWeightBloodPressure:
                GotoCalculationBloodPressure();
                break;
            case R.id.BtnResetBloodPressure:
                GotoResetBloodPressure();
                break;
        }
    }

    private void GotoCalculationBloodPressure() {
        String CalculateBp;
        if (TextUtils.isEmpty(EdtSystolicBloodPressure.getText().toString())) {
            EdtSystolicBloodPressure.setError(getResources().getString(R.string.valid));
        } else if (TextUtils.isEmpty(EdtDiastolicBloodPressure.getText().toString())) {
            EdtDiastolicBloodPressure.setError(getResources().getString(R.string.valid));
        } else {
            float FloatSystolic = Float.parseFloat(EdtSystolicBloodPressure.getText().toString());
            float FloatDiastolic = Float.parseFloat(EdtDiastolicBloodPressure.getText().toString());
            if (FloatSystolic > 180.0f || FloatDiastolic > 110.0f) {
                CalculateBp = getResources().getString(R.string.hypertensive_crisis);
            } else if (FloatSystolic >= 160.0f || FloatDiastolic >= 100.0f) {
                CalculateBp = getResources().getString(R.string.high_bp_stage2);
            } else if (FloatSystolic > 140.0f || FloatDiastolic > 90.0f) {
                CalculateBp = getResources().getString(R.string.high_bp_stage1);
            } else if (FloatSystolic > 120.0f || FloatDiastolic > 80.0f) {
                CalculateBp = getResources().getString(R.string.prehypertension);
            } else if (FloatSystolic <= 80.0f || FloatDiastolic <= 60.0f) {
                CalculateBp = getResources().getString(R.string.low_bp);
            } else {
                CalculateBp = getResources().getString(R.string.normal_bp);
            }

            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_weight);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            Window window = dialog.getWindow();
            lp.copyFrom(window.getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;
            window.setAttributes(lp);

            ImageView IvWeightClose = dialog.findViewById(R.id.IvWeightClose);
            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            TextView TvDialogWeightValue = dialog.findViewById(R.id.TvDialogWeightValue);
            Button BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);

            TvDialogWeightValue.setVisibility(View.VISIBLE);

            TvDialogWeightSubTitle.setText(getString(R.string.your_bp));
            TvDialogWeightValue.setText(String.valueOf(CalculateBp));

            BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());

            IvWeightClose.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        }
    }

    private void GotoResetBloodPressure() {
        EdtSystolicBloodPressure.setText("");
        EdtDiastolicBloodPressure.setText("");
        EdtSystolicBloodPressure.requestFocus();
    }
}