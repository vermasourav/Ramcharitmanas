package com.verma.android.common;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.verma.android.template.R;

import timber.log.Timber;

public class AppConfig {

    private static final AppConfig ourInstance = new AppConfig();
    private static final String TAG = "AppConfig";
    public static boolean featureSplash;
    public static boolean featureVersionCheck;
    public static boolean featureOnboarding;
    public static boolean featureUxTagging;
    public boolean featureRateUs;
    public boolean featureShareMenu;

    public boolean featureMenuOne;
    public boolean featureMenuTwo;
    public boolean featureMenuThree;
    public boolean featureMenuFour;
    public boolean featureMenuFive;
    public boolean featureMenuAboutUs;
    public boolean featureMenuSix;
    public boolean featureMenuSetting;
    public boolean featureMenuFaq;
    public boolean featureMenuPrivacy;
    public boolean featureMenuTermsOfService;
    public boolean featureMenuVersion;

    private AppConfig() {
        //Do Nothing
        Timber.tag(TAG).d("AppConfig: ");
    }

    public static AppConfig getInstance() {
        return ourInstance;
    }

    public void init(Context pContext) {
        Utils.getInstance().printHashKey(pContext);
        featureSplash = isFeaturesEnable(pContext, R.bool.feature_splash);
        featureVersionCheck = isFeaturesEnable(pContext, R.bool.feature_version_check);
        featureOnboarding = isFeaturesEnable(pContext, R.bool.feature_onboarding);
        featureUxTagging = isFeaturesEnable(pContext, R.bool.feature_ux_tagging);
        featureRateUs = isFeaturesEnable(pContext, R.bool.feature_rate_us);
        featureShareMenu = isFeaturesEnable(pContext, R.bool.feature_share_menu);

        featureMenuOne = isFeaturesEnable(pContext, R.bool.feature_menu_one);
        featureMenuTwo = isFeaturesEnable(pContext, R.bool.feature_menu_two);
        featureMenuThree = isFeaturesEnable(pContext, R.bool.feature_menu_three);
        featureMenuFour = isFeaturesEnable(pContext, R.bool.feature_menu_four);
        featureMenuFive = isFeaturesEnable(pContext, R.bool.feature_menu_five);
        featureMenuSix = isFeaturesEnable(pContext, R.bool.feature_menu_six);
        featureMenuSetting = isFeaturesEnable(pContext, R.bool.feature_menu_setting);
        featureMenuAboutUs = isFeaturesEnable(pContext, R.bool.feature_menu_about_us);
        featureMenuFaq = isFeaturesEnable(pContext, R.bool.feature_menu_faq);
        featureMenuPrivacy = isFeaturesEnable(pContext, R.bool.feature_menu_privacy);
        featureMenuTermsOfService = isFeaturesEnable(pContext, R.bool.feature_menu_terms_of_service);
        featureMenuVersion = isFeaturesEnable(pContext, R.bool.feature_menu_version);

        if(Utils.IS_DEBUG){
            Timber.tag(TAG).d("AppConfig: %s", this.toString());
        }

    }

    public boolean isFeaturesEnable(Context pContext, int features) {
        return pContext.getResources().getBoolean(features);
    }

    @NonNull
    @Override
    public String toString() {
        return "AppConfig{" +
                "featureRateUs=" + featureRateUs +
                ", featureShareMenu=" + featureShareMenu +
                ", featureMenuOne=" + featureMenuOne +
                ", featureMenuTwo=" + featureMenuTwo +
                ", featureMenuThree=" + featureMenuThree +
                ", featureMenuFour=" + featureMenuFour +
                ", featureMenuFive=" + featureMenuFive +
                ", featureMenuAboutUs=" + featureMenuAboutUs +
                ", featureMenuSix=" + featureMenuSix +
                ", featureMenuSetting=" + featureMenuSetting +
                ", featureMenuFaq=" + featureMenuFaq +
                ", featureMenuPrivacy=" + featureMenuPrivacy +
                ", featureMenuTermsOfService=" + featureMenuTermsOfService +
                ", featureMenuVersion=" + featureMenuVersion +
                '}';
    }
}
