package com.webmazix.shmooops;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.navdrawer.SimpleSideDrawer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import com.webmazix.shmooops.HomeFragments.LikeFragment;
import com.webmazix.shmooops.HomeFragments.LocationSearchFragment;
import com.webmazix.shmooops.HomeFragments.ProfileFragment;
import com.webmazix.shmooops.HomeFragments.SearchFragment;
import com.webmazix.shmooops.HomeFragments.HomeParentFragment;
import com.webmazix.shmooops.R;
import com.webmazix.shmooops.Tech.Settings.BaseActivity;


public class Home extends BaseActivity {

    private Toolbar toolbar;
    static SimpleSideDrawer slide_me;
    DisplayImageOptions options;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        InitViews();
    }

    private void InitViews() {
        if (isInternetAvailable()){

        }
        slide_me = new SimpleSideDrawer(this);
        view = slide_me.setLeftBehindContentView(R.layout.left_menu);
        updateFragment(new HomeParentFragment());

    }

    public void ChangeTab(View view) {
        switch (view.getId()) {
            case R.id.petTab:
                updateFragment(new HomeParentFragment());
                break;
            case R.id.searchTab:
                updateFragment(new SearchFragment());
                break;
            case R.id.likeTab:
                updateFragment(new LikeFragment());
                break;
            case R.id.calenderTab:
                updateFragment(new LikeFragment());
                break;
            case R.id.profileTab:
                updateFragment(new ProfileFragment());
                break;
        }
    }

    public void showMenu(View v)
    {
        slide_me.toggleLeftDrawer();
    }

    //go to location search fragment...
    public void Onclick_IV_location(View v){
        updateFragment(new LocationSearchFragment());
    }

}
