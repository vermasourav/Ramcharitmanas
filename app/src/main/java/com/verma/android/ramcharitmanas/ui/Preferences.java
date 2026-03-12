/*
 * Created by: V3RMA SOURAV on 08/03/24, 3:18 pm
 * Copyright © 2023 All rights reserved
 * Class name : Preferences
 * Last modified:  16/02/24, 1:40 pm
 * Location: Bangalore, India
 *
 */

package com.verma.android.ramcharitmanas.ui;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class Preferences {
    public static final String PREFS_FROM_VALUE = "from_value";
    public static final String PREFS_THEME = "light_theme";
    private static Preferences mInstance;
    private Context mContext;
    private SharedPreferences mPrefs;

    private Preferences(Context context) {
        this.mContext = context;
        this.mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Preferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Preferences(context.getApplicationContext());
        }
        return mInstance;
    }

    public boolean isLightTheme() {
        return this.mPrefs.getBoolean(PREFS_THEME, false);
    }

    public SharedPreferences getPreferences() {
        return this.mPrefs;
    }

    public String getLastValue() {
        return this.mPrefs.getString(PREFS_FROM_VALUE, "1.0");
    }

    public void setLastValue(String str) {
        this.mPrefs.edit().putString(PREFS_FROM_VALUE, str).apply();
    }
}
