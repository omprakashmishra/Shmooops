package com.webmazix.shmooops.HomeFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.webmazix.shmooops.R;
import com.webmazix.shmooops.Tech.Settings.GlobalFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapVenderFragment extends GlobalFragment {


    public MapVenderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fr_map_vender, container, false);
    }

}
