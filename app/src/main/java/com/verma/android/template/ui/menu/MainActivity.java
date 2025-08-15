package com.verma.android.template.ui.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.verma.android.common.AppConfig;
import com.verma.android.common.Utils;
//import com.verma.android.template.MobileAdsManager;
import com.verma.android.template.R;
import com.verma.android.template.databinding.ActivityMainBinding;


import timber.log.Timber;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle aToggle;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            setSupportActionBar(binding.appBarMain.toolbar);
            binding.appBarMain.fab.setOnClickListener(view -> {

                Snackbar snackbar = Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab);
                snackbar.setTextColor(ContextCompat.getColor(this, R.color.appTextColor));


                snackbar.show();
            });
            DrawerLayout drawer = binding.drawerLayout;
            NavigationView navigationView = binding.navView;
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            aToggle = new ActionBarDrawerToggle(this, drawer, binding.appBarMain.toolbar, android.R.string.ok, android.R.string.ok);
            //navigationView.getHeaderView(0).setVisibility(View.GONE);

            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home,
                    R.id.nav_one,
                    R.id.nav_two,
                    R.id.nav_three,
                    R.id.nav_four,
                    R.id.nav_five,
                    R.id.nav_six,
                    R.id.nav_setting,
                    R.id.nav_faq,
                    R.id.nav_privacy_policy,
                    R.id.nav_terms_of_service,
                    R.id.nav_item_version,
                    R.id.nav_about_us
            )
                    .setOpenableLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
            // setupBackPress();

        } catch (Exception e) {
            //DO Nothing
            Timber.d("onCreate: %s", e.getMessage());
        }

    }

    private void shareMe() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage = "\nLet me recommend you this application\n\n" +
                    "https://play.google.com/store/apps/details?id=" + Utils.getInstance().getApplicationId() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //Do Nothing
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all_screen, menu);
        menu.findItem(R.id.action_home).setVisible(true);
        menu.findItem(R.id.action_rate_us).setVisible(AppConfig.getInstance().featureRateUs);
        menu.findItem(R.id.action_share_me).setVisible(AppConfig.getInstance().featureShareMenu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (aToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.action_home) {
            gotoHome();
            return true;
        }
        if (id == R.id.action_rate_us) {
            clickRateMe();
            return true;
        } else if (id == R.id.action_share_me) {
            shareMe();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupMenu();
        displayVersion();
    }

    private void displayVersion() {
        String version = getVersion();
        SpannableString spanString = new SpannableString(version);

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, android.R.color.holo_red_light));
        spanString.setSpan(foregroundColorSpan, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        Menu menu = binding.navView.getMenu();
        menu.findItem(R.id.nav_item_version).setTitle(spanString);
    }

    public void hideKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception ignored) {
            Timber.e(ignored, "Error hiding keyboard");
        }
    }

    private String getVersion() {
        try {
            String verName = Utils.getInstance().getVersionName();
            return getString(R.string.app_name) + " Ver " + verName;
        } catch (Exception e) {
            Timber.e(e, "Error getting version name");
            return getString(R.string.app_name) + " Ver N/A";
        }
    }

    public void setupMenu() {
        Menu menu = binding.navView.getMenu();
        menu.findItem(R.id.nav_one).setVisible(AppConfig.getInstance().featureMenuOne);
        menu.findItem(R.id.nav_two).setVisible(AppConfig.getInstance().featureMenuTwo);
        menu.findItem(R.id.nav_three).setVisible(AppConfig.getInstance().featureMenuThree);
        menu.findItem(R.id.nav_four).setVisible(AppConfig.getInstance().featureMenuFour);
        menu.findItem(R.id.nav_five).setVisible(AppConfig.getInstance().featureMenuFive);
        menu.findItem(R.id.nav_six).setVisible(AppConfig.getInstance().featureMenuSix);
        menu.findItem(R.id.nav_setting).setVisible(AppConfig.getInstance().featureMenuSetting);
        menu.findItem(R.id.nav_about_us).setVisible(AppConfig.getInstance().featureMenuAboutUs);
        menu.findItem(R.id.nav_faq).setVisible(AppConfig.getInstance().featureMenuFaq);
        menu.findItem(R.id.nav_privacy_policy).setVisible(AppConfig.getInstance().featureMenuPrivacy);
        menu.findItem(R.id.nav_terms_of_service).setVisible(AppConfig.getInstance().featureMenuTermsOfService);
        menu.findItem(R.id.nav_item_version).setVisible(AppConfig.getInstance().featureMenuVersion);
    }

    private void gotoHome() {
        findViewById(R.id.nav_home).performClick();
    }
    private void clickRateMe() {
    }

    public void displayMessage(String pMessage) {
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.fab),
                pMessage,
                Snackbar.LENGTH_SHORT
        );
        snackbar.setTextColor(ContextCompat.getColor(this, R.color.appTextColor));
        snackbar.show();
    }

    public void setupBackPress() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                DrawerLayout drawer = binding.drawerLayout;
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    showExitAlert();
                    // If drawer is not open, show exit confirmation
                }
            }
        });
    }

    private void showExitAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.text_exit_request);
        builder.setCancelable(false)
                .setPositiveButton(getString(android.R.string.ok), (dialog, which) -> finish())
                .setNegativeButton(getString(android.R.string.no), null);
        builder.show();
        Timber.d("Exit alert dialog shown.");
    }

    public Context getContext() {
        return this;
    }

    @Deprecated
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.text_exit_request);
        builder.setCancelable(false)
                .setPositiveButton(getString(android.R.string.ok), (dialog, which) -> finish())
                .setNegativeButton(getString(android.R.string.no), null);
        //builder.show();
        super.onBackPressed();
    }

}
