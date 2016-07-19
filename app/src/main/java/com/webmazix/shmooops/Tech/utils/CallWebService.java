package com.webmazix.shmooops.Tech.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by Krishna Computers on 29-06-2016.
 */
public class CallWebService {

    private MyServiceListener myListener;    private Context mycontext;
    private String murl;
    private Map<String,String> map_vlaue;
    private ProgressDialog progressbar;

    public CallWebService(Context context ,String url, Map<String,String> map,MyServiceListener Listener) {
        super();
        this.mycontext=context;
        this.murl=url;
        this.map_vlaue=map;
        this.myListener=Listener;

        progressbar = new ProgressDialog(mycontext);
        progressbar.setMessage("Loading...");
        progressbar.setCancelable(true);
        progressbar.show();

        webservices();
    }

    private void webservices() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, murl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //-------------------------------------------------------------------------->Response Listener
                        progressbar.dismiss();
                        myListener.onSuccess(response);

                       /* if(response.trim().equals("success")){
                           // openProfile();
                        }else{
                           // Toast.makeText(getApplication(), response, Toast.LENGTH_LONG).show();
                        }*/

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //-------------------------------------------------------------------------->error Listener
                        progressbar.dismiss();
                        myListener.onFailed();
                        //  Toast.makeText(getApplication(),error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // map_vlaue = new HashMap<String,String>();
                return map_vlaue;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mycontext);
        requestQueue.add(stringRequest);
    }
}
