package com.webmazix.shmooops.HomeFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.webmazix.shmooops.R;


public class PetsFragment extends Fragment {
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fr_pets, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitViews();
    }

    private void InitViews() {
        // tabs = (TabLayout) getView().findViewById(R.id.tabs);
        //  viewPager = (ViewPager) getView().findViewById(R.id.viewPager);

        // ConfigViewPager();

    }
}
