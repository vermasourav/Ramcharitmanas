package com.verma.android.template.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.DashBoardManager;
import com.verma.android.dashboard.DashboardClickListener;
import com.verma.android.template.R;
import com.verma.android.template.databinding.Fragment00Binding;
import com.verma.android.dashboard.Setup;

import java.util.ArrayList;

import timber.log.Timber;

public class Menu00Fragment extends MenuBaseFragment {
    private Fragment00Binding binding;
    private static final String TAG = "Menu00Fragment";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_00, container, false);
        setOptionMenu(true);
        return binding.getRoot();
    }

    @Override
    public void setOptionMenu(boolean hasMenu) {
        setOptionMenu(hasMenu, new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.main00, menu);
                menu.findItem(R.id.action_home).setVisible(false);
                menu.findItem(R.id.action_share_me).setVisible(false);
                menu.findItem(R.id.action_rate_us).setVisible(false);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.action_rate_us){
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public String getScreenName() {
        return getString(R.string.menu_nav_home);
    }

    @Override
    public void initComponent() {
        binding.textViewHome.setText(getScreenName());
        binding.textViewHome.setVisibility(View.GONE);
        setupDashboard();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(getScreenName());
    }

    private void setupDashboard() {

        DashBoardManager dashBoardManager = new DashBoardManager();

        Setup setup    = new Setup();
        setup.setDebugLog(true);
        setup.setCountDisplay(true);
        setup.setImageDisplay(true);
        setup.setDescriptionDisplay(false);
        dashBoardManager.setSetup(setup);

         ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(getContext(),"content_dashboard.json", false);
        dashBoardManager.setupDashboard(getContext(),dashBoardManager.getGridLayout(binding.childBoardGrid),3,dashBoardItems,dashboardClickListener);
    }

    DashboardClickListener dashboardClickListener = (v, dashBoardItem) -> {
        Timber.tag(TAG).d("onClick: %s- %s ",dashBoardItem.getId(), dashBoardItem.getName());
        ((MainActivity) requireActivity()).displayMessage(dashBoardItem.getName());
        if(null != dashBoardItem.getChilds()){
            String children = new Gson().toJson(dashBoardItem.getChilds());
            Timber.tag(TAG).d("children: %s  ",children);
            Timber.tag(TAG).d("onClick: %s- %s ",dashBoardItem.getId(), dashBoardItem.getName());
        }
    };

}