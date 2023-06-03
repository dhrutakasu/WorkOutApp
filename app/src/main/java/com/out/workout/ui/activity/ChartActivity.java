package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.jsibbold.zoomage.ZoomageView;
import com.out.workout.Ads.Ad_Banner;
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
        if (StrChart.equalsIgnoreCase(getString(R.string.str_idealweight))) {
            TvTitle.setText(getString(R.string.str_ideal_chart));
            TvChartTitle.setText(getString(R.string.str_iwc_header));
            ZoomIvChart.setImageResource(R.drawable.ideal_weight);
        } else if (StrChart.equalsIgnoreCase(getString(R.string.str_bmi_title))) {
            TvTitle.setText(getString(R.string.str_bmi_title));
            TvChartTitle.setText(getString(R.string.str_bmi_chart));
            ZoomIvChart.setImageResource(R.drawable.ic_chart_new);
        }else if (StrChart.equalsIgnoreCase(getString(R.string.str_heartrate))) {
            TvTitle.setText(getString(R.string.str_heart_chart));
            TvChartTitle.setText(getString(R.string.str_heart_chart_desc));
            ZoomIvChart.setImageResource(R.drawable.ic_heart_chart);
        }else if (StrChart.equalsIgnoreCase(getString(R.string.str_canidonate))) {
            TvTitle.setText(getString(R.string.str_canidonate));
            TvChartTitle.setText(getString(R.string.str_bac_header));
            ZoomIvChart.setImageResource(R.drawable.ic_giving_blood);
        }else if (StrChart.equalsIgnoreCase(getString(R.string.str_caloriesval))) {
            TvTitle.setText(getString(R.string.str_chart_calorie));
            TvChartTitle.setText(getString(R.string.str_calorie_chart_header));
            ZoomIvChart.setImageResource(R.drawable.ic_calorie_chart);
        }else if (StrChart.equalsIgnoreCase(getString(R.string.str_waterintake))) {
            TvTitle.setText(getString(R.string.str_chart_water));
            TvChartTitle.setText(getString(R.string.str_water_intake_chart));
            ZoomIvChart.setImageResource(R.drawable.ic_water_chart);
        }else if (StrChart.equalsIgnoreCase(getString(R.string.str_bodyfat))) {
            TvTitle.setText(getString(R.string.str_fat_chart));
            TvChartTitle.setText(getString(R.string.str_fat_header));
            ZoomIvChart.setImageResource(R.drawable.ic_body_fat_chart);
        }else if (StrChart.equalsIgnoreCase(getString(R.string.str_bloodalcohol))) {
            TvTitle.setText(getString(R.string.str_alcohol_chart));
            TvChartTitle.setText(getString(R.string.str_bac_header));
            ZoomIvChart.setImageResource(R.drawable.ic_bac);
        }else if (StrChart.equalsIgnoreCase(getString(R.string.str_preg_chart))) {
            TvTitle.setText(getString(R.string.str_preg_chart));
            TvChartTitle.setText(getString(R.string.str_iwc_header));
            ZoomIvChart.setImageResource(R.drawable.ic_preg_chart);
        }else if (StrChart.equalsIgnoreCase(getString(R.string.str_ovulation))) {
            TvTitle.setText(getString(R.string.str_chart_ovulation));
            TvChartTitle.setText(getString(R.string.str_ovu_header));
            ZoomIvChart.setImageResource(R.drawable.ic_ovulation_chart);
        }
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
    }

    private void initActions() {
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

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