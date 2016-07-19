package com.webmazix.shmooops.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import com.webmazix.shmooops.Models.RvLocationSearchModel;
import com.webmazix.shmooops.R;
import com.webmazix.shmooops.Tech.Settings.MyApplication;
import com.webmazix.shmooops.Tech.utils.ImageLoader;

import java.util.Collections;
import java.util.List;

/**
 * Created by Krishna Computers on 13-07-2016.
 */
public class LocationSearchAdapter extends RecyclerView.Adapter<LocationSearchAdapter.MyViewHolder> {

    public MyApplication myApplication;
    private LayoutInflater LIoffer;
    DisplayImageOptions options;
    private ImageLoader imageLoading;
    List<RvLocationSearchModel> PetLocationList= Collections.emptyList();

    public LocationSearchAdapter(Context mcontext,List<RvLocationSearchModel> data) {
        LIoffer=LayoutInflater.from(mcontext);
        this.PetLocationList=data;

        myApplication=new MyApplication();
        imageLoading = new ImageLoader(mcontext);
       /* myApplication.loadimg(mcontext);

        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.profile_bg)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(170))
                .build();*/
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView TV_PetName,TV_reviews,TV_PetAdress,TV_PetServices,BT_expressBook,Bt_bookNow;
        public ImageView IV_PetLocation,IV_LikeDislike;

        public MyViewHolder(View itemView) {
            super(itemView);
            IV_PetLocation= (ImageView) itemView.findViewById(R.id.IV_PetLocation);
            IV_LikeDislike= (ImageView) itemView.findViewById(R.id.IV_LikeDislike);
            TV_PetName= (TextView) itemView.findViewById(R.id.TV_PetName);
            TV_reviews= (TextView) itemView.findViewById(R.id.TV_reviews);
            TV_PetAdress= (TextView) itemView.findViewById(R.id.TV_PetAdress);
            TV_PetServices= (TextView) itemView.findViewById(R.id.TV_PetServices);
            BT_expressBook= (TextView) itemView.findViewById(R.id.BT_expressBook);
            Bt_bookNow= (TextView) itemView.findViewById(R.id.Bt_bookNow);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LIoffer.inflate(R.layout.item_location_search,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }


    //bind date in view
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    RvLocationSearchModel ModelList=PetLocationList.get(position);

       // Utilities.downloadImage(ModelList.PetLocation_image, holder.IV_PetLocation, options, null);
        //add if like is 0 and one then set locally like dislike image..
        holder.TV_PetName.setText(ModelList.PetName);
        holder.TV_reviews.setText(ModelList.Reviews);
        holder.TV_PetAdress.setText(ModelList.PetAdress);
        holder.TV_PetServices.setText(ModelList.PetServices);
        holder.BT_expressBook.setText(ModelList.expressBook);
        holder.Bt_bookNow.setText(ModelList.bookNow);

    }

    @Override
    public int getItemCount() {
        return PetLocationList.size();
    }



}
