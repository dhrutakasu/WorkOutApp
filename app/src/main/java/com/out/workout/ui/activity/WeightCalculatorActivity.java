package com.out.workout.ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.Ads.Ad_Interstitial;
import com.out.workout.R;
import com.out.workout.ui.adapter.SpinnerAdapters;
import com.out.workout.utils.Constants;
import com.out.workout.utils.Pref;

import java.text.NumberFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

        TvTitle.setText(getString(R.string.str_idealweight));
        TvFTOrCMIdealWeight.setText(getString(R.string.str_cm));
        LLHeightCalculator.setVisibility(View.GONE);
        EdtAgeCalculator.setText(String.valueOf(new Pref(context).getInt(Pref.CALCULATOR_AGE,Pref.AGE)));
        String[] GenderArr = {getResources().getString(R.string.str_male), getResources().getString(R.string.str_female)};
        String[] HeightArr = {getResources().getString(R.string.str_centimeters), getResources().getString(R.string.str_feets)};
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
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Load Ad....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Ad_Interstitial.getInstance().showInter(WeightCalculatorActivity.this, new Ad_Interstitial.MyCallback() {
                            @Override
                            public void callbackCall() {
                                GotoCalculateWeight();
                            }
                        });
                    }
                }, 3000L);
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
                check = false;
                return;
            }

            if (weight.equalsIgnoreCase(getString(R.string.str_centimeters))) {
                if (DoubleHeight < 153.0d) {
                    return;
                }
                DoubleHeight *= 0.393701d;
                DoubleHeight -= 60.0d;
            } else if (DoubleHeight < 5.0d) {
                return;
            } else {
                DoubleHeight *= 12.0d;
                DoubleHeight += DoubleInch;
                DoubleHeight -= 60.0d;
            }

            if (gender.equalsIgnoreCase(getString(R.string.str_male))) {
                calculate = (DoubleHeight * 1.9d) + 52.0d;
            } else {
                calculate = (DoubleHeight * 1.7d) + 49.0d;
            }

            try {
                new Pref(context).putInt(Pref.CALCULATOR_AGE,Integer.parseInt(EdtAgeCalculator.getText().toString()));
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
            TvDialogName.setText(getResources().getString(R.string.str_idealweight));
            TvDialogDesc.setText(getResources().getString(R.string.str_idealweight_desc));

            CardIdealWeight.setVisibility(View.VISIBLE);
            TvDialogWeightSubTitle.setText(getString(R.string.str_uridealweight));
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
        EdtAgeCalculator.setText(String.valueOf(new Pref(context).getInt(Pref.CALCULATOR_AGE,Pref.AGE)));
        EdtInchCalculator.setText("");
        EdtAgeCalculator.requestFocus();

        TvFTOrCMIdealWeight.setText(getString(R.string.str_cm));
        SpinnerGenderIdealWeight.setSelection(0);
        SpinnerHeightIdealWeight.setSelection(0);
        LLHeightCalculator.setVisibility(View.GONE);
    }

    private void GotoCalculateChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.str_idealweight)));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println("------- VALLL : "+adapterView.getId()+" - "+R.id.SpinnerHeightIdealWeight);
        switch (adapterView.getId()) {
            case R.id.SpinnerHeightIdealWeight:
                String weight = (String) SpinnerHeightIdealWeight.getSelectedItem().toString();
                if (weight.equalsIgnoreCase(getString(R.string.str_centimeters))) {
                    EdtHeightCalculator.setText("");
                    EdtInchCalculator.setText("");
                    TvFTOrCMIdealWeight.setText(getString(R.string.str_cm));
                    LLHeightCalculator.setVisibility(View.GONE);
                } else {
                    TvFTOrCMIdealWeight.setText(getString(R.string.str_ft));
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