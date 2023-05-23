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

import java.text.NumberFormat;

public class BloodAlcoholCalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private EditText EdtAgeBloodAlcohol;
    private RadioGroup RgGender, RgWeightBloodAlcohol, RgDrink, RgTime;
    private RadioButton RbMale, RbFemale, RbOunces, RbMl, RbCup,RbHour,RbMinutes, RbDay;
    private EditText EdtWeightBloodAlcohol, EdtDrinkBloodAlcohol, EdtTimeBloodAlcohol;
    private Button BtnWeightBloodAlcohol, BtnResetBloodAlcohol,BtnChartBloodAlcohol;
    private double DoubleWeight, DoubleAge, DoubleTime, DoubleAlcohol,DoubleBloodAlcohol, DoubleNewAlcohol,calculateAlcohol;
    private boolean BoolCheckBloodAlcohol;
    private String calculate_BloodAlcohol;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_alcohol_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtAgeBloodAlcohol = (EditText) findViewById(R.id.EdtAgeBloodAlcohol);
        RgGender = (RadioGroup) findViewById(R.id.RgGender);
        RbMale = (RadioButton) findViewById(R.id.RbMale);
        RbFemale = (RadioButton) findViewById(R.id.RbFemale);
        RgWeightBloodAlcohol = (RadioGroup) findViewById(R.id.RgWeightBloodAlcohol);
        RgDrink = (RadioGroup) findViewById(R.id.RgDrink);
        RbOunces = (RadioButton) findViewById(R.id.RbOunces);
        RbMl = (RadioButton) findViewById(R.id.RbMl);
        RbCup = (RadioButton) findViewById(R.id.RbCup);
        RgTime = (RadioGroup) findViewById(R.id.RgTime);
        RbHour = (RadioButton) findViewById(R.id.RbHour);
        RbMinutes = (RadioButton) findViewById(R.id.RbMinutes);
        RbDay = (RadioButton) findViewById(R.id.RbDay);
        EdtDrinkBloodAlcohol = (EditText) findViewById(R.id.EdtDrinkBloodAlcohol);
        EdtTimeBloodAlcohol = (EditText) findViewById(R.id.EdtTimeBloodAlcohol);
        EdtWeightBloodAlcohol = (EditText) findViewById(R.id.EdtWeightBloodAlcohol);
        BtnWeightBloodAlcohol = (Button) findViewById(R.id.BtnWeightBloodAlcohol);
        BtnResetBloodAlcohol = (Button) findViewById(R.id.BtnResetBloodAlcohol);
        BtnChartBloodAlcohol = (Button) findViewById(R.id.BtnChartBloodAlcohol);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnWeightBloodAlcohol.setOnClickListener(this);
        BtnResetBloodAlcohol.setOnClickListener(this);
        BtnChartBloodAlcohol.setOnClickListener(this);
    }

    private void initActions() {
        TvTitle.setText(getString(R.string.bloodalcohol));
        EdtAgeBloodAlcohol.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnWeightBloodAlcohol:
                GotoCalculateWeight();
                break;
            case R.id.BtnResetBloodAlcohol:
                GotoCalculateReset();
                break;
            case R.id.BtnChartBloodAlcohol:
                GotoCalculateChart();
                break;
        }
    }

    private void GotoCalculateWeight() {
        RadioButton radioGenderButton = (RadioButton) findViewById(RgGender.getCheckedRadioButtonId());
        String gender = (String) radioGenderButton.getText();
        RadioButton radioBloodAlcoholWeightButton = (RadioButton) findViewById(RgWeightBloodAlcohol.getCheckedRadioButtonId());
        String BloodAlcoholWeight = (String) radioBloodAlcoholWeightButton.getText();
        RadioButton radioBloodAlcoholDrinkButton = (RadioButton) findViewById(RgDrink.getCheckedRadioButtonId());
        String BloodAlcoholDrink = (String) radioBloodAlcoholDrinkButton.getText();
        RadioButton radioBloodAlcoholTimeButton = (RadioButton) findViewById(RgTime.getCheckedRadioButtonId());
        String BloodAlcoholTime = (String) radioBloodAlcoholTimeButton.getText();
        System.out.println("-- --- --- come : ");
        try {
            try {
                DoubleAge = Double.parseDouble(EdtAgeBloodAlcohol.getText().toString());
            } catch (NumberFormatException unused) {
                BoolCheckBloodAlcohol = true;
            }
            try {
                DoubleAlcohol = Double.parseDouble(EdtDrinkBloodAlcohol.getText().toString());
            } catch (NumberFormatException unused2) {
                BoolCheckBloodAlcohol = true;
            }
            try {
                DoubleWeight = Double.parseDouble(EdtWeightBloodAlcohol.getText().toString());
            } catch (NumberFormatException unused3) {
                BoolCheckBloodAlcohol = true;
            }
            try {
                DoubleTime = Double.parseDouble(EdtTimeBloodAlcohol.getText().toString());
            } catch (NumberFormatException unused4) {
                BoolCheckBloodAlcohol = true;
            }
            if (BoolCheckBloodAlcohol) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                BoolCheckBloodAlcohol = false;
                return;
            }
            if (BloodAlcoholWeight.equalsIgnoreCase(getString(R.string.kilograms))) {
                DoubleWeight *= 2.20462d;
            }
            if (DoubleNewAlcohol != 1) {
                if (DoubleNewAlcohol == 2) {
                    DoubleAlcohol *= 0.033814d;
                } else {
                    DoubleAlcohol *= 8.0d;
                }
            }
            DoubleAge /= 100.0d;
            DoubleAlcohol *= DoubleAge;
            if (DoubleBloodAlcohol != 1) {
                if (DoubleBloodAlcohol == 2) {
                    DoubleTime *= 0.0166d;
                } else {
                    DoubleTime *= 24.0d;
                }
            }
            DoubleWeight = 5.14d / DoubleWeight;
            DoubleTime *= 0.015d;
            if (gender.equalsIgnoreCase(getString(R.string.male))) {
                calculateAlcohol = ((DoubleAlcohol * DoubleWeight) * 0.73d) - DoubleTime;
            } else {
                calculateAlcohol = ((DoubleAlcohol * DoubleWeight) * 0.66d) - DoubleTime;
            }
            calculate_BloodAlcohol = NumberFormat.getInstance().format(calculateAlcohol);

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
            LinearLayout LlBloodAlcohol = dialog.findViewById(R.id.LlBloodAlcohol);
            TextView TvDialogBloodAlcohol = dialog.findViewById(R.id.TvDialogBloodAlcohol);
            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            Button BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);

            LlBloodAlcohol.setVisibility(View.VISIBLE);
            TvDialogWeightSubTitle.setText(getString(R.string.urbloodalcohol));
            TvDialogBloodAlcohol.setText(calculate_BloodAlcohol);

            BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());

            IvWeightClose.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        } catch (Resources.NotFoundException e2) {
            System.out.println("----- -- - - e22 come : " + e2.getMessage());
            e2.printStackTrace();
        }
    }

    private void GotoCalculateReset() {
        EdtAgeBloodAlcohol.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
        EdtWeightBloodAlcohol.setText("");
        EdtDrinkBloodAlcohol.setText("");
        EdtTimeBloodAlcohol.setText("");
        EdtAgeBloodAlcohol.requestFocus();

        RadioButton radioGenderButton = (RadioButton) RgGender.getChildAt(0);
        radioGenderButton.setChecked(true);
        RadioButton radioBloodAlcoholWeightButton = (RadioButton) RgWeightBloodAlcohol.getChildAt(0);
        radioBloodAlcoholWeightButton.setChecked(true);
        RadioButton radioBloodAlcoholWaistButton = (RadioButton) RgDrink.getChildAt(0);
        radioBloodAlcoholWaistButton.setChecked(true);
        RadioButton radioBloodAlcoholNeckButton = (RadioButton) RgTime.getChildAt(0);
        radioBloodAlcoholNeckButton.setChecked(true);
    }

    private void GotoCalculateChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.bloodalcohol)));
    }

}