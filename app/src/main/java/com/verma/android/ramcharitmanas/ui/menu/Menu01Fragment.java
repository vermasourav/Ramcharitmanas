/*
 * Created by: V3RMA SOURAV on 08/03/24, 3:18 pm
 * Copyright © 2023 All rights reserved
 * Class name : Menu01Fragment
 * Last modified:  08/03/24, 10:53 am
 * Location: Bangalore, India
 *
 */

package com.verma.android.ramcharitmanas.ui.menu;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.DashBoardManager;
import com.verma.android.dashboard.DashboardClickListener;
import com.verma.android.dashboard.Setup;
import com.verma.android.dashboard.pojo.Child;
import com.verma.android.ramcharitmanas.R;
import com.verma.android.ramcharitmanas.databinding.Fragment01Binding;
import com.verma.android.widgets.readmore.AnotherReadMore;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import timber.log.Timber;


public class Menu01Fragment extends MenuBaseFragment {

    private static final String TAG = "Menu01Fragment";
    private Fragment01Binding binding;
    ArrayList<DashBoardItem> dashBoardItems = new ArrayList<>();
    String jsonString;
    String headerName = "";
    String description = "";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_01, container, false);

        if (getArguments() != null) {
            headerName = Menu01FragmentArgs.fromBundle(getArguments()).getArgHeader();
            description = Menu01FragmentArgs.fromBundle(getArguments()).getArgDescription();
            jsonString = Menu01FragmentArgs.fromBundle(getArguments()).getArgChilds();
            setModels(jsonString);
            Timber.tag(TAG).d("onCreateView: %s", dashBoardItems.size());
        }

        return binding.getRoot();
    }

    private void setModels(String yourJsonString) {
        try{
            Gson gson = new GsonBuilder().create();
            Type type = new TypeToken<List<Child>>() {}.getType();
            List<Child> dataModels  =   gson.fromJson(yourJsonString, type);
            dashBoardItems.clear();
            int count = 0;
            for (Child child : dataModels) {
                count ++;

                ArrayList<Child> verseAsChildItems = new ArrayList<>();
                if(count ==1){
                   // verseAsChildItems =   new VersedManager().getVerseAsChilds(getContext(),"01_balkanda.json",false);
                }

                //TODO Remove the ID in name
                String name = child.getChildName();
                name = name +"\n\n";
               // name = "- " + child.getChildId()+  " "+child.getChildName();

                if(child.isVisible()){
                    DashBoardItem item =
                        new DashBoardItem.DashBoardItemBuilder()
                                .setId(count)
                                .setURL("")
                                .setImage(R.drawable.ic_launcher)
                                .setId(child.getChildId())
                                .setName(name)
                                .setCount(count +"")
                                .setVisible(child.isVisible())
                                .setDescription(".")
                                .setChilds(verseAsChildItems)
                                .build();
                    dashBoardItems.add(item);
                }
            }
        }catch (Exception e){
            //DO Nothing
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
    }

    @Override
    public String getScreenName() {
        return getString(R.string.menu_nav_one);
    }

    public void initComponent() {
        if (!TextUtils.isEmpty(headerName)) {
            //TODO set subtitle
            setSubtitle(headerName);
            binding.textOne.setText(String.format(getString(R.string.child_header), headerName));
            setTitle(getString(R.string.app_name));
        }
        if (TextUtils.isEmpty(description)) {
            binding.tellMeMoreDescription.setVisibility(View.GONE);
        }else{
            binding.tellMeMoreDescription.setVisibility(View.VISIBLE);
            binding.tellMeMoreDescription.setText(description);

            AnotherReadMore anotherReadMore = new AnotherReadMore.Builder()
                    .moreLabel("More")
                    .lessLabel("Less")
                    .textLength(250, AnotherReadMore.TextLengthType.TYPE_CHARACTER)
                    .moreLabelColor(getContext().getColor(R.color.colorPrimary))
                    .lessLabelColor(getContext().getColor(R.color.colorPrimary))
                    .build();
            anotherReadMore.addReadMoreTo(binding.tellMeMoreDescription,description);
        }

        //TODO need to delete
       // setupExpendedList();

        setupChild();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private DashboardClickListener dashboardClickListener = (v, dashBoardItem) -> {
        if(dashBoardItem.getChilds() != null){
            Timber.tag(TAG).d("onClick: %s", dashBoardItem.getChilds().toString());
            doClick(dashBoardItem.getId(),dashBoardItem.getName());
        }
    };

    private void setupChild() {
        DashBoardManager dashBoardManager = new DashBoardManager();
        Setup setup  = new Setup();
        setup.setDebugLog(false);
        setup.setCountDisplay(true);
        setup.setImageDisplay(true);
        setup.setDescriptionDisplay(false);
        dashBoardManager.setSetup(setup);

        dashBoardItems.sort(Comparator.comparing(DashBoardItem::getId));
        View view = binding.getRoot();
        GridLayout gridLayout =  (view).findViewById(com.verma.android.dashboard.R.id.child_board_grid);
        dashBoardManager.setupDashboard(getContext(), gridLayout,3,dashBoardItems,dashboardClickListener);

    }

    private void doClick(Integer childId, String pName) {
        //TODO On Click Items
        ((MainActivity) requireActivity()).displayMessage(pName);

    }
    @Override
    public void setOptionMenu(boolean hasMenu) {
        setOptionMenu(hasMenu, new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                //DO Nothing
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
    }

    private void setupExpendedList() {
        binding.expandableListview.setVisibility(View.VISIBLE);
        dashBoardItems.sort(Comparator.comparing(DashBoardItem::getId));

        binding.expandableListview.isWithImage(false);
        binding.expandableListview.isWithSorting(false);
        binding.expandableListview.isWithChildArrow(true);
        binding.expandableListview.withChildMode(3);

        binding.expandableListview.setGroupClickListener((group, groupPos) -> {
            binding.expandableListview.getGroups().get(groupPos);
            Timber.tag(TAG).d("You clicked : %s", group.getName());
            //TODO Remove
            ((MainActivity) requireActivity()).displayMessage( group.getName());

        });

        binding.expandableListview.setChildClickListener((child, groupPos, childPos, header) -> {
            //TODO Remove
            ((MainActivity) requireActivity()).displayMessage(child.getChildName());
            Timber.tag(TAG).d("You clicked : %s", child.getChildName());
        });

        binding.expandableListview.doUpdate(dashBoardItems);

    }


}