package com.webmazix.shmooops.HomeFragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.webmazix.shmooops.ClickListener.RecyclerItemClickListener;
import com.webmazix.shmooops.Models.LatLongModel;
import com.webmazix.shmooops.Models.RvLocationSearchModel;
import com.webmazix.shmooops.Tech.Settings.GlobalFragment;
import com.webmazix.shmooops.Tech.utils.CallWebService;
import com.webmazix.shmooops.VendorsLocationMap;
import com.webmazix.shmooops.adapters.LocationSearchAdapter;
import com.webmazix.shmooops.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationSearchFragment extends GlobalFragment implements OnMapReadyCallback
        ,TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{

    @Bind(R.id.tvback)
    com.neopixl.pixlui.components.textview.TextView tvback;
    @Bind(R.id.PetSearchSpinner)
    Spinner PetSearchSpinner;
    @Bind(R.id.TV_SerachCity)
    TextView TVSerachCity;
    @Bind(R.id.TV_SearchDate)
    TextView TVSearchDate;
    @Bind(R.id.TV_SearchTime)
    TextView TVSearchTime;
    @Bind(R.id.TVmap)
    TextView TvMap;
    @Bind(R.id.TV_search)
    TextView TVSearch;
    @Bind(R.id.RV_PetsDetails)
    RecyclerView RVPetsDetails;
    @Bind(R.id.LL_Map)
    LinearLayout LLMap;


    CallWebService myservice;
    RelativeLayout RL_back;
    public LocationSearchAdapter locationSearchAdapter;
    final List<RvLocationSearchModel> LocationSearchList=new ArrayList<>();
    String[] vals = { "here", "are", "some", "values" };


    //requiered item for map...
    public static String listOrMap="map";
    private GoogleMap mMap;
    SupportMapFragment mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_location_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitViews();
    }

    private void InitViews() {
        // tabs = (TabLayout) getView().findViewById(R.id.tabs);
        //  viewPager = (ViewPager) getView().findViewById(R.id.viewPager);
        // ConfigViewPager();
        LinearLayoutManager layout
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RVPetsDetails.setLayoutManager(layout);

         RL_back= (RelativeLayout) getView().findViewById(R.id.RL_back);
        RL_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        //hit api as per required
        myservice = new CallWebService(getActivity(), urlList.TEST, commonFunctions.ForgetPassword("om@gmail.com"), this);

    }

    @OnClick({R.id.TVmap, R.id.TV_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TVmap:
                if(listOrMap.equals("list")){
                    TvMap.setText("map");
                    listOrMap="map";
                    RVPetsDetails.setVisibility(View.VISIBLE);
                    LLMap.setVisibility(View.GONE);
                }

                // set visibility gone on first time click map...
                else if(listOrMap.equals("map")){
                    TvMap.setText("list");
                    listOrMap="list";
                    RVPetsDetails.setVisibility(View.GONE);
                    LLMap.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.TV_search:

                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //header back click listener


    //------------------------------------------------------>>>>>>>service lisner
    @Override
    public void onSuccess(String string) {
        super.onSuccess(string);
        BindHomeData(string);
        // Tst(getActivity(),string);
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(LocationSearchFragment.this);
    }

    @Override
    public void onFailed() {
        super.onFailed();
    }

    //-----------------------------------------------------------Date Time
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Tst(getActivity(), date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = "You picked the following time: "+hourOfDay+"h"+minute;
        Tst(getActivity(), time);
    }


    public void BindHomeData(String json_api){

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
                RvLocationSearchModel current=new RvLocationSearchModel();
                current.id = rank;
                current.PetLocation_image = flag;
                current.PetName = country;
                current.Reviews="(22 Reviews)";
                current.PetAdress="addess abscc";
                current.PetServices="Services adfasdf ";
                current.expressBook="Express Book";
                current.bookNow="Book Now";


                LocationSearchList.add(current);
                // Toast.makeText(this, rank+population+flag, Toast.LENGTH_SHORT).show();
            } } catch (JSONException e) {
            e.printStackTrace();
        }

        //1. Adapter for category
        locationSearchAdapter=new LocationSearchAdapter(getActivity(),LocationSearchList);
        RVPetsDetails.setAdapter(locationSearchAdapter);

        //add in spinner
        ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, vals);
        PetSearchSpinner.setAdapter(ad);

        //-----------------------RV_recentallybooked click listener....
        RVPetsDetails.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), RVPetsDetails, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // ...String.valueOf(position)
                // String abc=OfferList.get(1);
                Toast.makeText(getActivity(), LocationSearchList.get(Integer.parseInt(String.valueOf(position))).id
                        , Toast.LENGTH_SHORT).show();
                // on click list item row page will navigate to service info activity...same process on map pointer click
                Intent intent = new Intent(getActivity(), VendorsLocationMap.class);
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

        }));
        //--------------------------

    }


    /// map OnMapReadyCallback listener...
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
//=============Scope End=====
}
