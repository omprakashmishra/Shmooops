package com.webmazix.shmooops.HomeFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.webmazix.shmooops.ClickListener.RecyclerItemClickListener;

import com.webmazix.shmooops.Models.OfferModel;
import com.webmazix.shmooops.Tech.Settings.GlobalFragment;
import com.webmazix.shmooops.Tech.utils.CallWebService;
import com.webmazix.shmooops.adapters.HomeOfferAdapter;
import com.webmazix.shmooops.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;


public class HomeParentFragment extends GlobalFragment {

    private ViewPager viewPager;
    public CallWebService callWebService;
    private RecyclerView RV_categories,RV_OFFERS,RV_recentallybooked;
    public HomeOfferAdapter homeOfferAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_parent_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitViews();
    }

    private void InitViews() {
        RV_categories=(RecyclerView)getView().findViewById(R.id.RV_categories);
        RV_OFFERS=(RecyclerView)getView().findViewById(R.id.RV_offers);
        RV_recentallybooked=(RecyclerView)getView().findViewById(R.id.RV_recentallybooked);

       // set for horizontally scroll
        LinearLayoutManager CategoryLM
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager OfferLM
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager RecentlyBLM
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);


        //1.layout manager for CATEGORY...
        RV_categories.setLayoutManager(CategoryLM);
        //2.layout manager for OFFER RV
        RV_OFFERS.setLayoutManager(OfferLM);
        //3.layout manager for RECENTLY BOOKED SERVICES
        RV_recentallybooked.setLayoutManager(RecentlyBLM);

        // initialize for image loader class

       //call
        callWebService=new CallWebService(getActivity(),urlList.TEST,commonFunctions.Access("asa"),this);
    }
    //------------------------------------------------------------>service listener ....
    @Override
    public void onSuccess(String string) {
       super.onSuccess(string);
        BindHomeData(string);
    }
    @Override
    public void onFailed() {
        super.onFailed();
    }

    //------------------------------------------------------------>Click listner
    public void ClickOn_Location(View v){

    }

   public void ClickOn_OfferMore(View v){

    }

    public void ClickOn_BookedMore(View v){

    }

    //==============================**********************Bind data in HomeParentFragment *****==========
    public void BindHomeData(String json_api){

        //1. List one for category data..
        final List<OfferModel> CategoryList=new ArrayList<>();
        //2. List one for offer data..
        final List<OfferModel> OfferList=new ArrayList<>();
        //3. List two for Recent Booked service..
        final List<OfferModel> RecentlyBookedList=new ArrayList<>();
        //=====================================
        JSONObject rootJsonObj = null;
        try {
            rootJsonObj = new JSONObject(json_api);

            JSONArray wArray = rootJsonObj.optJSONArray("worldpopulation");

            //********** bind data in CategoryList
            for (int i = 0; i < wArray.length(); i++) {
                JSONObject petsJsonObj = wArray.optJSONObject(i);

                String rank = petsJsonObj.optString("rank");
                String country=petsJsonObj.optString("country");
                String flag = petsJsonObj.optString("flag");
                // .....................................bind in offer layout...
                OfferModel current=new OfferModel();
                current.id = rank;
                //current.title = country;
                current.title = "Rupesh";
                current.image = flag;
                CategoryList.add(current);
                // Toast.makeText(this, rank+population+flag, Toast.LENGTH_SHORT).show();
            }

            //**********bind data in OfferList
            for (int i = 0; i < wArray.length(); i++) {
                JSONObject petsJsonObj = wArray.optJSONObject(i);

                String rank = petsJsonObj.optString("rank");
                String country=petsJsonObj.optString("country");
                String flag = petsJsonObj.optString("flag");

                // .....................................bind in offer layout...
                OfferModel current=new OfferModel();
                current.id = rank;
                current.title = country;
                current.off = " 20% OFF ";
                current.image = flag;
                OfferList.add(current);
                // Toast.makeText(this, rank+population+flag, Toast.LENGTH_SHORT).show();
            }
            //**********bind data in Recently booked
            for (int i = 0; i < wArray.length(); i++) {
                JSONObject petsJsonObj = wArray.optJSONObject(i);

                String rank = petsJsonObj.optString("rank");
                String country=petsJsonObj.optString("country");
                String flag = petsJsonObj.optString("flag");

                // .....................................bind in offer layout...
                OfferModel current=new OfferModel();
                current.id = rank;
                //current.title = country;
                current.title = "Om";
                current.image = flag;
                RecentlyBookedList.add(current);
                // Toast.makeText(this, rank+population+flag, Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //1. Adapter for category
        homeOfferAdapter=new HomeOfferAdapter(getActivity(),CategoryList);
        RV_categories.setAdapter(homeOfferAdapter);

        //2. Adapter for offer
        homeOfferAdapter=new HomeOfferAdapter(getActivity(),OfferList);
        RV_OFFERS.setAdapter(homeOfferAdapter);

        //3. Adapter for Recent Booked
        homeOfferAdapter=new HomeOfferAdapter(getActivity(),RecentlyBookedList);
        RV_recentallybooked.setAdapter(homeOfferAdapter);

        //=========================RV_categories click listener....
        RV_categories.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), RV_categories, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // ...String.valueOf(position)
                // String abc=OfferList.get(1);
                Toast.makeText(getActivity(), CategoryList.get(Integer.parseInt(String.valueOf(position))).title
                        , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        }));
        //=========================RV_OFFERS click listener....
        RV_OFFERS.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), RV_OFFERS, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // ...String.valueOf(position)
                // String abc=OfferList.get(1);
                Toast.makeText(getActivity(), OfferList.get(Integer.parseInt(String.valueOf(position))).title
                        , Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onItemLongClick(View view, int position) {
                // ...
                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

        }));
        //=========================RV_recentallybooked click listener....
        RV_recentallybooked.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), RV_recentallybooked, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // ...String.valueOf(position)
                // String abc=OfferList.get(1);
                Toast.makeText(getActivity(), RecentlyBookedList.get(Integer.parseInt(String.valueOf(position))).title
                        , Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onItemLongClick(View view, int position) {
                // ...
                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

        }));
        //============================

    }

}
