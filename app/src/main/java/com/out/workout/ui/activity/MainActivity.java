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

import com.google.android.material.navigation.NavigationView;
import com.out.workout.R;

public class MainActivity extends AppCompatActivity implements ActionBar.OnNavigationListener {

    private Context context;
    private DrawerLayout DlMain;
    private NavController NcMain;
    private AppBarConfiguration AcMain;
    private NavigationView NvMain;
    private Toolbar TbMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initAction();
    }

    private void initView() {
        context=this;
        TbMain = findViewById(R.id.TbMain);
        setSupportActionBar(TbMain);

        DlMain = findViewById(R.id.DlMain);

        NvMain = findViewById(R.id.NvMain);
        NvMain.bringToFront();
        NvMain.setItemIconTintList(null);
        AcMain = new AppBarConfiguration.Builder(R.id.NavTraining, R.id.NavRoutines, R.id.NavCalculator, R.id.NavDietTips, R.id.NavProfile)
                .setDrawerLayout(DlMain)
                .build();
        NcMain = Navigation.findNavController(this, R.id.NcMain);
        NavigationUI.setupActionBarWithNavController(this, NcMain, AcMain);
        NavigationUI.setupWithNavController(NvMain, NcMain);
    }

    private void initListener() {
        NvMain.setNavigationItemSelectedListener(menuItem -> {
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
        });
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