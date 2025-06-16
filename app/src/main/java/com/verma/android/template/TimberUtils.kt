package com.verma.android.template

import timber.log.Timber

object TimberUtils {

    @JvmStatic
    fun configTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Timber DebugTree planted")
        }
    }
}