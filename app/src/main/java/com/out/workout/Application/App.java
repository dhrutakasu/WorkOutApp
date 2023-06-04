package com.out.workout.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.out.workout.BuildConfig;
import com.out.workout.R;

import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

//sub_cat
//https://khnknocklock.firebaseio.com/Sunflower.json

//Utils

//https://github.com/PPartisan/Simple-Alarms/tree/master

//imge
//https://d250cpfim6cf4j.cloudfront.net/cat_vegeterian.jpg

//https://github.com/WrichikBasu/ShakeAlarmClock


public class App extends Application implements DefaultLifecycleObserver, Application.ActivityLifecycleCallbacks {

    private AppOpenAdManager appOpenAdManager;
    private Activity currentActivity;
    private static final String TAG = "MyApplication";

    public static App App = null;
    public static TextToSpeech textToSpeech;

    public static App getInstance() {
        return App;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.registerActivityLifecycleCallbacks(this);

        Log.d(TAG, "Google Mobile Ads SDK Version: " + MobileAds.getVersion());

        MobileAds.initialize(
                this,
                new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(
                            @NonNull InitializationStatus initializationStatus) {}
                });

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        appOpenAdManager = new AppOpenAdManager();
        App = this;
        new Thread(() -> {
            textToSpeech = new TextToSpeech(getInstance(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.QUEUE_FLUSH) {
                        textToSpeech.setLanguage(Locale.US);
                    }
                }
            });
        }).start();
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStart(owner);
        appOpenAdManager.showAdIfAvailable(currentActivity);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (!appOpenAdManager.isShowingAd) {
            currentActivity = activity;
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {}

    @Override
    public void onActivityPaused(@NonNull Activity activity) {}

    @Override
    public void onActivityStopped(@NonNull Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {}

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {}

    public void showAdIfAvailable(@NonNull Activity activity, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        appOpenAdManager.showAdIfAvailable(activity, onShowAdCompleteListener);
    }

    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }

    public void TextSpeak(String str) {
        try {
            if (textToSpeech != null) {
                textToSpeech.setSpeechRate(1.0f);
                textToSpeech.speak(str, TextToSpeech.QUEUE_ADD, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TextStop() {
        try {
            if (textToSpeech != null) {
                textToSpeech.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSpeaker() {
        try {
            if (textToSpeech != null) {
                textToSpeech.addEarcon("Done", BuildConfig.APPLICATION_ID, R.raw.clocktick_trim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSpeaker() {
        try {
            if (textToSpeech == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.playEarcon("Done", TextToSpeech.QUEUE_FLUSH, null, BuildConfig.APPLICATION_ID);
            } else {
                textToSpeech.playEarcon("Done", TextToSpeech.QUEUE_FLUSH, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class AppOpenAdManager {

        private static final String LOG_TAG = "AppOpenAdManager";
        private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294";

        private AppOpenAd appOpenAd = null;
        private boolean isLoadingAd = false;
        private boolean isShowingAd = false;

        private long loadTime = 0;

        public AppOpenAdManager() {}

        private void loadAd(Context context) {
            if (isLoadingAd || isAdAvailable()) {
                return;
            }

            isLoadingAd = true;
            AdRequest request = new AdRequest.Builder().build();
            AppOpenAd.load(
                    context,
                    AD_UNIT_ID,
                    request,
                    new AppOpenAd.AppOpenAdLoadCallback() {
                        @Override
                        public void onAdLoaded(AppOpenAd ad) {
                            appOpenAd = ad;
                            isLoadingAd = false;
                            loadTime = (new Date()).getTime();

                            Log.d(LOG_TAG, "onAdLoaded.");
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            isLoadingAd = false;
                            Log.d(LOG_TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
                        }
                    });
        }

        private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
            long dateDifference = (new Date()).getTime() - loadTime;
            long numMilliSecondsPerHour = 3600000;
            return (dateDifference < (numMilliSecondsPerHour * numHours));
        }

        private boolean isAdAvailable() {
            return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
        }

        private void showAdIfAvailable(@NonNull final Activity activity) {
            showAdIfAvailable(activity, new OnShowAdCompleteListener() {
                @Override
                public void onShowAdComplete() {
                }
            });
        }

        private void showAdIfAvailable(@NonNull final Activity activity, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
            if (isShowingAd) {
                Log.d(LOG_TAG, "The app open ad is already showing.");
                return;
            }

            if (!isAdAvailable()) {
                Log.d(LOG_TAG, "The app open ad is not ready yet.");
                loadAd(activity);
                onShowAdCompleteListener.onShowAdComplete();
                return;
            }

            Log.d(LOG_TAG, "Will show ad.");

            appOpenAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAd = null;
                            isShowingAd = false;

                            Log.d(LOG_TAG, "onAdDismissedFullScreenContent.");

                            onShowAdCompleteListener.onShowAdComplete();
                            loadAd(activity);
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            appOpenAd = null;
                            isShowingAd = false;

                            Log.d(LOG_TAG, "onAdFailedToShowFullScreenContent: " + adError.getMessage());

                            onShowAdCompleteListener.onShowAdComplete();
                            loadAd(activity);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            Log.d(LOG_TAG, "onAdShowedFullScreenContent.");
                        }
                    });

            isShowingAd = true;
            appOpenAd.show(activity);
        }
    }
}
