package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.R;
import com.out.workout.ui.adapter.FitnessAdapter;
import com.out.workout.ui.adapter.SubDietTipsAdapter;
import com.out.workout.utils.Constants;

import java.util.ArrayList;

public class FitnessCalculatorsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private RecyclerView RvDietsFitness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_calculators);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        RvDietsFitness = (RecyclerView) findViewById(R.id.RvDietsFitness);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
    }

    private void initActions() {
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

        TvTitle.setText(getString(R.string.title_activity_fitness_and_health_calculators));
        ArrayList<String> list = new ArrayList<>();
        list.add("BMR Calculator");
        list.add("Calorie Calculator");
        list.add("Ideal Weight Calculator");
        list.add("Protein Calculator");
        list.add("Body Fat Calculator");
        list.add("Water Intake");
        RvDietsFitness.setLayoutManager(new LinearLayoutManager(context));
        RvDietsFitness.setAdapter(new FitnessAdapter(context, list));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
        }
    }
}