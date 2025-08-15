package com.verma.android.template.ui.menu.aboutus;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.verma.android.template.App;
import com.verma.android.template.R;
import com.verma.android.template.ui.menu.aboutus.models.OfficeInfo;

import timber.log.Timber;

public class AboutUsHelper {
    private static final String TAG = "AboutUsHelper";

    private AboutUsHelper() {
        throw new IllegalStateException("AboutUsHelper class No Public constructor");
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

    private static GlideUrl getGlideUrl(String pURL) {
        GlideUrl glideURL = null;
        if (isAvailable(pURL) ) {
            String USER_AGENT = "Android Application";
            final String APP_AGENT = "VERMA";
            LazyHeaders authHeaders =
                    new LazyHeaders.Builder()
                            .setHeader("User-Agent", USER_AGENT)
                            .setHeader("APP-Agent", APP_AGENT)
                            .build();
            glideURL = new GlideUrl(pURL, authHeaders);
            Timber.d(glideURL.toStringUrl());
        }
        return glideURL;
    }

    public static void setImageWithGlide(Context context, final String pURL, final ImageView pImageView) {
        GlideUrl glideURL = getGlideUrl(pURL);
        Glide.with(context)
                .load(glideURL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Timber.e("Error loading image");
                        pImageView.setImageResource(R.drawable.ic_about_face_profile_black_18dp);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .thumbnail(0.5f)
                .into(pImageView);
    }
}
