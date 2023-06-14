package com.out.workout.ui.activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.out.workout.Ads.Ad_Interstitial;
import com.out.workout.Ads.Ad_Native;
import com.out.workout.Ads.AppOpenAdManager;
import com.out.workout.Ads.SingleJsonPass;
import com.out.workout.Application.App;
import com.out.workout.R;
import com.out.workout.utils.Constants;
import com.out.workout.utils.Pref;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private Context context;
    private String url2 = "https://7starinnovation.com/api/demoapi.json";
    String AdShow, appid, app_open, banner_ad_unit_id, interstitial_full_screen = "", native_id;
    private static final String LOG_TAG = "AppOpenAdManager";
    private long secondsRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.GRAY);
        }
        setContentView(R.layout.activity_splash);
        context = this;
        createHandler(3);
    }

    private void createHandler(long seconds) {
        if (Constants.isConnectingToInternet(context)) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response2) {
                            try {
                                JSONObject response = new JSONObject(response2);

                                app_open = response.getString("AppOpenAd");
                                banner_ad_unit_id = response.getString("BannerAd");
                                interstitial_full_screen = response.getString("InterstitialAd");
                                native_id = response.getString("NativaAds");
                                AdShow = response.getString("AdShow");

                                Log.d("Manish", "onResponse is: " + banner_ad_unit_id);


                                new Pref(SplashActivity.this).putString(Pref.SHOW, AdShow);
                                new Pref(SplashActivity.this).putString(Pref.AD_BANNER, banner_ad_unit_id);
                                new Pref(SplashActivity.this).putString(Pref.AD_INTER, interstitial_full_screen);
                                new Pref(SplashActivity.this).putString(Pref.AD_NATIVE, native_id);
                                new Pref(SplashActivity.this).putString(Pref.AD_OPEN, app_open);

                                AppOpenAdManager appOpenAdManager = new AppOpenAdManager(SplashActivity.this);
//                            appOpenAdManager.loadAd(SplashActivity.this);
                                Ad_Native.getInstance().LoadNative(SplashActivity.this);
                                Ad_Interstitial.getInstance().loadInterOne(SplashActivity.this);
                                try {
                                    ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
                                    Bundle bundle = ai.metaData;
                                    String myApiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");
                                    ai.metaData.putString("com.google.android.gms.ads.APPLICATION_ID", app_open);//you can replace your key APPLICATION_ID here
                                    String ApiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");
                                } catch (PackageManager.NameNotFoundException e) {
                                } catch (NullPointerException e) {
                                }
                                CountDownTimer countDownTimer =
                                        new CountDownTimer(seconds * 1000, 1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                secondsRemaining = ((millisUntilFinished / 1000) + 1);
                                            }

                                            @Override
                                            public void onFinish() {
                                                secondsRemaining = 0;
                                                Application application = getApplication();

                                                if (!(application instanceof App)) {
                                                    Log.e(LOG_TAG, "Failed to cast application to MyApplication.");
                                                    startMainActivity();
                                                    return;
                                                }

//                                            Ad_Interstitial.getInstance().showInter(SplashActivity.this, new Ad_Interstitial.MyCallback() {
//                                        @Override
//                                        public void callbackCall() {
//                                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                                            finish();
//                                        }
//                                    });
                                                ((App) application)
                                                        .showAdIfAvailable(
                                                                SplashActivity.this,
                                                                new App.OnShowAdCompleteListener() {
                                                                    @Override
                                                                    public void onShowAdComplete() {
                                                                        Log.d(LOG_TAG, "onShowAdComplete.");
                                                                        startMainActivity();
                                                                    }
                                                                });
                                            }
                                        };
                                countDownTimer.start();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
            };
            SingleJsonPass.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(context, "Please turn on your internet connection...", Toast.LENGTH_LONG).show();
        }
    }

    public void startMainActivity() {
        startActivity(new Intent(this, WalkthroughActivity.class));
        finish();
    }
}