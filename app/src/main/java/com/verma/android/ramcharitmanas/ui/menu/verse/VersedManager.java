/*
 * Created by: V3RMA SOURAV on 07/03/24, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : DashBoardManager
 * Last modified:  07/03/24, 10:09 pm
 * Location: Bangalore, India
 */

package com.verma.android.ramcharitmanas.ui.menu.verse;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.DashBoardWindowItem;
import com.verma.android.dashboard.pojo.Child;
import com.verma.android.dashboard.pojo.DashBoardGroup;
import com.verma.android.dashboard.pojo.Group;
import com.verma.android.dashboard.window.WindowGroup;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class VersedManager {
    public static final String USER_AGENT = "VersedManager";
    public static final String APP_AGENT = "V3RMA";
    private static final String TAG = "VersedManager";
    private Gson gson;


    public VersedManager() {
        gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return false;
                    }
                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create();
    }

    public ArrayList<Child> getVerseAsChilds(Context context, String fileName, boolean isSorting) {
        ArrayList<Child> verseAsChildItems = new ArrayList<>();
        try {
            VerseGroup verseGroup = gson.fromJson(loadJSONFromAsset(context, fileName), VerseGroup.class);
            verseAsChildItems =   convertVerseGroupToChildItems(isSorting, verseGroup);
        } catch (Exception e) {
            Timber.tag(TAG).d("getVerseAsChilds: %s", e.getMessage());
            //DO Nothing
        }
        return verseAsChildItems;

    }

    private ArrayList<Child>  convertVerseGroupToChildItems(boolean isSorting, VerseGroup VerseGroup) {

        ArrayList<Child> childes = new ArrayList<>();
        if(null == VerseGroup){
            return  childes;
        }
        final List<Verse> groups = VerseGroup.getVerses();

        groups.forEach(group -> {
           String verseNumber = group.getVerseNumber()+"";
           String content = group.getContent();
            Child child = new Child().withDescription(content).withId(1).withName(verseNumber);
            childes.add(child);
        });
        if (isSorting) {
            childes.sort((o1, o2) -> o1.getChildName().compareToIgnoreCase(o2.getChildName()));

        }
        return childes;
    }


    private String loadJSONFromAsset(Context context, String pFileName) {
        String json;
        try (InputStream is = context.getAssets().open(pFileName)) {
            int size = is.available();
            byte[] buffer = new byte[size];
            int bytesRead = is.read(buffer);
            if (bytesRead != size) {
                Timber.tag(TAG).w("loadJSONFromAsset: bytesRead != size for %s", pFileName);
            }
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            Timber.tag(TAG).e(ex, "Error loading JSON from asset: %s", pFileName);
            return null;
        }
        return json;
    }

}
