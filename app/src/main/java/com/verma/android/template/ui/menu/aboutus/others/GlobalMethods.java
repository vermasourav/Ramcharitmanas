package com.verma.android.template.ui.menu.aboutus.others;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.verma.android.template.ui.menu.aboutus.models.OfficeInfo;

public class GlobalMethods {
    private GlobalMethods() {
        throw new IllegalStateException("GlobalMethods class");
    }

    public static boolean isAvailable(String something) {
        return !TextUtils.isEmpty(something);
    }

    public static Intent getFacebookPageIntent(Context context, OfficeInfo officeInfo) {
        PackageManager pm = context.getPackageManager();
        Uri uri = Uri.parse(officeInfo.getFacebookPageUrl());
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://page/" + officeInfo.getFacebookPageID());
            }
        } catch (Exception e) {
            // Do Nothing
        }

        return new Intent(Intent.ACTION_VIEW, uri);
    }
}
