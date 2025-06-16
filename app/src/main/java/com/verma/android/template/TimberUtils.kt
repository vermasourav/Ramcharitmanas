package com.verma.android.template

import com.verma.android.common.Utils
import timber.log.Timber

object TimberUtils {

    @JvmStatic
    fun configTimber() {
        if (Utils.IS_DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Timber DebugTree planted")
        }
    }
}