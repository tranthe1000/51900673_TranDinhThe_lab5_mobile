package com.example.bai2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedPrefManager {
    private Map<Context, SharedPreferences> sharedPrefs = new HashMap<>();

    private SharedPrefManager() {
    }

    private volatile static SharedPrefManager uniqueInstance;

    public static SharedPrefManager getInstance() {
        if (uniqueInstance == null) {
            synchronized (SharedPrefManager.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new SharedPrefManager();
                }
            }
        }
        return uniqueInstance;
    }

    protected void putSharedPref(Context context, SharedPreferences sharedPref) {
        this.sharedPrefs.put(context, sharedPref);
    }

    protected SharedPreferences getSharedPref(Context context) {
        return sharedPrefs.get(context);
    }

    protected void removeAllSharedPref() {
        List<Context> contexts = new ArrayList<>(sharedPrefs.keySet());
        SharedPreferences mSharedPref;
        SharedPreferences.Editor mSharedPrefEditor;
        for (Context context : contexts) {
            mSharedPref = context.getSharedPreferences(context.getClass().getSimpleName(), Activity.MODE_PRIVATE);
            mSharedPrefEditor = mSharedPref.edit();
            mSharedPrefEditor.clear();
            mSharedPrefEditor.commit();
        }
    }
}
