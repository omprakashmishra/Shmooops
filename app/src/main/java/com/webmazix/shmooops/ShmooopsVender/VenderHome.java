package com.webmazix.shmooops.ShmooopsVender;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import com.navdrawer.SimpleSideDrawer;
import com.webmazix.shmooops.R;
import com.webmazix.shmooops.Tech.Settings.BaseActivity;


public class VenderHome extends BaseActivity {
    private static FragmentManager fragmentManager;
    static SimpleSideDrawer slide_me;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vender_home);
        InitViews();
    }
    private void InitViews() {
        if (isInternetAvailable()){

        }
        slide_me = new SimpleSideDrawer(this);
        view = slide_me.setLeftBehindContentView(R.layout.vender_left_menu);
        fragmentManager = getSupportFragmentManager();
    }

    public void showMenu(View v)
    {
        slide_me.toggleLeftDrawer();
    }

//on click listener...
    public void Onclick_IV_send(View v){
        GotoNextActivity(this,VenderMessage.class);
    }
    public void Onclick_IV_delete(View v){

    }


}
