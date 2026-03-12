/*
 * Created by: V3RMA SOURAV on 08/03/24, 3:18 pm
 * Copyright © 2023 All rights reserved
 * Class name : Menu00Fragment
 * Last modified:  08/03/24, 3:18 pm
 * Location: Bangalore, India
 *
 */

package com.verma.android.ramcharitmanas.ui.menu;

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
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;
import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.DashBoardManager;
import com.verma.android.dashboard.DashboardClickListener;
import com.verma.android.dashboard.Setup;
import com.verma.android.ramcharitmanas.R;
import com.verma.android.ramcharitmanas.databinding.Fragment00Binding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
                //TODO Add new Menu
                // menuInflater.inflate(R.menu.main00, menu);
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
        setupDashboard();
    }

    private void setupDashboard() {
        DashBoardManager dashBoardManager = new DashBoardManager();

        Setup setup  = new Setup();
        setup.setDebugLog(false);
        setup.setCountDisplay(true);
        setup.setImageDisplay(true);
        setup.setDescriptionDisplay(false);
        dashBoardManager.setSetup(setup);

        int homeRowItemsCount = getResources().getInteger(R.integer.home_row_items_count);
        boolean isShorting = false;
        if(homeRowItemsCount == 1){
            isShorting = true;
        }
        ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(getContext(),"content_dashboard.json",false);
        if(isShorting){
            Collections.sort(dashBoardItems, Comparator.comparing(o -> o.getName().toLowerCase()));
        }
        dashBoardManager.setupDashboard(getContext(),binding.dashBoardGrid, homeRowItemsCount ,dashBoardItems,dashboardClickListener);
    }

    DashboardClickListener dashboardClickListener = (v, dashBoardItem) -> {

        if(dashBoardItem.getChilds() != null){
            Timber.tag(TAG).d("onClick: " + dashBoardItem.getId() + "- " + dashBoardItem.getName());
            Menu00FragmentDirections.ActionNavHomeToNavOne action = Menu00FragmentDirections.actionNavHomeToNavOne();
            action.setArgHeader(dashBoardItem.getName());
            action.setArgDescription(dashBoardItem.getDescription());
            action.setArgChilds(new Gson().toJson(dashBoardItem.getChilds()));
            //action.setArgDescription(dashBoardItem.)
            NavHostFragment.findNavController(Menu00Fragment.this).navigate(action);
        }
    };

}