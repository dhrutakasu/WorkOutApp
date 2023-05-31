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

public class CalorieCalculatorActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private EditText EdtAgeCalorie;
    private EditText EdtHeightCalorie, EdtInchCalorie, EdtWeightCalorie;
    private LinearLayout LLHeightCalorie;
    private TextView BtnWeightCalorie, TvFTOrCMCalorie;
    private TextView BtnResetCalorie;
    private ImageView BtnChartCalorie;
    private double DoubleHeight, DoubleWeight, DoubleAge, DoubleInch, DoubleCalorie, DoubleBMR;
    private boolean check;
    private Spinner SpinnerCalorie, SpinnerGenderCalorie, SpinnerHeightCalorie, SpinnerWeightCalorie;
    private String calculate_BMR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtAgeCalorie = (EditText) findViewById(R.id.EdtAgeCalorie);
        EdtHeightCalorie = (EditText) findViewById(R.id.EdtHeightCalorie);
        LLHeightCalorie = (LinearLayout) findViewById(R.id.LLHeightCalorie);
        EdtInchCalorie = (EditText) findViewById(R.id.EdtInchCalorie);
        EdtWeightCalorie = (EditText) findViewById(R.id.EdtWeightCalorie);
        TvFTOrCMCalorie = (TextView) findViewById(R.id.TvFTOrCMCalorie);
        BtnWeightCalorie = (TextView) findViewById(R.id.BtnWeightCalorie);
        BtnResetCalorie = (TextView) findViewById(R.id.BtnResetCalorie);
        BtnChartCalorie = (ImageView) findViewById(R.id.BtnChartCalorie);
        SpinnerCalorie = (Spinner) findViewById(R.id.SpinnerCalorie);
        SpinnerGenderCalorie = (Spinner) findViewById(R.id.SpinnerGenderCalorie);
        SpinnerHeightCalorie = (Spinner) findViewById(R.id.SpinnerHeightCalorie);
        SpinnerWeightCalorie = (Spinner) findViewById(R.id.SpinnerWeightCalorie);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnWeightCalorie.setOnClickListener(this);
        BtnResetCalorie.setOnClickListener(this);
        BtnChartCalorie.setOnClickListener(this);
        SpinnerWeightCalorie.setOnItemSelectedListener(this);
        SpinnerCalorie.setOnItemSelectedListener(this);
    }

    private void initActions() {
        TvTitle.setText(getString(R.string.calories));
        TvFTOrCMCalorie.setText(getString(R.string.cm));
        LLHeightCalorie.setVisibility(View.GONE);
        EdtAgeCalorie.setText(String.valueOf(SharePreference.getCalculatorAge(context)));

        String[] GenderArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] WeightArr = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};
        String[] HeightArr = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};
        String[] ArrCalorie = {getResources().getString(R.string.sedentary), getResources().getString(R.string.lightly_active), getResources().getString(R.string.moderately_active), getResources().getString(R.string.very_active), getResources().getString(R.string.extremely_active)};
        SpinnerCalorie.setAdapter((SpinnerAdapter) new SpinnerAdapters(this, R.layout.item_spinner, ArrCalorie));
        SpinnerGenderCalorie.setAdapter((SpinnerAdapter) new SpinnerAdapters(this, R.layout.item_spinner, GenderArr));
        SpinnerHeightCalorie.setAdapter((SpinnerAdapter) new SpinnerAdapters(this, R.layout.item_spinner, HeightArr));
        SpinnerWeightCalorie.setAdapter((SpinnerAdapter) new SpinnerAdapters(this, R.layout.item_spinner, WeightArr));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnWeightCalorie:
                GotoCalculateWeight();
                break;
            case R.id.BtnResetCalorie:
                GotoCalculateReset();
                break;
            case R.id.BtnChartCalorie:
                GotoCalculateChart();
                break;
        }
    }

    private void GotoCalculateWeight() {
        String gender = (String) SpinnerGenderCalorie.getSelectedItem().toString();
        String weight = (String) SpinnerHeightCalorie.getSelectedItem().toString();
        String CalorieWeight = (String) SpinnerWeightCalorie.getSelectedItem().toString();
        System.out.println("-- --- --- come : ");
        try {
            try {
                DoubleHeight = Double.parseDouble(EdtHeightCalorie.getText().toString());
            } catch (NumberFormatException unused) {
                check = true;
            }
            try {
                DoubleWeight = Double.parseDouble(EdtWeightCalorie.getText().toString());
            } catch (NumberFormatException unused2) {
                check = true;
            }
            try {
                DoubleAge = Double.parseDouble(EdtAgeCalorie.getText().toString());
            } catch (NumberFormatException unused3) {
                check = true;
            }
            try {
                DoubleInch = Double.parseDouble(EdtInchCalorie.getText().toString());
            } catch (NumberFormatException unused4) {
                DoubleInch = 0.0d;
            }
            if (check) {
                Toast.makeText(context, getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                check = false;
                return;
            }
            if (!weight.equalsIgnoreCase(getString(R.string.centimeters))) {
                DoubleHeight *= 12.0d;
                DoubleHeight += DoubleInch;
                DoubleHeight *= 2.54d;
            }
            if (!CalorieWeight.equalsIgnoreCase(getString(R.string.kilograms))) {
                DoubleWeight *= 0.453592d;
            }
            if (gender.equalsIgnoreCase(getString(R.string.male))) {
                DoubleWeight *= 13.7d;
                DoubleWeight += 66.0d;
                DoubleHeight *= 5.0d;
                DoubleAge *= 6.8d;
            } else {
                DoubleWeight *= 9.6d;
                DoubleWeight += 655.0d;
                DoubleHeight *= 1.8d;
                DoubleAge *= 4.7d;
            }
            try {
                SharePreference.setCalculatorAge(context, Integer.parseInt(EdtAgeCalorie.getText().toString()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            DoubleBMR = (DoubleWeight + DoubleHeight) - DoubleAge;
            DoubleBMR *= DoubleCalorie;
            calculate_BMR = NumberFormat.getInstance().format(DoubleBMR);
            System.out.println("--- -- callllllll : " + calculate_BMR);

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
            LinearLayout LlCalorie = dialog.findViewById(R.id.LlCalorie);
            TextView TvDialogCalorie = dialog.findViewById(R.id.TvDialogCalorie);
            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            Button BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);

            LlCalorie.setVisibility(View.VISIBLE);
            TvDialogWeightSubTitle.setText(getString(R.string.urcal));
            TvDialogCalorie.setText(calculate_BMR);

            BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());

            IvWeightClose.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        } catch (Resources.NotFoundException e2) {
            System.out.println("----- -- - - e22 come : " + e2.getMessage());
            e2.printStackTrace();
        }
    }

    private void GotoCalculateReset() {
        EdtHeightCalorie.setText("");
        EdtAgeCalorie.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
        EdtInchCalorie.setText("");
        EdtWeightCalorie.setText("");
        EdtAgeCalorie.requestFocus();

        SpinnerCalorie.setSelection(0);
        SpinnerGenderCalorie.setSelection(0);
        SpinnerHeightCalorie.setSelection(0);
        SpinnerWeightCalorie.setSelection(0);
        TvFTOrCMCalorie.setText(getString(R.string.cm));
        LLHeightCalorie.setVisibility(View.GONE);
    }

    private void GotoCalculateChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.caloriesval)));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (view.getId()) {
            case R.id.SpinnerCalorie:
                String obj = SpinnerCalorie.getSelectedItem().toString();
                if (obj.equals(getResources().getString(R.string.sedentary))) {
                    DoubleCalorie = 1.2d;
                } else if (obj.equals(getResources().getString(R.string.lightly_active))) {
                    DoubleCalorie = 1.375d;
                } else if (obj.equals(getResources().getString(R.string.moderately_active))) {
                    DoubleCalorie = 1.55d;
                } else if (obj.equals(getResources().getString(R.string.very_active))) {
                    DoubleCalorie = 1.725d;
                } else {
                    DoubleCalorie = 1.9d;
                }
                break;
            case R.id.SpinnerWeightCalorie:
                String weight = SpinnerWeightCalorie.getSelectedItem().toString();
                if (weight.equalsIgnoreCase(getString(R.string.centimeters))) {
                    TvFTOrCMCalorie.setText(getString(R.string.cm));
                    EdtHeightCalorie.setText("");
                    EdtInchCalorie.setText("");
                    LLHeightCalorie.setVisibility(View.GONE);
                } else {
                    TvFTOrCMCalorie.setText(getString(R.string.ft));
                    EdtHeightCalorie.setText("");
                    EdtInchCalorie.setText("");
                    LLHeightCalorie.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}