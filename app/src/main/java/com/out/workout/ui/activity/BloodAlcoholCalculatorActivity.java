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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdSize;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.R;
import com.out.workout.ui.adapter.SpinnerAdapters;
import com.out.workout.utils.Constants;
import com.out.workout.utils.SharePreference;

import java.text.NumberFormat;

public class BloodAlcoholCalculatorActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Context context;
    private ImageView IvBack, BtnChartBloodAlcohol;
    private TextView TvTitle;
    private EditText EdtAgeBloodAlcohol;
    private Spinner SpinnerGenderBloodAlcohol, SpinnerDrinkBloodAlcohol, SpinnerTimeBloodAlcohol, SpinnerWeightBloodAlcohol;
    private EditText EdtWeightBloodAlcohol, EdtDrinkBloodAlcohol, EdtTimeBloodAlcohol;
    private TextView BtnWeightBloodAlcohol, BtnResetBloodAlcohol;
    private double DoubleWeight, DoubleAge, DoubleTime, DoubleAlcohol, DoubleBloodAlcohol = 0, DoubleNewAlcohol = 0, calculateAlcohol;
    private boolean BoolCheckBloodAlcohol;
    private String calculate_BloodAlcohol;
    private boolean StrMale = true, StrKg = true;

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
        EdtDrinkBloodAlcohol = (EditText) findViewById(R.id.EdtDrinkBloodAlcohol);
        EdtTimeBloodAlcohol = (EditText) findViewById(R.id.EdtTimeBloodAlcohol);
        EdtWeightBloodAlcohol = (EditText) findViewById(R.id.EdtWeightBloodAlcohol);
        SpinnerGenderBloodAlcohol = (Spinner) findViewById(R.id.SpinnerGenderBloodAlcohol);
        SpinnerDrinkBloodAlcohol = (Spinner) findViewById(R.id.SpinnerDrinkBloodAlcohol);
        SpinnerTimeBloodAlcohol = (Spinner) findViewById(R.id.SpinnerTimeBloodAlcohol);
        SpinnerWeightBloodAlcohol = (Spinner) findViewById(R.id.SpinnerWeightBloodAlcohol);
        BtnWeightBloodAlcohol = (TextView) findViewById(R.id.BtnWeightBloodAlcohol);
        BtnResetBloodAlcohol = (TextView) findViewById(R.id.BtnResetBloodAlcohol);
        BtnChartBloodAlcohol = (ImageView) findViewById(R.id.BtnChartBloodAlcohol);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnWeightBloodAlcohol.setOnClickListener(this);
        BtnResetBloodAlcohol.setOnClickListener(this);
        BtnChartBloodAlcohol.setOnClickListener(this);
        SpinnerGenderBloodAlcohol.setOnItemSelectedListener(this);
        SpinnerDrinkBloodAlcohol.setOnItemSelectedListener(this);
        SpinnerTimeBloodAlcohol.setOnItemSelectedListener(this);
        SpinnerWeightBloodAlcohol.setOnItemSelectedListener(this);
    }

    private void initActions() {
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

        TvTitle.setText(getString(R.string.bloodalcohol));
        EdtAgeBloodAlcohol.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
        String[] GenderArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] WeightArr = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};
        String[] TimeArr = {getResources().getString(R.string.hour), getResources().getString(R.string.minute), getResources().getString(R.string.day)};
        String[] DrinkArr = {getResources().getString(R.string.ounces), getResources().getString(R.string.ml), getResources().getString(R.string.cup)};
        SpinnerGenderBloodAlcohol.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, GenderArr));
        SpinnerDrinkBloodAlcohol.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, DrinkArr));
        SpinnerWeightBloodAlcohol.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, WeightArr));
        SpinnerTimeBloodAlcohol.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, TimeArr));
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
        if (SpinnerDrinkBloodAlcohol.getSelectedItem().toString().equals(getResources().getString(R.string.ounces))) {
            DoubleBloodAlcohol = 1;
        } else if (SpinnerDrinkBloodAlcohol.getSelectedItem().toString().equals(getResources().getString(R.string.ml))) {
            DoubleBloodAlcohol = 2;
        } else {
            DoubleBloodAlcohol = 3;
        }
        if (SpinnerTimeBloodAlcohol.getSelectedItem().toString().equals(getResources().getString(R.string.hour))) {
            DoubleNewAlcohol = 1;
        } else if (SpinnerTimeBloodAlcohol.getSelectedItem().toString().equals(getResources().getString(R.string.minute))) {
            DoubleNewAlcohol = 2;
        } else {
            DoubleNewAlcohol = 3;
        }
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
            if (SpinnerWeightBloodAlcohol.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.kilograms))) {
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
            if (SpinnerGenderBloodAlcohol.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.male))) {
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

            LinearLayout LlBloodAlcohol = dialog.findViewById(R.id.LlBloodAlcohol);
            TextView TvDialogBloodAlcohol = dialog.findViewById(R.id.TvDialogBloodAlcohol);
            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            TextView BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);


            ImageView IvDialogBanner = dialog.findViewById(R.id.IvDialogBanner);
            TextView TvDialogName = dialog.findViewById(R.id.TvDialogName);
            TextView TvDialogDesc = dialog.findViewById(R.id.TvDialogDesc);

            IvDialogBanner.setImageResource(R.drawable.ic_blood_alcohol);
            TvDialogName.setText(getResources().getString(R.string.bloodalcohol));
            TvDialogDesc.setText(getResources().getString(R.string.bloodalcohol_desc));

            LlBloodAlcohol.setVisibility(View.VISIBLE);
            TvDialogWeightSubTitle.setText(getString(R.string.urbloodalcohol));
            TvDialogBloodAlcohol.setText(calculate_BloodAlcohol);

            BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());

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


        SpinnerGenderBloodAlcohol.setSelection(0);
        SpinnerDrinkBloodAlcohol.setSelection(0);
        SpinnerTimeBloodAlcohol.setSelection(0);
        SpinnerWeightBloodAlcohol.setSelection(0);
    }

    private void GotoCalculateChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.bloodalcohol)));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.SpinnerGenderBloodAlcohol:
                StrMale = SpinnerGenderBloodAlcohol.getSelectedItem().toString().equals(getResources().getString(R.string.male));
                break;
            case R.id.SpinnerDrinkBloodAlcohol:
                String weight = SpinnerDrinkBloodAlcohol.getSelectedItem().toString();
                if (weight.equals(getResources().getString(R.string.ounces))) {
                    DoubleBloodAlcohol = 1;
                } else if (weight.equals(getResources().getString(R.string.ml))) {
                    DoubleBloodAlcohol = 2;
                } else {
                    DoubleBloodAlcohol = 3;
                }
                break;
            case R.id.SpinnerTimeBloodAlcohol:
                StrKg = SpinnerTimeBloodAlcohol.getSelectedItem().toString().equals(getResources().getString(R.string.kilograms));
                break;
            case R.id.SpinnerWeightBloodAlcohol:
                String time = (String) SpinnerWeightBloodAlcohol.getSelectedItem().toString();
                if (time.equals(getResources().getString(R.string.hour))) {
                    DoubleNewAlcohol = 1;
                } else if (time.equals(getResources().getString(R.string.minute))) {
                    DoubleNewAlcohol = 2;
                } else {
                    DoubleNewAlcohol = 3;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}