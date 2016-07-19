package com.webmazix.shmooops.Tech.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;

import android.widget.Toast;

import com.webmazix.shmooops.HomeFragments.HomeParentFragment;
import com.webmazix.shmooops.R;
import com.webmazix.shmooops.Tech.utils.AlertMessage;
import com.webmazix.shmooops.Tech.utils.CommonFunctions;
import com.webmazix.shmooops.Tech.utils.ConnectionDetector;
import com.webmazix.shmooops.Tech.utils.MyServiceListener;
import com.webmazix.shmooops.Tech.utils.UrlList;

/**
 * Created by Krishna Computers on 12-07-2016.
 */
public abstract class GlobalFragment extends Fragment implements MyServiceListener {

     public static ConnectionDetector cdr;
     public SharedPreferences sharedPreferences;
     public static CommonFunctions commonFunctions;
     public static UrlList urlList;
     public static AlertMessage alertMessage;


 @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    cdr = new ConnectionDetector(getActivity());
    sharedPreferences = MyApplication.preference;
    commonFunctions = new CommonFunctions(getActivity());
    alertMessage=new AlertMessage();

 }


    @Override
    public void onSuccess(String string) {

    }

    @Override
    public void onFailed() {

    }


     public void Tst(Context ctx,String msg) {
      // Call Toast to show msg
      Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
     }
    public void updateFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (fragment != null) {

            fragmentTransaction.replace(getActivity().findViewById(R.id.mainFrameContainer).getId(), fragment);
            if (!(fragment instanceof HomeParentFragment))
                fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    //==========Scope Closed======
}
