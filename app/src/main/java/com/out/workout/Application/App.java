package com.out.workout.Application;

import android.app.Application;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class App extends Application {
    static App App = null;
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

    public void speak(String str) {
        try {
            if (textToSpeech != null) {
                textToSpeech.setSpeechRate(1.0f);
                textToSpeech.speak(str, TextToSpeech.QUEUE_ADD, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            if (textToSpeech != null) {
                textToSpeech.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
