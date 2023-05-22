package com.out.workout.ui.activity;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.out.workout.R;
import com.out.workout.ui.adapter.SpinnerAdapters;
import com.out.workout.utils.Constants;
import com.out.workout.utils.SharePreference;

import java.text.NumberFormat;

import androidx.appcompat.app.AppCompatActivity;

public class HeartRateCalculatorActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private EditText EdtAgeHeart, EdtHeartRate;
    private RadioGroup RgGender;
    private RadioButton RbMale, RbFemale;
    private Spinner SpinnerHeartRate;
    private Button BtnHeartRate, BtnResetHeart, BtnChartHeart;
    private double StrRate, StrRateSec;
    private double DoubleRate, DoubleAge;

    private double DoubleRatemax, DoubleRateMhr, DoubleRatemin;
    private boolean BoolCheck;
    private String str_Rate, str_RateMin, str_RateMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtAgeHeart = (EditText) findViewById(R.id.EdtAgeHeart);
        RgGender = (RadioGroup) findViewById(R.id.RgGender);
        RbMale = (RadioButton) findViewById(R.id.RbMale);
        RbFemale = (RadioButton) findViewById(R.id.RbFemale);
        EdtHeartRate = (EditText) findViewById(R.id.EdtHeartRate);
        SpinnerHeartRate = (Spinner) findViewById(R.id.SpinnerHeartRate);
        BtnHeartRate = (Button) findViewById(R.id.BtnHeartRate);
        BtnResetHeart = (Button) findViewById(R.id.BtnResetHeart);
        BtnChartHeart = (Button) findViewById(R.id.BtnChartHeart);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnHeartRate.setOnClickListener(this);
        BtnResetHeart.setOnClickListener(this);
        BtnChartHeart.setOnClickListener(this);

        String[] ArrHeartRate = {getResources().getString(R.string.moderate), getResources().getString(R.string.little_diff), getResources().getString(R.string.moderately_diff), getResources().getString(R.string.hard)};
        SpinnerHeartRate.setAdapter((SpinnerAdapter) new SpinnerAdapters(this, R.layout.layout_spinner, ArrHeartRate));
        SpinnerHeartRate.setOnItemSelectedListener(this);
    }

    private void initActions() {
        TvTitle.setText(getString(R.string.heartrate));
        EdtAgeHeart.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnHeartRate:
                GotoCalculateHeartRate();
                break;
            case R.id.BtnResetHeart:
                GotoCalculateReset();
                break;
            case R.id.BtnChartHeart:
                GotoCalculateChart();
                break;
        }
    }

    private void GotoCalculateHeartRate() {
        RadioButton radioGenderButton = (RadioButton) findViewById(RgGender.getCheckedRadioButtonId());
        String gender = (String) radioGenderButton.getText();
        System.out.println("-- --- --- come : ");
        try {
            try {
                DoubleAge = Double.parseDouble(EdtAgeHeart.getText().toString());
            } catch (NumberFormatException unused) {
                BoolCheck = true;
            }
            try {
                DoubleRate = Double.parseDouble(EdtHeartRate.getText().toString());
            } catch (NumberFormatException unused2) {
                BoolCheck = true;
            }
            if (BoolCheck) {
                System.out.println("----- -- - - e22 BoolCheck : " + BoolCheck);
                Toast.makeText(context, getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                BoolCheck = false;
                return;
            }
            if (gender.equalsIgnoreCase(getString(R.string.male))) {
                DoubleAge *= 0.8d;
                DoubleRateMhr = 214.0d - DoubleAge;
            } else {
                DoubleAge *= 0.9d;
                DoubleRateMhr = 209.0d - DoubleAge;
            }
            try {
                SharePreference.setCalculatorAge(context, Integer.parseInt(EdtAgeHeart.getText().toString()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            str_Rate = NumberFormat.getInstance().format(DoubleRateMhr);
            DoubleRateMhr -= DoubleRate;
            DoubleRatemin = (DoubleRateMhr * StrRate) + DoubleRate;
            DoubleRatemax = (DoubleRateMhr * StrRateSec) + DoubleRate;
            str_RateMin = NumberFormat.getInstance().format(DoubleRatemin);
            str_RateMax = NumberFormat.getInstance().format(DoubleRatemax);

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
            LinearLayout LlHeartRate = dialog.findViewById(R.id.LlHeartRate);
            TextView TvDialogMaxRateValue = dialog.findViewById(R.id.TvDialogMaxRateValue);
            TextView TvDialogTrainingRateValue = dialog.findViewById(R.id.TvDialogTrainingRateValue);
            Button BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);

            LlHeartRate.setVisibility(View.VISIBLE);

            TvDialogMaxRateValue.setText(str_Rate);
            TvDialogTrainingRateValue.setText(str_RateMin + " - " + str_RateMax);
            TvDialogWeightSubTitle.setText(getString(R.string.maxhrrate));

            BtnDialogWeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            IvWeightClose.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        } catch (Resources.NotFoundException e2) {
            System.out.println("----- -- - - e22 come : " + e2.getMessage());
            e2.printStackTrace();
        }
    }

    private void GotoCalculateReset() {
        EdtHeartRate.setText("");
        EdtAgeHeart.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
        EdtAgeHeart.requestFocus();

        RadioButton radioGenderButton = (RadioButton) RgGender.getChildAt(0);
        radioGenderButton.setChecked(true);
        SpinnerHeartRate.setSelection(0);
    }

    private void GotoCalculateChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.heartrate)));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String toString = SpinnerHeartRate.getSelectedItem().toString();
        if (toString.equals(getResources().getString(R.string.moderate))) {
            StrRate = 0.6d;
            StrRateSec = 0.65d;
        } else if (toString.equals(getResources().getString(R.string.little_diff))) {
            StrRate = 0.65d;
            StrRateSec = 0.7d;
        } else if (toString.equals(getResources().getString(R.string.moderately_diff))) {
            StrRate = 0.7d;
            StrRateSec = 0.75d;
        } else {
            StrRate = 0.75d;
            StrRateSec = 0.8d;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}