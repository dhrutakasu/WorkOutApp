package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.out.workout.R;
import com.out.workout.utils.Constants;
import com.out.workout.utils.SharePreference;

public class WaterIntakeCalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private EditText EdtAgeWater, EdtWeightWater;
    private RadioGroup RgWeightWater;
    private Button BtnWeightWater, BtnResetWater, BtnChartWater;
    private double DoubleHeight, DoubleWeight, DoubleAge, DoubleInch, DoubleWater, DoubleBMR;
    private boolean check;
    private int calculate_water;
    private String calculate_glass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtAgeWater = (EditText) findViewById(R.id.EdtAgeWater);
        RgWeightWater = (RadioGroup) findViewById(R.id.RgWeightWater);
        EdtWeightWater = (EditText) findViewById(R.id.EdtWeightWater);
        BtnWeightWater = (Button) findViewById(R.id.BtnWeightWater);
        BtnResetWater = (Button) findViewById(R.id.BtnResetWater);
        BtnChartWater = (Button) findViewById(R.id.BtnChartWater);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnWeightWater.setOnClickListener(this);
        BtnResetWater.setOnClickListener(this);
        BtnChartWater.setOnClickListener(this);
    }

    private void initActions() {
        TvTitle.setText(getString(R.string.waterintake));
        EdtAgeWater.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnWeightWater:
                GotoCalculateWeight();
                break;
            case R.id.BtnResetWater:
                GotoCalculateReset();
                break;
            case R.id.BtnChartWater:
                GotoCalculateChart();
                break;
        }
    }

    private void GotoCalculateWeight() {
        RadioButton radioWaterWeightButton = (RadioButton) findViewById(RgWeightWater.getCheckedRadioButtonId());
        String WaterWeight = (String) radioWaterWeightButton.getText();
        System.out.println("-- --- --- come : ");
        try {
            try {
                DoubleWeight = Double.parseDouble(EdtWeightWater.getText().toString());
            } catch (NumberFormatException unused) {
                check = true;
            }
            try {
                DoubleAge = Double.parseDouble(EdtAgeWater.getText().toString());
            } catch (NumberFormatException unused2) {
                check = true;
            }
            if (check) {
                Toast.makeText(context, getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                check = false;
                return;
            }
            if (!WaterWeight.equalsIgnoreCase(getString(R.string.kg))) {
                DoubleWeight /= 2.2d;
            }
            if (DoubleAge <= 30.0d) {
                DoubleWater = DoubleWeight * 40.0d;
            } else if (DoubleAge > 55.0d) {
                DoubleWater = DoubleWeight * 30.0d;
            } else {
                DoubleWater = DoubleWeight * 35.0d;
            }
            try {
                SharePreference.setCalculatorAge(context, Integer.parseInt(EdtAgeWater.getText().toString()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            DoubleWater /= 28.3d;
            DoubleWater /= 8.0d;
            calculate_water = (int) DoubleWater;
            calculate_glass = String.valueOf(calculate_water);

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
            LinearLayout LlWater = dialog.findViewById(R.id.LlWater);
            TextView TvDialogWater = dialog.findViewById(R.id.TvDialogWater);
            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            Button BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);

            LlWater.setVisibility(View.VISIBLE);
            TvDialogWeightSubTitle.setText(getString(R.string.dailywaterreq));
            TvDialogWater.setText(calculate_glass);

            BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());

            IvWeightClose.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        } catch (Resources.NotFoundException e2) {
            System.out.println("----- -- - - e22 come : " + e2.getMessage());
            e2.printStackTrace();
        }
    }

    private void GotoCalculateReset() {
        EdtWeightWater.setText("");
        EdtAgeWater.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
        EdtAgeWater.requestFocus();

        RadioButton radioWeightButton = (RadioButton) RgWeightWater.getChildAt(0);
        radioWeightButton.setChecked(true);
    }

    private void GotoCalculateChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.waterintake)));
    }
}