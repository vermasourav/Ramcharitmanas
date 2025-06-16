package com.verma.android.deps;

import android.annotation.SuppressLint;

import com.verma.android.deps.models.base.SenderInfo;
import com.verma.android.template.App;
import com.verma.android.template.BuildConfig;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Constant {

    public static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static Constant instance;
    private static String DEFAULT_DATE_FORMAT = "dd/MM/yyyy hh.mm aa";
    String URL_PROFILE_IMG = "http://graph.facebook.com/672467397/picture?type=square";
    String AUTH_KEY_1 = "sourav";

    private Constant() {
    }

    public static Constant instance() {
        if (instance == null) {
            synchronized (Constant.class) {
                instance = new Constant();
            }
        }
        return instance;
    }

    public String getDeviceId() {
        return "1111111111111111";
    }

    public String getVersion() {
        try {
            String verName = App.getInstance().getVersionName();
            int verCode = App.getInstance().getVersionCode();
            return "Ver. " + verName + " (" + verCode + ")";
        } catch (Exception e) {
            return "";
        }
    }

    public String getTransactionId() {
        String input = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder builder = new StringBuilder(9);
        for (int i = 0; i < 9; i++) {
            int index = (int) (input.length() * Math.random());
            builder.append(input.charAt(index));
        }
        return builder.toString();
    }

    @SuppressLint("SimpleDateFormat")
    public String getTime() {
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        return s.format(new Date());
    }

    public SenderInfo getSenderInfo(String key) {
        return new SenderInfo()
                .withAppVersion(Constant.instance().getVersion())
                .withSender(Constant.instance().getDeviceId())
                ;
    }
}
