package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

public class WeightCalculatorActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle,TvFTOrCMIdealWeight;
    private Spinner SpinnerGenderIdealWeight, SpinnerHeightIdealWeight;
    private EditText EdtAgeCalculator;
    private EditText EdtHeightCalculator, EdtInchCalculator;
    private LinearLayout LLHeightCalculator;
    private TextView BtnWeightCalculator;
    private TextView BtnResetCalculator;
    private ImageView BtnChartCalculator;
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
        TvFTOrCMIdealWeight = (TextView) findViewById(R.id.TvFTOrCMIdealWeight);
        EdtAgeCalculator = (EditText) findViewById(R.id.EdtAgeCalculator);
        SpinnerGenderIdealWeight = (Spinner) findViewById(R.id.SpinnerGenderIdealWeight);
        SpinnerHeightIdealWeight = (Spinner) findViewById(R.id.SpinnerHeightIdealWeight);

        EdtHeightCalculator = (EditText) findViewById(R.id.EdtHeightCalculator);
        LLHeightCalculator = (LinearLayout) findViewById(R.id.LLHeightCalculator);
        EdtInchCalculator = (EditText) findViewById(R.id.EdtInchCalculator);
        BtnWeightCalculator = (TextView) findViewById(R.id.BtnWeightCalculator);
        BtnResetCalculator = (TextView) findViewById(R.id.BtnResetCalculator);
        BtnChartCalculator = (ImageView) findViewById(R.id.BtnChartCalculator);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnWeightCalculator.setOnClickListener(this);
        BtnResetCalculator.setOnClickListener(this);
        BtnChartCalculator.setOnClickListener(this);
        SpinnerHeightIdealWeight.setOnItemSelectedListener(this);
    }

    private void initActions() {
        TvTitle.setText(getString(R.string.idealweight));
        TvFTOrCMIdealWeight.setText(getString(R.string.cm));
        LLHeightCalculator.setVisibility(View.GONE);
        EdtAgeCalculator.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
        String[] GenderArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] HeightArr = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};
        SpinnerGenderIdealWeight.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, GenderArr));
        SpinnerHeightIdealWeight.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, HeightArr));

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
        String gender = (String) SpinnerGenderIdealWeight.getSelectedItem().toString();
        String weight = (String) SpinnerHeightIdealWeight.getSelectedItem().toString();
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

            if (weight.equalsIgnoreCase(getString(R.string.centimeters))) {
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


            CardView CardIdealWeight = dialog.findViewById(R.id.CardIdealWeight);
            TextView TvDialogWeightValue = dialog.findViewById(R.id.TvDialogWeightValue);
            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            TextView BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);

            ImageView IvDialogBanner = dialog.findViewById(R.id.IvDialogBanner);
            TextView TvDialogName = dialog.findViewById(R.id.TvDialogName);
            TextView TvDialogDesc = dialog.findViewById(R.id.TvDialogDesc);

            IvDialogBanner.setImageResource(R.drawable.ic_ideal_weight);
            TvDialogName.setText(getResources().getString(R.string.idealweight));
            TvDialogDesc.setText(getResources().getString(R.string.idealweight_desc));

            CardIdealWeight.setVisibility(View.VISIBLE);
            TvDialogWeightSubTitle.setText(getString(R.string.uridealweight));
            BtnDialogWeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            TvDialogWeightValue.setText(calculate_Kg + "Kg / " + calculate_lbs+"lbs");


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

        TvFTOrCMIdealWeight.setText(getString(R.string.cm));
        SpinnerGenderIdealWeight.setSelection(0);
        SpinnerHeightIdealWeight.setSelection(0);
        LLHeightCalculator.setVisibility(View.GONE);
    }

    private void GotoCalculateChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.idealweight)));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println("------- VALLL : "+adapterView.getId()+" - "+R.id.SpinnerHeightIdealWeight);
        switch (adapterView.getId()) {
            case R.id.SpinnerHeightIdealWeight:
                String weight = (String) SpinnerHeightIdealWeight.getSelectedItem().toString();
                if (weight.equalsIgnoreCase(getString(R.string.centimeters))) {
                    EdtHeightCalculator.setText("");
                    EdtInchCalculator.setText("");
                    TvFTOrCMIdealWeight.setText(getString(R.string.cm));
                    LLHeightCalculator.setVisibility(View.GONE);
                } else {
                    TvFTOrCMIdealWeight.setText(getString(R.string.ft));
                    EdtHeightCalculator.setText("");
                    EdtInchCalculator.setText("");
                    LLHeightCalculator.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}