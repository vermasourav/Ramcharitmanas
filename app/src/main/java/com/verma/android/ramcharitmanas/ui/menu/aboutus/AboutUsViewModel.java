package com.verma.android.ramcharitmanas.ui.menu.aboutus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.verma.android.common.Utils;
import com.verma.android.ramcharitmanas.R;
import com.verma.android.ramcharitmanas.ui.menu.aboutus.models.Member;
import com.verma.android.ramcharitmanas.ui.menu.aboutus.models.OfficeInfo;

import java.util.ArrayList;

public class AboutUsViewModel extends ViewModel {

    final String urlIsNotProvidedYet = "URL is not provided yet";
    public ArrayList<Member> members = new ArrayList<>();
    public OfficeInfo officeInfo;
    private MutableLiveData<String> mText;

    public AboutUsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public void init(Context pContext) {
        try {
            String aboutMe = Utils.getInstance().readFile(pContext, R.raw.about_me);
            if (!TextUtils.isEmpty(aboutMe)) {
                officeInfo = new Gson().fromJson(aboutMe, OfficeInfo.class);
                if (null != officeInfo) {
                    members.addAll(officeInfo.getMembers());
                }
            } else {
                officeInfo = new Gson().fromJson("", OfficeInfo.class);
            }
        } catch (Exception e) {
            officeInfo = new Gson().fromJson("", OfficeInfo.class);
        }
    }

    public void onClickGooglePlay(View pView) {
        if (AboutUsHelper.isAvailable(officeInfo.getGooglePlayUrl())) {
            openBrowserTab(pView.getContext(),Uri.parse(officeInfo.getGooglePlayUrl()));
        } else {
            Toast.makeText(pView.getContext(), urlIsNotProvidedYet, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickFacebook(View pView) {
        if (AboutUsHelper.isAvailable(officeInfo.getFacebookPageUrl())) {
            Intent intent = AboutUsHelper.getFacebookPageIntent(pView.getContext(), officeInfo);
            openBrowserTab(pView.getContext(),intent.getData());
        } else {
            Toast.makeText(pView.getContext(), urlIsNotProvidedYet, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickGroup(View pView) {
        if (AboutUsHelper.isAvailable(officeInfo.getGroupUrl())) {
            openBrowserTab(pView.getContext(),Uri.parse(officeInfo.getGroupUrl()));
        } else {
            Toast.makeText(pView.getContext(), urlIsNotProvidedYet, Toast.LENGTH_SHORT).show();
        }
    }

    private void openBrowserTab(Context context, Uri pUri) {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        builder.setShowTitle(true);
        customTabsIntent.launchUrl(context, pUri);
    }

    public void onClickYoutube(View pView) {
        if (AboutUsHelper.isAvailable(officeInfo.getYoutubeUrl())) {
            openBrowserTab(pView.getContext(),Uri.parse(officeInfo.getYoutubeUrl()));
        } else {
            Toast.makeText(pView.getContext(), urlIsNotProvidedYet, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickGithub(View pView) {
        if (AboutUsHelper.isAvailable(officeInfo.getGithubUrl())) {
            openBrowserTab(pView.getContext(),Uri.parse(officeInfo.getGithubUrl()));
        } else {
            Toast.makeText(pView.getContext(), urlIsNotProvidedYet, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickWeb(View pView) {
        if (AboutUsHelper.isAvailable(officeInfo.getWebUrl())) {
            openBrowserTab(pView.getContext(),Uri.parse(officeInfo.getWebUrl()));
        } else {
            Toast.makeText(pView.getContext(), urlIsNotProvidedYet, Toast.LENGTH_SHORT).show();
        }
    }

    public LiveData<String> getText() {
        return mText;
    }
}