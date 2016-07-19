package com.webmazix.shmooops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.neopixl.pixlui.components.textview.TextView;

import com.webmazix.shmooops.Models.VendorLocationModel;
import com.webmazix.shmooops.R;
import com.webmazix.shmooops.Tech.utils.ImageLoader;


import java.util.ArrayList;

/**
 * Created by Lenovo on 01-04-2016.
 */
public class VendorLocationAdapter extends RecyclerView
        .Adapter<VendorLocationAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<VendorLocationModel> mDataset;
    private Context context;
    private ImageLoader imageLoading;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {


        TextView txtFarmName, txtAddress, txtDistance;
        ImageView profileImage;

        public DataObjectHolder(View itemView, int viewType) {
            super(itemView);
            profileImage = (ImageView ) itemView.findViewById(R.id.profileImage);
            txtFarmName = (TextView) itemView.findViewById(R.id.txtFarmName);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);

            Log.i(LOG_TAG, "Adding Listener");

        }


    }

    public VendorLocationAdapter(Context context, ArrayList<VendorLocationModel> myDataset) {
        this.context = context;
        mDataset = myDataset;
        imageLoading = new ImageLoader(context);
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vendor_location_adapter, parent, false);


        DataObjectHolder dataObjectHolder = new DataObjectHolder(view, viewType);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        VendorLocationModel vendorLocationModel = mDataset.get(position);
        imageLoading.DisplayImage(vendorLocationModel.getImage_url(), holder.profileImage);
        holder.txtFarmName.setText(vendorLocationModel.getName());
        holder.txtAddress.setText(vendorLocationModel.getAddress());
        holder.txtDistance.setText(vendorLocationModel.getDistance() + " mi");
    }

    public void addItem(VendorLocationModel dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
