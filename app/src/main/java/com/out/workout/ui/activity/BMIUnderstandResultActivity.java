package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.out.workout.R;

public class BMIUnderstandResultActivity extends AppCompatActivity {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiunderstand_result);
        initViews();
        initListeners();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
    }

    private void initListeners() {
        IvBack.setOnClickListener(v -> onBackPressed());
        TvTitle.setText(getString(R.string.bmi_title));
    }
}