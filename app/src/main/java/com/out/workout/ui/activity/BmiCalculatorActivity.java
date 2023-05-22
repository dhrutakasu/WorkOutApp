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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.out.workout.R;
import com.out.workout.utils.Constants;
import com.out.workout.utils.SharePreference;

import java.io.PrintStream;
import java.text.NumberFormat;

public class BmiCalculatorActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private EditText EdtAgeBMI;
    private RadioGroup RgGender, RgWeight, RgWeightBMI;
    private RadioButton RbMale, RbFemale, RbCm, RbInch, RbKg, RbPounds;
    private EditText EdtHeightBMI, EdtInchBMI, EdtWeightBMI;
    private LinearLayout LLHeightBMI;
    private Button BtnWeightBMI, BtnResetBMI, BtnChartBMI;
    private double DoubleHeight, DoubleWeight, DoubleAge, DoubleInch;
    private boolean check;
    private double calculate;
    private String calculate_Kg, calculate_BMI;
    private int calculate_BMI_Int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator_activtiy);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtAgeBMI = (EditText) findViewById(R.id.EdtAgeBMI);
        RgGender = (RadioGroup) findViewById(R.id.RgGender);
        RbMale = (RadioButton) findViewById(R.id.RbMale);
        RbFemale = (RadioButton) findViewById(R.id.RbFemale);
        RgWeight = (RadioGroup) findViewById(R.id.RgWeight);
        RbCm = (RadioButton) findViewById(R.id.RbCm);
        RbInch = (RadioButton) findViewById(R.id.RbInch);
        RgWeightBMI = (RadioGroup) findViewById(R.id.RgWeightBMI);
        RbKg = (RadioButton) findViewById(R.id.RbKg);
        RbPounds = (RadioButton) findViewById(R.id.RbPounds);
        EdtHeightBMI = (EditText) findViewById(R.id.EdtHeightBMI);
        LLHeightBMI = (LinearLayout) findViewById(R.id.LLHeightBMI);
        EdtInchBMI = (EditText) findViewById(R.id.EdtInchBMI);
        EdtWeightBMI = (EditText) findViewById(R.id.EdtWeightBMI);
        BtnWeightBMI = (Button) findViewById(R.id.BtnWeightBMI);
        BtnResetBMI = (Button) findViewById(R.id.BtnResetBMI);
        BtnChartBMI = (Button) findViewById(R.id.BtnChartBMI);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnWeightBMI.setOnClickListener(this);
        BtnResetBMI.setOnClickListener(this);
        BtnChartBMI.setOnClickListener(this);
        RgWeight.setOnCheckedChangeListener(this);
    }

    private void initActions() {
        TvTitle.setText(getString(R.string.bmi_title));
        LLHeightBMI.setVisibility(View.GONE);
        EdtAgeBMI.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnWeightBMI:
                GotoCalculateBMI();
                break;
            case R.id.BtnResetBMI:
                GotoBMIReset();
                break;
            case R.id.BtnChartBMI:
                GotoBMIChart();
                break;
        }
    }

    private void GotoCalculateBMI() {
        RadioButton radioGenderButton = (RadioButton) findViewById(RgGender.getCheckedRadioButtonId());
        String gender = (String) radioGenderButton.getText();
        RadioButton radioWeightButton = (RadioButton) findViewById(RgWeight.getCheckedRadioButtonId());
        String weight = (String) radioWeightButton.getText();
        RadioButton radioBMIWeightButton = (RadioButton) findViewById(RgWeightBMI.getCheckedRadioButtonId());
        String weightBMI = (String) radioBMIWeightButton.getText();
        try {
            try {
                DoubleHeight = Double.parseDouble(EdtHeightBMI.getText().toString());
            } catch (NumberFormatException unused) {
                check = true;
            }
            try {
                DoubleWeight = Double.parseDouble(EdtWeightBMI.getText().toString());
            } catch (NumberFormatException unused2) {
                check = true;
            }
            try {
                DoubleAge = Double.parseDouble(EdtAgeBMI.getText().toString());
            } catch (NumberFormatException unused3) {
                check = true;
            }
            try {
                DoubleInch = Double.parseDouble(EdtInchBMI.getText().toString());
            } catch (NumberFormatException unused4) {
                DoubleInch = 0.0d;
            }
            if (check) {
                Toast.makeText(context, getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                check = false;
                return;
            }
            if (weight.equalsIgnoreCase(getString(R.string.cm))) {
                DoubleHeight /= 100.0d;
            } else {
                DoubleHeight *= 12.0d;
                DoubleHeight += DoubleInch;
                DoubleHeight *= 0.0254d;
            }
            if (!weightBMI.equalsIgnoreCase(getString(R.string.kilograms))) {
                DoubleWeight *= 0.453592d;
            }
            try {
                SharePreference.setCalculatorAge(context, Integer.parseInt(EdtAgeBMI.getText().toString()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            calculate = DoubleWeight / (DoubleHeight * DoubleHeight);
            calculate_BMI = NumberFormat.getInstance().format(calculate);
            System.out.println("-------- BMI : " + calculate + " - - - " + calculate_BMI);
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
            ImageView IvWeightWomen = dialog.findViewById(R.id.IvWeightWomen);
            TextView TvDialogWeightValue = dialog.findViewById(R.id.TvDialogWeightValue);
            TextView TvDialogWeightBMIValue = dialog.findViewById(R.id.TvDialogWeightBMIValue);
            Button BtnDialogResult = dialog.findViewById(R.id.BtnDialogResult);
            ProgressBar PbBMI = dialog.findViewById(R.id.PbBMI);
            TvDialogWeightValue.setVisibility(View.VISIBLE);
            PbBMI.setVisibility(View.VISIBLE);
            IvWeightWomen.setVisibility(View.VISIBLE);
            TvDialogWeightBMIValue.setVisibility(View.VISIBLE);
            BtnDialogResult.setVisibility(View.VISIBLE);
            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            Button BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);
            TvDialogWeightSubTitle.setText(getString(R.string.bmiis));

            TvDialogWeightBMIValue.setText(calculate_BMI);
            try {
                int BMI_Int = (int) Double.valueOf(calculate_BMI).doubleValue();
                calculate_BMI_Int = BMI_Int;
                if (BMI_Int < 16) {
                    calculate_BMI_Int = 1;
                    TvDialogWeightValue.setText(getResources().getString(R.string.sixteenmin) + " " + getResources().getString(R.string.exunder));
                } else if (BMI_Int > 40) {
                    calculate_BMI_Int = 100;
                    TvDialogWeightValue.setText(getResources().getString(R.string.sixteenmin) + " " + getResources().getString(R.string.morbid));
                } else {
                    if (calculate >= 16.0d && calculate <= 18.5d) {
                        TvDialogWeightValue.setText(getResources().getString(R.string.sixteenmin) + " " + getResources().getString(R.string.underweight));
                    } else if (calculate > 18.5d && calculate <= 25.0d) {
                        TvDialogWeightValue.setText(getResources().getString(R.string.sixteenmin) + " " + getResources().getString(R.string.normalweight));
                    } else if (calculate > 25.0d && calculate <= 30.0d) {
                        TvDialogWeightValue.setText(getResources().getString(R.string.sixteenmin) + " " + getResources().getString(R.string.overweight));
                    } else if (calculate > 30.0d && calculate <= 35.0d) {
                        TvDialogWeightValue.setText(getResources().getString(R.string.sixteenmin) + " " + getResources().getString(R.string.obeseone));
                    } else if (calculate > 35.0d && calculate <= 40.0d) {
                        TvDialogWeightValue.setText(getResources().getString(R.string.sixteenmin) + " " + getResources().getString(R.string.obesetwo));
                    } else if (calculate < 16.0d) {
                        TvDialogWeightValue.setText(getResources().getString(R.string.sixteenmin) + " " + getResources().getString(R.string.exunder));
                    } else if (calculate > 40.0d) {
                        TvDialogWeightValue.setText(getResources().getString(R.string.sixteenmin) + " " + getResources().getString(R.string.morbid));
                    }
                    calculate_BMI_Int = (calculate_BMI_Int - 15) * 4;
                }
            } catch (NumberFormatException e) {
                System.out.println("Could not parse " + e);
                calculate_BMI_Int = 100;
                TvDialogWeightValue.setText(getResources().getString(R.string.sixteenmin) + " " + getResources().getString(R.string.morbid));
            }
            PbBMI.setProgress(calculate_BMI_Int);
            BtnDialogWeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            BtnDialogResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, BMIUnderstandResultActivity.class));
                }
            });

            IvWeightClose.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
        }
    }

    private void GotoBMIReset() {
        EdtHeightBMI.setText("");
        EdtAgeBMI.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
        EdtInchBMI.setText("");
        EdtWeightBMI.setText("");
        EdtAgeBMI.requestFocus();

        RadioButton radioGenderButton = (RadioButton) RgGender.getChildAt(0);
        radioGenderButton.setChecked(true);
        RadioButton radioWeightButton = (RadioButton) RgWeight.getChildAt(0);
        radioWeightButton.setChecked(true);
        RadioButton radioBMIWeightButton = (RadioButton) RgWeightBMI.getChildAt(0);
        radioBMIWeightButton.setChecked(true);
    }

    private void GotoBMIChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.bmi_title)));
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getId()) {
            case R.id.RgWeight:
                RadioButton radioWeightButton = (RadioButton) findViewById(RgWeight.getCheckedRadioButtonId());
                String weight = (String) radioWeightButton.getText();
                if (weight.equalsIgnoreCase("CM")) {
                    EdtHeightBMI.setText("");
                    EdtInchBMI.setText("");
                    LLHeightBMI.setVisibility(View.GONE);
                } else {
                    EdtHeightBMI.setText("");
                    EdtInchBMI.setText("");
                    LLHeightBMI.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}