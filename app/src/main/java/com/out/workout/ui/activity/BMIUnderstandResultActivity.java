package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.Ads.Ad_Interstitial;
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
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

        IvBack.setOnClickListener(v -> onBackPressed());
        TvTitle.setText(getString(R.string.str_bmi_title));
    }
    @Override
    public void onBackPressed() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Load Ad....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Ad_Interstitial.getInstance().showInter(BMIUnderstandResultActivity.this, new Ad_Interstitial.MyCallback() {
                    @Override
                    public void callbackCall() {
                        finish();
                    }
                });
            }
        }, 3000L);
    }
}