package com.webmazix.shmooops.ShmooopsVender;

import android.os.Bundle;
import android.view.View;

import com.webmazix.shmooops.R;
import com.webmazix.shmooops.Tech.Settings.BaseActivity;


public class VenderMessage extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vender_message);
    }
    //on click
    public void back(View v){
        finish();
    }

}
