package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.out.workout.R;

public class MainActivity extends AppCompatActivity implements ActionBar.OnNavigationListener {

    private Context context;
    private NavController NcMain;
    private AppBarConfiguration AcMain;
    private Toolbar TbMain;
    private ImageView IvTraining, IvRoutines, IvDietTips, IvCalculator, IvProfile;

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
        TbMain = findViewById(R.id.TbMain);
        IvTraining = findViewById(R.id.IvTraining);
        IvRoutines = findViewById(R.id.IvRoutines);
        IvDietTips = findViewById(R.id.IvDietTips);
        IvCalculator = findViewById(R.id.IvCalculator);
        IvProfile = findViewById(R.id.IvProfile);

//        setSupportActionBar(TbMain);
//
//        AcMain = new AppBarConfiguration.Builder(R.id.NavTraining, R.id.NavRoutines, R.id.NavCalculator, R.id.NavDietTips, R.id.NavProfile)
//                .setDrawerLayout(DlMain)
//                .build();
//        NcMain = Navigation.findNavController(this, R.id.NcMain);
//        NavigationUI.setupActionBarWithNavController(this, NcMain, AcMain);
//        NavigationUI.setupWithNavController(NvMain, NcMain);

    }

    private void initListener() {
        NcMain.navigate(R.id.NavTraining);
        IvTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IvTraining.setBackgroundResource(R.drawable.ic_training_press);
                IvRoutines.setBackgroundResource(R.drawable.ic_routines);
                IvDietTips.setBackgroundResource(R.drawable.ic_diet_tips);
                IvDietTips.setBackgroundResource(R.drawable.ic_calculator);
                NcMain.navigate(R.id.NavTraining);
            }
        });
        IvRoutines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IvTraining.setBackgroundResource(R.drawable.ic_training);
                IvRoutines.setBackgroundResource(R.drawable.ic_routines_press);
                IvDietTips.setBackgroundResource(R.drawable.ic_diet_tips);
                IvDietTips.setBackgroundResource(R.drawable.ic_calculator);
                NcMain.popBackStack(R.id.NavTraining, false);
                NcMain.navigate(R.id.NavRoutines);
            }
        });
        IvDietTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IvTraining.setBackgroundResource(R.drawable.ic_training);
                IvRoutines.setBackgroundResource(R.drawable.ic_routines);
                IvDietTips.setBackgroundResource(R.drawable.ic_diet_tips_press);
                IvDietTips.setBackgroundResource(R.drawable.ic_calculator);
                NcMain.popBackStack(R.id.NavTraining, false);
                NcMain.navigate(R.id.NavDietTips);
            }
        });
        IvCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IvTraining.setBackgroundResource(R.drawable.ic_training);
                IvRoutines.setBackgroundResource(R.drawable.ic_routines);
                IvDietTips.setBackgroundResource(R.drawable.ic_diet_tips);
                IvDietTips.setBackgroundResource(R.drawable.ic_calculator_press);
                NcMain.popBackStack(R.id.NavTraining, false);
                NcMain.navigate(R.id.NavCalculator);
            }
        });
        IvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IvTraining.setBackgroundResource(R.drawable.ic_training);
                IvRoutines.setBackgroundResource(R.drawable.ic_routines);
                IvDietTips.setBackgroundResource(R.drawable.ic_diet_tips);
                IvDietTips.setBackgroundResource(R.drawable.ic_calculator);
                NcMain.popBackStack(R.id.NavTraining, false);
                NcMain.navigate(R.id.NavProfile);
            }
        });

        /*NvMain.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.NavTraining:
                    NcMain.navigate(R.id.NavTraining);
                    DlMain.closeDrawers();
                    break;
                case R.id.NavRoutines:
                    NcMain.popBackStack(R.id.NavTraining, false);
                    NcMain.navigate(R.id.NavRoutines);
                    DlMain.closeDrawers();
                    break;
                case R.id.NavCalculator:
                    NcMain.popBackStack(R.id.NavTraining, false);
                    NcMain.navigate(R.id.NavCalculator);
                    DlMain.closeDrawers();
                    break;
                case R.id.NavDietTips:
                    NcMain.popBackStack(R.id.NavTraining, false);
                    NcMain.navigate(R.id.NavDietTips);
                    DlMain.closeDrawers();
                    break;
                case R.id.NavProfile:
                    NcMain.popBackStack(R.id.NavTraining, false);
                    NcMain.navigate(R.id.NavProfile);
                    DlMain.closeDrawers();
                    break;
            }
            return true;
        });*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.NcMain);
        return NavigationUI.navigateUp(navController, AcMain) || super.onSupportNavigateUp();
    }

    private void initAction() {

    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        return false;
    }
}