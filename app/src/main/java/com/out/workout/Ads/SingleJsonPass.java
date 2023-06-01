package com.out.workout.Ads;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingleJsonPass extends Application {
    private static SingleJsonPass mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private SingleJsonPass(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized SingleJsonPass getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SingleJsonPass(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
