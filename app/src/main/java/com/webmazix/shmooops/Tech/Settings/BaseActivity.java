package com.webmazix.shmooops.Tech.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;
import com.webmazix.shmooops.HomeFragments.HomeParentFragment;
import com.webmazix.shmooops.R;
import com.webmazix.shmooops.Tech.utils.AlertMessage;
import com.webmazix.shmooops.Tech.utils.CommonFunctions;
import com.webmazix.shmooops.Tech.utils.ConnectionDetector;
import com.webmazix.shmooops.Tech.utils.UrlList;


import java.net.URL;

/**
 * Created by Lenovo on 18-03-2016.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    public static ConnectionDetector cdr;
    public  SharedPreferences sharedPreferences;
    public static CommonFunctions commonFunctions;
    public static UrlList urlList;
    public static AlertMessage alertMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
            setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();
        cdr = new ConnectionDetector(this);
        sharedPreferences = MyApplication.preference;
        commonFunctions = new CommonFunctions(this);
        alertMessage=new AlertMessage();
    }

    public boolean isInternetAvailable() {
        return cdr.isConnectingToInternet();
       // return false;
    }

    public static void updateFragment(Fragment fragment) {
        String name = fragment.getClass().getName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment != null) {
            fragmentTransaction.replace(R.id.mainFrameContainer, fragment);
            if (!(fragment instanceof HomeParentFragment))
                fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void GotoNextActivity(Activity currentActivity, Class nextActivity) {
        Intent intent = new Intent(currentActivity, nextActivity);
        startActivity(intent);
    }
    public void GotoNextActivityClearTop(Activity currentActivity, Class nextActivity) {
        Intent intent = new Intent(currentActivity, nextActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    public void Tst(Context ctx,String msg) {
        // Call Toast to show msg
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }
}
