package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getkeepsafe.android.multistateanimation.MultiStateAnimation;
import com.google.android.gms.ads.AdSize;
import com.google.android.material.navigation.NavigationView;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.Ads.Ad_Native;
import com.out.workout.Application.App;
import com.out.workout.R;
import com.out.workout.model.WorkoutExerciseModel;
import com.out.workout.utils.Constants;

public class MainActivity extends AppCompatActivity implements ActionBar.OnNavigationListener {

    private Context context;
    private NavController NcMain;
    private AppBarConfiguration AcMain;
    private TextView TvTitleMain;
    private ImageView IvTraining, IvRoutines, IvDietTips, IvCalculator, IvProfile,IvNotification;
    private View DialogQuit;
    private TextView BtnDialogQuit,BtnDialogExreciseExit,BtnDialogExreciseNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initAction();
    }

    private void initView() {
        context = this;
        TvTitleMain = findViewById(R.id.TvTitleMain);
        IvTraining = findViewById(R.id.IvTraining);
        IvRoutines = findViewById(R.id.IvRoutines);
        IvDietTips = findViewById(R.id.IvDietTips);
        IvCalculator = findViewById(R.id.IvCalculator);
        IvProfile = findViewById(R.id.IvProfile);
        IvNotification = findViewById(R.id.IvNotification);
        DialogQuit = findViewById(R.id.DialogQuit);
        BtnDialogQuit = findViewById(R.id.BtnDialogQuit);
        BtnDialogExreciseExit = findViewById(R.id.BtnDialogExreciseExit);
        BtnDialogExreciseNo = findViewById(R.id.BtnDialogExreciseNo);
        NcMain = Navigation.findNavController(this, R.id.NcMain);

    }

    private void initListener() {
        NcMain.navigate(R.id.NavTraining);
        IvTraining.setOnClickListener(view -> {
            IvTraining.setImageResource(R.drawable.ic_training_press);
            IvRoutines.setImageResource(R.drawable.ic_routines);
            IvDietTips.setImageResource(R.drawable.ic_diet_tips);
            IvCalculator.setImageResource(R.drawable.ic_calculator);
            NcMain.navigate(R.id.NavTraining);
        });
        IvRoutines.setOnClickListener(view -> {
            IvTraining.setImageResource(R.drawable.ic_training);
            IvRoutines.setImageResource(R.drawable.ic_routines_press);
            IvDietTips.setImageResource(R.drawable.ic_diet_tips);
            IvCalculator.setImageResource(R.drawable.ic_calculator);
            NcMain.popBackStack(R.id.NavTraining, false);
            NcMain.navigate(R.id.NavRoutines);
        });
        IvDietTips.setOnClickListener(view -> {
            IvTraining.setImageResource(R.drawable.ic_training);
            IvRoutines.setImageResource(R.drawable.ic_routines);
            IvDietTips.setImageResource(R.drawable.ic_diet_tips_press);
            IvCalculator.setImageResource(R.drawable.ic_calculator);
            NcMain.popBackStack(R.id.NavTraining, false);
            NcMain.navigate(R.id.NavDietTips);
        });
        IvCalculator.setOnClickListener(view -> {
            IvTraining.setImageResource(R.drawable.ic_training);
            IvRoutines.setImageResource(R.drawable.ic_routines);
            IvDietTips.setImageResource(R.drawable.ic_diet_tips);
            IvCalculator.setImageResource(R.drawable.ic_calculator_press);
            NcMain.popBackStack(R.id.NavTraining, false);
            NcMain.navigate(R.id.NavCalculator);
        });
        IvProfile.setOnClickListener(view -> {
            startActivity(new Intent(context, ProfileActivity.class));
        });
        IvNotification.setOnClickListener(view -> {
            startActivity(new Intent(context, DayRemindersActivity.class));
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.NcMain);
        return NavigationUI.navigateUp(navController, AcMain) || super.onSupportNavigateUp();
    }

    private void initAction() {
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));
        Ad_Native.getInstance().showNative250(this, findViewById(R.id.FlNativeExit));

        BtnDialogQuit.setText("Would you like to quit this application?");
        BtnDialogExreciseExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        BtnDialogExreciseNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogQuit.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (DialogQuit.getVisibility()==View.VISIBLE){
            DialogQuit.setVisibility(View.GONE);
        }else {
            DialogQuit.setVisibility(View.VISIBLE);
        }
    }
}