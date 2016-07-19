package com.webmazix.shmooops.Tech.utils;

import android.content.Context;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Krishna Computers on 23-06-2016.
 */
public class CommonFunctions {

    private Context context;
    public CommonFunctions(Context context) {
        this.context = context;
    }

    public Map<String, String> Login(String users_name, String users_password) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("users_name", users_name);
        params.put("users_password", users_password);
        return params;
    }
    public Map<String, String> SignUp(String name,String user_name, String user_password,String user_email,String user_phone) {
        HashMap<String, String> params = new HashMap<String, String>();
       // params.put("name", name);
        params.put("user_name", user_name);
        params.put("user_password", user_password);
        params.put("user_phone", user_phone);
        params.put("user_email", user_email);
        return params;
    }
    public Map<String, String> ForgetPassword(String users_name) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", users_name);
        return params;
    }
    public Map<String, String> Access(String aa) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("accesstoken", "om@webmazix.com");
        return params;
    }

}
