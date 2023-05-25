package com.out.workout.Application;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.out.workout.BuildConfig;
import com.out.workout.R;

import java.util.HashMap;
import java.util.Locale;

//sub_cat
//https://khnknocklock.firebaseio.com/Sunflower.json

//Utils

//imge
//https://d250cpfim6cf4j.cloudfront.net/cat_vegeterian.jpg

public class App extends Application {

    public static App App = null;
    public static TextToSpeech textToSpeech;

    public static App getInstance() {
        return App;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
}
