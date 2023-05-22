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

public class WeightCalculatorActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private EditText EdtAgeCalculator;
    private RadioGroup RgGender, RgWeight;
    private RadioButton RbMale, RbFemale, RbCm, RbInch;
    private EditText EdtHeightCalculator, EdtInchCalculator;
    private LinearLayout LLHeightCalculator;
    private Button BtnWeightCalculator, BtnResetCalculator, BtnChartCalculator;
    private double DoubleHeight, DoubleAge, DoubleInch;
    private boolean check;
    private double calculate;
    private String calculate_Kg, calculate_lbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_calculator);

        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtAgeCalculator = (EditText) findViewById(R.id.EdtAgeCalculator);
        RgGender = (RadioGroup) findViewById(R.id.RgGender);
        RbMale = (RadioButton) findViewById(R.id.RbMale);
        RbFemale = (RadioButton) findViewById(R.id.RbFemale);
        RgWeight = (RadioGroup) findViewById(R.id.RgWeight);
        RbCm = (RadioButton) findViewById(R.id.RbCm);
        RbInch = (RadioButton) findViewById(R.id.RbInch);
        EdtHeightCalculator = (EditText) findViewById(R.id.EdtHeightCalculator);
        LLHeightCalculator = (LinearLayout) findViewById(R.id.LLHeightCalculator);
        EdtInchCalculator = (EditText) findViewById(R.id.EdtInchCalculator);
        BtnWeightCalculator = (Button) findViewById(R.id.BtnWeightCalculator);
        BtnResetCalculator = (Button) findViewById(R.id.BtnResetCalculator);
        BtnChartCalculator = (Button) findViewById(R.id.BtnChartCalculator);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnWeightCalculator.setOnClickListener(this);
        BtnResetCalculator.setOnClickListener(this);
        BtnChartCalculator.setOnClickListener(this);
        RgWeight.setOnCheckedChangeListener(this);
    }

    private void initActions() {
        TvTitle.setText(getString(R.string.idealweight));
        LLHeightCalculator.setVisibility(View.GONE);
        EdtAgeCalculator.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnWeightCalculator:
                GotoCalculateWeight();
                break;
            case R.id.BtnResetCalculator:
                GotoCalculateReset();
                break;
            case R.id.BtnChartCalculator:
                GotoCalculateChart();
                break;
        }
    }

    private void GotoCalculateWeight() {
        RadioButton radioGenderButton = (RadioButton) findViewById(RgGender.getCheckedRadioButtonId());
        String gender = (String) radioGenderButton.getText();
        RadioButton radioWeightButton = (RadioButton) findViewById(RgWeight.getCheckedRadioButtonId());
        String weight = (String) radioWeightButton.getText();
        System.out.println("-- --- --- come : ");
        try {
            try {
                DoubleHeight = Double.parseDouble(EdtHeightCalculator.getText().toString());
            } catch (NumberFormatException unused) {
                System.out.println("----- -- - - DoubleHeight come : " + unused.getMessage());
                check = true;
            }

            try {
                DoubleAge = Double.parseDouble(EdtAgeCalculator.getText().toString());
            } catch (NumberFormatException unused2) {
                System.out.println("----- -- - - DoubleAge come : " + unused2.getMessage());
                check = true;
            }

            try {
                DoubleInch = Double.parseDouble(EdtInchCalculator.getText().toString());
            } catch (NumberFormatException unused3) {
                DoubleInch = 0.0d;
                System.out.println("----- -- - - DoubleInch come : " + unused3.getMessage());
            }

            if (check) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                check = false;
                return;
            }

            if (weight.equalsIgnoreCase(getString(R.string.cm))) {
                if (DoubleHeight < 153.0d) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.entercm), Toast.LENGTH_SHORT).show();
                    return;
                }
                DoubleHeight *= 0.393701d;
                DoubleHeight -= 60.0d;
            } else if (DoubleHeight < 5.0d) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.enterfeet), Toast.LENGTH_SHORT).show();
                return;
            } else {
                DoubleHeight *= 12.0d;
                DoubleHeight += DoubleInch;
                DoubleHeight -= 60.0d;
            }

            if (gender.equalsIgnoreCase(getString(R.string.male))) {
                calculate = (DoubleHeight * 1.9d) + 52.0d;
            } else {
                calculate = (DoubleHeight * 1.7d) + 49.0d;
            }

            try {
                SharePreference.setCalculatorAge(getApplicationContext(), Integer.parseInt(EdtAgeCalculator.getText().toString()));
            } catch (NumberFormatException e) {
                System.out.println("----- -- - - e come : " + e.getMessage());
                e.printStackTrace();
            }

            calculate_Kg = NumberFormat.getInstance().format(calculate);
            calculate *= 2.20462d;
            calculate_lbs = NumberFormat.getInstance().format(calculate);
            System.out.println("----- -- - - calculate_lbs come : " + calculate_lbs);
            System.out.println("----- -- - - calculate_Kg come : " + calculate_Kg);
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
            TextView TvDialogWeightValue = dialog.findViewById(R.id.TvDialogWeightValue);
            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            Button BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);
            TvDialogWeightValue.setVisibility(View.VISIBLE);
            TvDialogWeightSubTitle.setText(getString(R.string.uridealweight));
            BtnDialogWeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            TvDialogWeightValue.setText(calculate_Kg + " / " + calculate_lbs);

            IvWeightClose.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        } catch (Resources.NotFoundException e2) {
            System.out.println("----- -- - - e22 come : " + e2.getMessage());
            e2.printStackTrace();
        }
    }

    private void GotoCalculateReset() {
        EdtHeightCalculator.setText("");
        EdtAgeCalculator.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
        EdtInchCalculator.setText("");
        EdtAgeCalculator.requestFocus();

        RadioButton radioGenderButton = (RadioButton) RgGender.getChildAt(0);
        radioGenderButton.setChecked(true);
        RadioButton radioWeightButton = (RadioButton) RgWeight.getChildAt(0);
        radioWeightButton.setChecked(true);
    }

    private void GotoCalculateChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.idealweight)));
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getId()) {
            case R.id.RgWeight:
                RadioButton radioWeightButton = (RadioButton) findViewById(RgWeight.getCheckedRadioButtonId());
                String weight = (String) radioWeightButton.getText();
                if (weight.equalsIgnoreCase("CM")) {
                    EdtHeightCalculator.setText("");
                    EdtInchCalculator.setText("");
                    LLHeightCalculator.setVisibility(View.GONE);
                } else {
                    EdtHeightCalculator.setText("");
                    EdtInchCalculator.setText("");
                    LLHeightCalculator.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}