package com.verma.android.template.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.verma.android.dashboard.DashBoardManager;
import com.verma.android.dashboard.DashBoardWindowItem;
import com.verma.android.dashboard.DashboardClickListener;
import com.verma.android.dashboard.Setup;
import com.verma.android.dashboard.expendview.ExpandableHelper;
import com.verma.android.dashboard.window.WindowAdapter;
import com.verma.android.template.R;
import com.verma.android.template.databinding.Fragment02Binding;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class Menu02Fragment extends MenuBaseFragment {

    private Fragment02Binding binding;
    private static final String TAG = "Menu02Fragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_02, container, false);
        setHasOptionsMenu(false);
        return binding.getRoot();
    }
    @Override
    public String getScreenName() {
        return getString(R.string.menu_nav_two);
    }
    @Override
    public void initComponent() {
        binding.textTwo.setText(getScreenName());
        binding.textTwo.setVisibility(View.GONE);
        initWindowDashboard();
    }

    @Override
    public void setOptionMenu(boolean hasMenu) {
        setOptionMenu(hasMenu, new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
    }

    private void initWindowDashboard() {
         //List<DashBoardWindowItem> windowItems = ExpandableHelper.getSampleWindowList(15);
        ArrayList<DashBoardWindowItem> windowItems = new DashBoardManager().getWindowsItems(getContext(), "content_dashboard_window.json");

        WindowAdapter windowAdapter = new WindowAdapter(getContext(), windowItems, dashboardClickListener);
        Setup setup = new Setup();
        setup.setDebugLog(false);
        setup.setCountDisplay(true);
        setup.setImageDisplay(false);
        setup.setDescriptionDisplay(true);

        windowAdapter.setSetup(setup);
        binding.windowRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.windowRecyclerView.setAdapter(windowAdapter);

        windowAdapter.setDashboardClickListener(dashboardClickListener);

    }

     DashboardClickListener dashboardClickListener = (v, dashBoardItem) -> {
        if (dashBoardItem.getChilds() != null) {
            ((MainActivity) requireActivity()).displayMessage(dashBoardItem.getName());
            Timber.tag(TAG).d("Group Clicked: You clicked : %s", dashBoardItem);
        }
    };


}