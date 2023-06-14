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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.out.workout.Ads.Ad_Interstitial;
import com.out.workout.Ads.Ad_Native;
import com.out.workout.R;
import com.out.workout.ui.adapter.SpinnerAdapters;
import com.out.workout.utils.Constants;
import com.out.workout.utils.Pref;

import androidx.appcompat.app.AppCompatActivity;

public class WaterIntakeCalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private EditText EdtAgeWater, EdtWeightWater;
    private TextView BtnWeightWater;
    private TextView BtnResetWater;
    private ImageView BtnChartWater;
    private double  DoubleWeight, DoubleAge,  DoubleWater;
    private boolean BoolCheck;
    private int calculate_water;
    private String calculate_glass;
    private Spinner SpinnerGenderWater,SpinnerWeightWater;

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
        SpinnerGenderWater = (Spinner) findViewById(R.id.SpinnerGenderWater);
        SpinnerWeightWater = (Spinner) findViewById(R.id.SpinnerWeightWater);
        EdtWeightWater = (EditText) findViewById(R.id.EdtWeightWater);
        BtnWeightWater = (TextView) findViewById(R.id.BtnWeightWater);
        BtnResetWater = (TextView) findViewById(R.id.BtnResetWater);
        BtnChartWater = (ImageView) findViewById(R.id.BtnChartWater);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnWeightWater.setOnClickListener(this);
        BtnResetWater.setOnClickListener(this);
        BtnChartWater.setOnClickListener(this);
    }

    private void initActions() {
        Ad_Native.getInstance().showNative250(this, findViewById(R.id.FlNative));
        TvTitle.setText(getString(R.string.str_waterintake));
        EdtAgeWater.setText(String.valueOf(new Pref(context).getInt(Pref.CALCULATOR_AGE,Pref.AGE)));
        String[] GenderArr = {getResources().getString(R.string.str_male), getResources().getString(R.string.str_female)};
        String[] WeightArr = {getResources().getString(R.string.str_kilograms), getResources().getString(R.string.str_pounds)};
        SpinnerGenderWater.setAdapter((SpinnerAdapter) new SpinnerAdapters(this, R.layout.item_spinner, GenderArr));
        SpinnerWeightWater.setAdapter((SpinnerAdapter) new SpinnerAdapters(this, R.layout.item_spinner, WeightArr));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnWeightWater:
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Load Ad....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Ad_Interstitial.getInstance().showInter(WaterIntakeCalculatorActivity.this, new Ad_Interstitial.MyCallback() {
                            @Override
                            public void callbackCall() {
                                GotoCalculateWeight();
                            }
                        });
                    }
                }, 3000L);
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
        String WaterWeight = (String) SpinnerWeightWater.getSelectedItem().toString();
        try {
            try {
                DoubleWeight = Double.parseDouble(EdtWeightWater.getText().toString());
            } catch (NumberFormatException unused) {
                BoolCheck = true;
            }
            try {
                DoubleAge = Double.parseDouble(EdtAgeWater.getText().toString());
            } catch (NumberFormatException unused2) {
                BoolCheck = true;
            }
            if (BoolCheck) {
                BoolCheck = false;
                return;
            }
            if (!WaterWeight.equalsIgnoreCase(getString(R.string.str_kilograms))) {
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
                new Pref(context).putInt(Pref.CALCULATOR_AGE,Integer.parseInt(EdtAgeWater.getText().toString()));
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


            LinearLayout LlWater = dialog.findViewById(R.id.LlWater);
            TextView TvDialogWater = dialog.findViewById(R.id.TvDialogWater);
            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            TextView BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);



            ImageView IvDialogBanner = dialog.findViewById(R.id.IvDialogBanner);
            TextView TvDialogName = dialog.findViewById(R.id.TvDialogName);
            TextView TvDialogDesc = dialog.findViewById(R.id.TvDialogDesc);

            IvDialogBanner.setImageResource(R.drawable.ic_water_intake);
            TvDialogName.setText(getResources().getString(R.string.str_waterintake));
            TvDialogDesc.setText(getResources().getString(R.string.str_waterintake_desc));

            LlWater.setVisibility(View.VISIBLE);
            TvDialogWeightSubTitle.setText(getString(R.string.str_dailywaterreq));
            TvDialogWater.setText(calculate_glass);

            BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());


            dialog.show();
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
        }
    }

    private void GotoCalculateReset() {
        EdtWeightWater.setText("");
        EdtAgeWater.setText(String.valueOf(new Pref(context).getInt(Pref.CALCULATOR_AGE,Pref.AGE)));
        EdtAgeWater.requestFocus();

        SpinnerGenderWater.setSelection(0);
        SpinnerWeightWater.setSelection(0);
    }

    private void GotoCalculateChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.str_waterintake)));
    }
}