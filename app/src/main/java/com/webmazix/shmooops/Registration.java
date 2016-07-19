package com.webmazix.shmooops;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;
import com.webmazix.shmooops.R;
import com.webmazix.shmooops.Tech.Settings.BaseActivity;
import com.webmazix.shmooops.Tech.utils.CallWebService;
import com.webmazix.shmooops.Tech.utils.MyServiceListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Registration extends BaseActivity implements MyServiceListener {

    @Bind(R.id.ET_name)
    EditText ETName;
    @Bind(R.id.ET_UserName)
    EditText ETUserName;
    @Bind(R.id.ET_password)
    EditText ETPassword;
    @Bind(R.id.ET_Mail)
    EditText ETMail;
    @Bind(R.id.ET_Mobile)
    EditText ETMobile;
    @Bind(R.id.BT_SignUp)
    TextView BTSignUp;
    @Bind(R.id.BT_signin)
    TextView BTSignin;
//====================================================
    public static SharedPreferences preference;
    CallWebService myservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.BT_SignUp, R.id.BT_signin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BT_SignUp:
                ValidateFuntion();
                break;
            case R.id.BT_signin:
                GotoNextActivity(this,Login.class);
                break;
        }
    }


    // After Registration button click...
    private void ValidateFuntion() {
        if (isInternetAvailable()) {
            if (TextUtils.isEmpty(ETName.getText().toString()))
                ETName.setError("Please Enter Name");
            else if (TextUtils.isEmpty(ETUserName.getText().toString()))
                ETUserName.setError("Please Enter User Name");
            else if (TextUtils.isEmpty(ETPassword.getText().toString()))
                ETPassword.setError("Please Enter Password");
            else if (TextUtils.isEmpty(ETMail.getText().toString()) || alertMessage.isEmailValid(ETMail.getText().toString()) == false)
                ETMail.setError("Please Enter Correct Email");
            else if (TextUtils.isEmpty(ETMobile.getText().toString()) || ETMobile.getText().toString().length() <= 5)
                ETMobile.setError("Please Enter Correct Phone No");
            else {
                myservice = new CallWebService(this, urlList.REGISTER, commonFunctions.SignUp(ETName.getText().toString(),ETUserName.getText().toString(),ETPassword.getText().toString(),ETMail.getText().toString(),ETMobile.getText().toString()), this);
            }
        } else {
            Tst(this, "Internet Connection Failed !");
        }
    }

    @Override
    public void onSuccess(String string) {
        Tst(this, string);
    }

    @Override
    public void onFailed() {

    }

}
