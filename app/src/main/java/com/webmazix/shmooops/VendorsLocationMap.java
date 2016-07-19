package com.webmazix.shmooops;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.webmazix.shmooops.Models.VendorLocationModel;
import com.webmazix.shmooops.Tech.Settings.BaseActivity;
import com.webmazix.shmooops.adapters.VendorLocationAdapter;
import com.webmazix.shmooops.R;


import java.util.ArrayList;

public class VendorsLocationMap extends BaseActivity {


    private VendorLocationAdapter vendorLocationAdapter;
    private RecyclerView vendorsRV;
    private ArrayList<VendorLocationModel> vendorLocationModels;
    private Toolbar toolbar;
    private AppBarLayout appbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors_location_map);
        InitView();
    }

    private void InitView() {
        //initToolbar();
        //InitMap();

        vendorsRV = (RecyclerView) findViewById(R.id.vendorsRV);
        vendorsRV.setLayoutManager(new LinearLayoutManager(this));
        vendorLocationModels = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            VendorLocationModel vendorLocationModel = new VendorLocationModel();
            vendorLocationModel.setName("Adams Shop");
            vendorLocationModel.setAddress("23 Town St");
            vendorLocationModel.setDistance("2");
            vendorLocationModel.setImage_url("http://www.pachd.com/free-images/food-images/strawberries-02.jpg");

            vendorLocationModels.add(vendorLocationModel);
        }

        vendorLocationAdapter = new VendorLocationAdapter(this, vendorLocationModels);
        vendorsRV.setAdapter(vendorLocationAdapter);
    }

    private void initToolbar() {

        /*searchHeader = (SearchHeaderView) findViewById(R.id.searchHeader);
        searchHeader.InitViews(this);*/
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        /*behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return false;
            }
        });*/
        layoutParams.setBehavior(behavior);
    }

    private void InitMap() {
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);*/


    }



}
