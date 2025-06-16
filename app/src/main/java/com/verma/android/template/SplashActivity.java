package com.verma.android.template;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.verma.android.common.AppConfig;
import com.verma.android.common.Utils;
import com.verma.android.deps.service.SharedKey;
import com.verma.android.onboarding.OnBoardingActivity;
import com.verma.android.template.databinding.ActivitySplashBinding;
import com.verma.android.template.ui.menu.MainActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity implements SharedKey {
    private static final String TAG = "SplashActivity";
    private int splashTimeOut = 1000;
    private Animation topAnimation;
    private Animation bottomAnimation;
    private Animation middleAnimation;

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initSplash();
        String verName = "Ver. " + Utils.getInstance().getVersionName();
        binding.appVersion.setText(verName);
        Glide.with(this).load(R.drawable.dot_loading).into(binding.process);

        if (AppConfig.featureSplash) {
            splashTimeOut = getResources().getInteger(R.integer.splash_time_out);
            ActionBar actionBar = getSupportActionBar();
            if (null != actionBar) {
                actionBar.hide();
            }
            if(AppConfig.featureVersionCheck){
                checkForUpdate();
            }else{
                startSplash();
            }
        } else {
            moveNext();
        }
    }

    private void startSplash() {
        ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
        Runnable runnable = this::moveNext;
        worker.schedule(runnable, splashTimeOut, TimeUnit.MILLISECONDS);
    }

    private void initSplash() {
        //Animation Calls
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_middle_animation);

        //-----------Setting Animations to the elements of SplashScreen-------- -
        binding.firstLine.setAnimation(topAnimation);
        binding.secondLine.setAnimation(topAnimation);
        binding.thirdLine.setAnimation(topAnimation);
        binding.fifthLine.setAnimation(topAnimation);
        binding.fifthLine.setAnimation(topAnimation);
        binding.sixthLine.setAnimation(topAnimation);
        binding.appTitle.setAnimation(middleAnimation);
        binding.appVersion.setAnimation(bottomAnimation);

        binding.appSubtitle.setAnimation(topAnimation);


    }

    private void moveNext(){
        if (AppConfig.featureOnboarding) {
            boolean isFirstLaunch = ((App) getApplication()).sharedPreferencesService.getBoolean(SharedKey.KEY_BOOLEAN_SETTING_KEEP_ME_LOGIN,true);
            if(isFirstLaunch){
                startOnboarding();
            }else{
                close();
            }
        }else{
            close();
        }
    }

    private void close() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void startOnboarding(){
        Intent intent = new Intent(this, OnBoardingActivity.class);
        onBoardingLauncher.launch(intent);
    }



    private void checkForUpdate() {
        try{
            String currentVersion = this.getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Version");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String latestVersion = dataSnapshot.child("versionNumber").getValue(String.class);
                    String appUrl = dataSnapshot.child("appUrl").getValue(String.class);

                    if (latestVersion != null && !latestVersion.equals(currentVersion)) {
                        if (TextUtils.isEmpty(appUrl)) {
                            Timber.tag(TAG).e("App URL is null in Firebase.");
                            startSplash(); // Proceed if app URL is missing
                        } else {
                            showUpdateDialog(appUrl);
                        }
                    } else {
                        startSplash();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Timber.tag(TAG).e(databaseError.toException(), "Failed to read version from Firebase.");
                    startSplash(); // Proceed if Firebase read fails
                }
            });

        }catch (Exception e){
            Timber.tag(TAG).e("checkForUpdate: %s", e.getMessage());
            startSplash();
        }
    }

    private void showUpdateDialog(String appUrl) {
        new AlertDialog.Builder(SplashActivity.this)
                .setTitle("New Version Available!")
                .setMessage("Please update our app to the latest version for continuous use.")
                .setPositiveButton("UPDATE", (dialog, which) -> {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl)));
                    finish();
                })
                .setNegativeButton("EXIT", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }


    ActivityResultLauncher<Intent> onBoardingLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    ((App) getApplication()).sharedPreferencesService.setBoolean(SharedKey.KEY_BOOLEAN_SETTING_KEEP_ME_LOGIN, false);
                    //TODO Task OnBoarding Done
                    // Intent data = result.getData(); // data is not used, can be removed
                    close();
                } else {
                    // Handle the case where onboarding was not completed successfully, e.g., user pressed back
                    // Depending on the desired behavior, you might want to close the app or retry onboarding
                    close(); // For now, just close like successful onboarding
                }
            });
}