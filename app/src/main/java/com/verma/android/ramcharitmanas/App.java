package com.verma.android.ramcharitmanas;

import android.content.Context;

import androidx.annotation.NonNull;

import com.verma.android.common.AppConfig;
import com.verma.android.deps.service.SharedPreferencesService;

public class App extends android.app.Application {

    private static App instance = null;
    SharedPreferencesService sharedPreferencesService;
    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public static App from(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        TimberUtils.configTimber();
        AppConfig.getInstance().init(this);
        sharedPreferencesService = new SharedPreferencesService(getSharedPreferences("ramcharitmanas", Context.MODE_PRIVATE));
    }

    public Context getContext() {
        return this;
    }

    public boolean isFeaturesEnable(Context pContext, int features) {
        return pContext.getResources().getBoolean(features);
    }

}
