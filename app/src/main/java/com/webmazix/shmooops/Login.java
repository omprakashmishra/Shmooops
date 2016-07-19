package com.webmazix.shmooops;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.neopixl.pixlui.components.textview.TextView;
import com.webmazix.shmooops.R;
import com.facebook.FacebookSdk;
import com.webmazix.shmooops.ShmooopsVender.VenderHome;
import com.webmazix.shmooops.Tech.Settings.BaseActivity;
import com.webmazix.shmooops.Tech.utils.CallWebService;
import com.webmazix.shmooops.Tech.utils.MyServiceListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Login extends BaseActivity implements MyServiceListener {

    public static SharedPreferences preference;
    CallWebService myservice;


    @Bind(R.id.ET_email)
    com.neopixl.pixlui.components.edittext.EditText ETEmail;
    @Bind(R.id.ET_password)
    com.neopixl.pixlui.components.edittext.EditText ETPassword;
    @Bind(R.id.loginAs)
    Switch loginAs;
    @Bind(R.id.TV_forgetpass)
    TextView TVForgetpass;
    @Bind(R.id.TV_login)
    TextView TVLogin;
    @Bind(R.id.TV_signup)
    TextView TVSignup;

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    public static String login_to="user";
    //=============================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.login);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.loginAs, R.id.TV_forgetpass, R.id.TV_login, R.id.TV_signup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginAs:
                if(login_to.equals("user")){
                    login_to="vendor" ;
                    loginAs.setText("Login as Vendor");
                }else if(login_to.equals("vendor")){
                    login_to="user" ;
                    loginAs.setText("Login as User");
                }
                break;
            case R.id.TV_forgetpass:
                ForgetPaasword();
                break;
            case R.id.TV_login:
                 if (isInternetAvailable()) {
                     if (TextUtils.isEmpty(ETEmail.getText().toString()))
                         ETEmail.setError("Please Enter Name");
                     else if (TextUtils.isEmpty(ETPassword.getText().toString()))
                         ETPassword.setError("Please Enter User Name");
                    else {
                         if(login_to.equals("user")) {
                             myservice = new CallWebService(this, urlList.LOGIN, commonFunctions.Login(ETEmail.getText().toString(), ETPassword.getText().toString()), this);
                         }else if(login_to.equals("vendor")){
                             //myservice = new CallWebService(this, urlList.LOGIN, commonFunctions.Login(ETEmail.getText().toString(), ETPassword.getText().toString()), this);
                             GotoNextActivity(this,VenderHome.class);
                         }
                     }
                 }
                else
                 {
                  Tst(this, "Internet Connection Failed !");
                  }
                break;
            case R.id.TV_signup:
                GotoNextActivity(this,Registration.class);
                break;
        }
    }

    //-----------------------------------------------------------------forget password
    private void ForgetPaasword() {
        Context context=this;
        LayoutInflater LI = LayoutInflater.from(this);
        final View ForgetPasswordView = LI.inflate(R.layout.custom_view, null, false);
       final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);

        Effectstype effect = Effectstype.SlideBottom;

        dialogBuilder
                .withTitle("Forget Password")                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor("#ec4103")                               //def  | withDialogColor(int resid)                               //def
                //.withIcon(context.getResources().getDrawable(R.drawable.ic_like))
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(700)                                          //def
                .withEffect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("Submit")                                      //def gone
                .withButton2Text("Cancel")                                  //def gone
                .setCustomView(ForgetPasswordView, context)         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText ETemail=(EditText)ForgetPasswordView.findViewById(R.id.ET_email);
                        String email=ETemail.getText().toString();
                        //set error message

                        if(TextUtils.isEmpty(email))
                        {
                            ETemail.setError("Email is incorrect");
                            return;
                        }else{
                        // call service listener..
                            if (isInternetAvailable()) {
                                myservice = new CallWebService(Login.this, urlList.ForgetPassword, commonFunctions.ForgetPassword("om@webmazix.com"),Login.this);
                            }
                        }
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }

   //-------------------------------------------------------------------FACEBOOK BUTTON CLICK..

    private void facebook_bt_click() {
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                TVSignup.setText("User ID: "+ loginResult.getAccessToken().getUserId()
                        + "\n" +"Auth Token: "+ loginResult.getAccessToken().getToken() );

            }
            @Override
            public void onCancel() {
                TVSignup.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                TVSignup.setText("Login attempt failed.");
            }
        });
    }

    //-----------------------------------------------------------------service listener
    @Override
    public void onSuccess(String string) {
        Tst(this,string);
        //GotoNextActivity(this, Home.class);
    }

    @Override
    public void onFailed() {
        Tst(this,"Please Try Again !");
        //GotoNextActivity(this, Home.class);
    }

}
