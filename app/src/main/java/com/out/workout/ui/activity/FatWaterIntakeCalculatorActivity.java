package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.Ads.Ad_Interstitial;
import com.out.workout.R;
import com.out.workout.ui.adapter.FitSliderAdapter;
import com.out.workout.ui.adapter.SliderLayoutManager;
import com.out.workout.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class FatWaterIntakeCalculatorActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private RecyclerView  RvFatAge;
    private TextView BtnFatCalculate;
    private FrameLayout FlFatMale, FlFatFemale;
    private ArrayList<String> data = new ArrayList<>();
    private ImageView IvFatFemale, IvFatMale;
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fat_water_intake_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = findViewById(R.id.IvBack);
        TvTitle = findViewById(R.id.TvTitle);
        RvFatAge = findViewById(R.id.RvFatAge);
        BtnFatCalculate = findViewById(R.id.BtnFatCalculate);
        FlFatMale = findViewById(R.id.FlFatMale);
        FlFatFemale = findViewById(R.id.FlFatFemale);
        IvFatFemale = findViewById(R.id.IvFatFemale);
        IvFatMale = findViewById(R.id.IvFatMale);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnFatCalculate.setOnClickListener(this);
        FlFatMale.setOnClickListener(this);
        FlFatFemale.setOnClickListener(this);
    }

    private void initActions() {
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

        List<Number> list = new ArrayList<>();
        for (int i = 5; i < 99; i++) {
            list.add(i);
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (Number number : list) {
            arrayList.add(String.valueOf(number.intValue()));
        }
        data = arrayList;
        TvTitle.setText("Water Intake Calculator");
        setHorizontalSlider();
        FlFatMale.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnFatCalculate:
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Load Ad....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Ad_Interstitial.getInstance().showInter(FatWaterIntakeCalculatorActivity.this, new Ad_Interstitial.MyCallback() {
                            @Override
                            public void callbackCall() {
                                GotoFitCalculate();
                            }
                        });
                    }
                }, 3000L);
                break;
            case R.id.FlFatMale:
                SelectGenderMale();
                break;
            case R.id.FlFatFemale:
                SelectGenderFemale();
                break;
        }
    }

    public void SelectGenderFemale() {
        FlFatFemale.setSelected(true);
        FlFatMale.setSelected(false);
        IvFatFemale.setVisibility(View.VISIBLE);
        IvFatMale.setVisibility(View.GONE);
    }

    public void SelectGenderMale() {
        FlFatMale.setSelected(true);
        FlFatFemale.setSelected(false);
        IvFatFemale.setVisibility(View.GONE);
        IvFatMale.setVisibility(View.VISIBLE);
    }

    public final void setHorizontalSlider() {
        int screenWidth = (Constants.getScreenWidth(context) / 2) - Constants.dpToPx(context, 40);
        RvFatAge.setPadding(screenWidth, 0, screenWidth, 0);
        SliderLayoutManager sliderLayoutManager = new SliderLayoutManager(context);
        sliderLayoutManager.setCallback(i -> {
            String str = getData().get(i);
            setAge(Integer.parseInt(str));
        });

        RvFatAge.setLayoutManager(sliderLayoutManager);
        FitSliderAdapter fitSliderAdapter = new FitSliderAdapter();
        fitSliderAdapter.setData(getData());
        fitSliderAdapter.setCallback(view -> RvFatAge.smoothScrollToPosition(RvFatAge.getChildLayoutPosition(view)));
        RvFatAge.setAdapter(fitSliderAdapter);
        RvFatAge.postDelayed(() -> setHorizontalPicker(), 250L);
    }

    public void setHorizontalPicker() {
        RvFatAge.smoothScrollToPosition(20);
    }

    public void setAge(int i) {
        age = i;
    }

    public final int getAge() {
        return this.age;
    }

    public final ArrayList<String> getData() {
        return data;
    }

    public final void GotoFitCalculate() {
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

        TextView BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);

        TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
        TextView TvDialogRequiredWater = dialog.findViewById(R.id.TvDialogRequiredWater);
        NestedScrollView ScrollWater = dialog.findViewById(R.id.ScrollWater);

        RelativeLayout  RlCardItem = dialog.findViewById(R.id.RlCardItem);
        RlCardItem.setVisibility(View.GONE);
        TvDialogWeightSubTitle.setText("Total water (incl. water in food):10≈");
        ScrollWater.setVisibility(View.VISIBLE);

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, getIntakeWater(getAge(), FlFatMale.isSelected()));
        animator.setDuration(1000L);
        animator.addUpdateListener(valueAnimator -> TvDialogRequiredWater.setText(Constants.formattedString(Float.parseFloat(valueAnimator.getAnimatedValue().toString()))));
        animator.start();

        BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());


        dialog.show();
    }

    private float getIntakeWater(int age, boolean IsMale) {
        boolean Iswater = false;
        if (IsMale) {
            if (1 > age || age > 3) {
                if (4 > age || age > 8) {
                    if (9 > age || age > 13) {
                        if (14 <= age && age <= 18) {
                            Iswater = true;
                        }
                        return Iswater ? 3.3f : 3.7f;
                    }
                    return 2.4f;
                }
                return 1.7f;
            }
            return 1.3f;
        } else if (1 > age || age > 3) {
            if (4 > age || age > 8) {
                if (9 > age || age > 13) {
                    if (14 <= age && age <= 18) {
                        Iswater = true;
                    }
                    return Iswater ? 2.3f : 2.7f;
                }
                return 2.1f;
            }
            return 1.7f;
        } else {
            return 1.3f;
        }
    }

}