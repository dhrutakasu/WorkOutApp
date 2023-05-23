package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;
import com.out.workout.R;
import com.out.workout.utils.Constants;

public class ChartActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private ZoomageView ZoomIvChart;
    private TextView TvChartTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        initViews();
        initIntents();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        TvChartTitle = (TextView) findViewById(R.id.TvChartTitle);
        ZoomIvChart = (ZoomageView) findViewById(R.id.ZoomIvChart);

    }

    private void initIntents() {
        String StrChart = getIntent().getStringExtra(Constants.ChartType);
        if (StrChart.equalsIgnoreCase(getString(R.string.idealweight))) {
            TvTitle.setText(getString(R.string.ideal_chart));
            TvChartTitle.setText(getString(R.string.iwc_header));
            ZoomIvChart.setImageResource(R.drawable.ideal_weight);
        } else if (StrChart.equalsIgnoreCase(getString(R.string.bmi_title))) {
            TvTitle.setText(getString(R.string.bmi_title));
            TvChartTitle.setText(getString(R.string.bmi_chart));
            ZoomIvChart.setImageResource(R.drawable.chart_new);
        }else if (StrChart.equalsIgnoreCase(getString(R.string.heartrate))) {
            TvTitle.setText(getString(R.string.heart_chart));
            TvChartTitle.setText(getString(R.string.heart_chart_desc));
            ZoomIvChart.setImageResource(R.drawable.heart_chart);
        }else if (StrChart.equalsIgnoreCase(getString(R.string.canidonate))) {
            TvTitle.setText(getString(R.string.canidonate));
            TvChartTitle.setText(getString(R.string.bac_header));
            ZoomIvChart.setImageResource(R.drawable.giving_blood);
        }else if (StrChart.equalsIgnoreCase(getString(R.string.caloriesval))) {
            TvTitle.setText(getString(R.string.chart_calorie));
            TvChartTitle.setText(getString(R.string.calorie_chart_header));
            ZoomIvChart.setImageResource(R.drawable.calorie_chart);
        }else if (StrChart.equalsIgnoreCase(getString(R.string.waterintake))) {
            TvTitle.setText(getString(R.string.chart_water));
            TvChartTitle.setText(getString(R.string.water_intake_chart));
            ZoomIvChart.setImageResource(R.drawable.water_chart);
        }else if (StrChart.equalsIgnoreCase(getString(R.string.bodyfat))) {
            TvTitle.setText(getString(R.string.fat_chart));
            TvChartTitle.setText(getString(R.string.fat_header));
            ZoomIvChart.setImageResource(R.drawable.body_fat_chart);
        }else if (StrChart.equalsIgnoreCase(getString(R.string.bloodalcohol))) {
            TvTitle.setText(getString(R.string.alcohol_chart));
            TvChartTitle.setText(getString(R.string.bac_header));
            ZoomIvChart.setImageResource(R.drawable.bac);
        }
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
    }

    private void initActions() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
        }
    }
}