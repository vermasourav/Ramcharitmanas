package com.verma.android.template.ui.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.databinding.DataBindingUtil;

import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.DashBoardManager;
import com.verma.android.template.R;
import com.verma.android.template.databinding.Fragment01Binding;

import java.util.ArrayList;

import timber.log.Timber;


public class Menu01Fragment extends MenuBaseFragment {

    private Fragment01Binding binding;
    private static final String TAG = "Menu01Fragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setOptionMenu(false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_01, container, false);
        //binding = Fragment01Binding.inflate(inflater, container, false);

        return binding.getRoot();
    }
    @Override
    public String getScreenName() {
        return getString(R.string.menu_nav_one);
    }

    @Override
    public void initComponent() {
        binding.textOne.setText(getScreenName());
        binding.textOne.setVisibility(View.GONE);
        intExpendedList();
    }

    @Override
    public void setOptionMenu(boolean hasMenu) {
        setOptionMenu(hasMenu, new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                //Do Nothing
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
    }

    private void intExpendedList() {
        binding.expandableListview.setVisibility(View.VISIBLE);
        binding.expandableListview.isWithImage(true);
        binding.expandableListview.isWithSorting(false);
        binding.expandableListview.isWithChildArrow(true);
        binding.expandableListview.withChildMode(0);

        binding.expandableListview.setGroupClickListener((group, groupPos) -> {
            binding.expandableListview.getGroups().get(groupPos);
            ((MainActivity) requireActivity()).displayMessage(group.getName());
            Timber.tag(TAG).d("Group Clicked: You clicked : %s", group.getName());
        });

        binding.expandableListview.setChildClickListener((child, groupPos, childPos, header) -> {
            Timber.tag(TAG).d("Child Clicked: You clicked : " + "[" + groupPos + "," + childPos + "] " + child.getChildName());
            ((MainActivity) requireActivity()).displayMessage(child.getChildName());
        });

        DashBoardManager dashBoardManager = new DashBoardManager();
        ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(getActivity(),"content_dashboard.json");
        binding.expandableListview.doUpdate(dashBoardItems);

    }
}