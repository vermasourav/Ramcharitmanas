package com.verma.android.template;

import android.content.Context;

import androidx.annotation.NonNull;

import com.verma.android.common.AppConfig;
import com.verma.android.deps.service.SharedKey;
import com.verma.android.deps.service.SharedPreferencesService;
import com.verma.android.ramcharitmanas.TimberUtils;


import timber.log.Timber;

public class App extends android.app.Application {

    public SharedPreferencesService sharedPreferencesService;
    private static App instance = null;


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

    }

    public Context getContext() {
        return this;
    }

    public boolean isFeaturesEnable(Context pContext, int features) {
        return pContext.getResources().getBoolean(features);
    }
}
