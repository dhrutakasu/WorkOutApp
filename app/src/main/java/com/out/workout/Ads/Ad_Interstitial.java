package com.out.workout.Ads;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.out.workout.utils.Pref;

public class Ad_Interstitial {
    static int gclick = 1;
    private static Ad_Interstitial mInstance;
    public InterstitialAd interstitialOne;

    MyCallback myCallback;

    public interface MyCallback {
        void callbackCall();
    }

    public static Ad_Interstitial getInstance() {
        if (mInstance == null) {
            mInstance = new Ad_Interstitial();
        }
        return mInstance;
    }

    public void loadInterOne(final Activity activity) {
        MobileAds.initialize(activity, new OnInitializationCompleteListener() {

            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        InterstitialAd.load(activity, new Pref(activity).getString(Pref.AD_INTER, ""), new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {

            public void onAdLoaded(InterstitialAd interstitialAd) {
                Ad_Interstitial.this.interstitialOne = interstitialAd;
                Ad_Interstitial.this.interstitialOne.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Ad_Interstitial.this.interstitialOne = null;
                        Ad_Interstitial.this.loadInterOne(activity);
                        if (Ad_Interstitial.this.myCallback != null) {
                            Ad_Interstitial.this.myCallback.callbackCall();
                            Ad_Interstitial.this.myCallback = null;
                        }
                    }
                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {

                        Ad_Interstitial.this.interstitialOne = null;
                        Ad_Interstitial.this.loadInterOne(activity);
                    }
                });
            }
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Ad_Interstitial.this.interstitialOne = null;
            }
        });
    }



    public void showInter(Activity activity, MyCallback myCallback2) {
        this.myCallback = myCallback2;
        int integer =new Pref(activity).getInt(Pref.CLICK, 1);
        int i = gclick;

        String string2 = new Pref(activity).getString(Pref.SHOW, "no");

        if (string2.equalsIgnoreCase("yes")) {
            if (i == integer) {
                gclick = 1;
                InterstitialAd interstitialAd = this.interstitialOne;
                if (interstitialAd != null) {
                    interstitialAd.show(activity);
                    return;
                }

                MyCallback myCallback3 = this.myCallback;
                if (myCallback3 != null) {
                    myCallback3.callbackCall();
                    this.myCallback = null;
                    return;
                }
                return;
            }
            gclick = i + 1;
        } else {
            MyCallback myCallback4 = this.myCallback;
            if (myCallback4 != null) {
                myCallback4.callbackCall();
                this.myCallback = null;
            }
            return;
        }


    }

    public void showInterBack(Activity activity, MyCallback myCallback2) {
        this.myCallback = myCallback2;
        int integer = new Pref(activity).getInt(Pref.AD_BACK, 0);
        int integer2 = new Pref(activity).getInt(Pref.CLICK, 1);
        if (integer == 0) {
            int i = gclick;
            if (i == integer2) {
                gclick = 1;
                InterstitialAd interstitialAd = this.interstitialOne;
                if (interstitialAd != null) {
                    interstitialAd.show(activity);
                    return;
                }
                MyCallback myCallback3 = this.myCallback;
                if (myCallback3 != null) {
                    myCallback3.callbackCall();
                    this.myCallback = null;
                    return;
                }
                return;
            }
            gclick = i + 1;
            MyCallback myCallback4 = this.myCallback;
            if (myCallback4 != null) {
                myCallback4.callbackCall();
                this.myCallback = null;
                return;
            }
            return;
        }
        MyCallback myCallback5 = this.myCallback;
        if (myCallback5 != null) {
            myCallback5.callbackCall();
            this.myCallback = null;
        }
    }

    public boolean isInternetOn(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        if (connectivityManager.getNetworkInfo(0).getState() != NetworkInfo.State.DISCONNECTED) {
            connectivityManager.getNetworkInfo(1).getState();
            NetworkInfo.State state = NetworkInfo.State.DISCONNECTED;
        }
        return false;
    }

    public static void alert(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Internet Alert");
        builder.setMessage("You need to internet connection");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
