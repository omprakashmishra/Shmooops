package com.webmazix.shmooops.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import com.webmazix.shmooops.Models.OfferModel;
import com.webmazix.shmooops.R;
import com.webmazix.shmooops.Tech.Settings.MyApplication;

import java.util.Collections;
import java.util.List;

/**
 * Created by Krishna Computers on 11-07-2016.
 */
public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.MyViewHolder> {
    public MyApplication myApplication;
    private LayoutInflater LIoffer;
    DisplayImageOptions options;
    List<OfferModel> categorylist= Collections.emptyList();

    public HomeCategoryAdapter(Context mcontext, List<OfferModel> data) {
        LIoffer=LayoutInflater.from(mcontext);
        this.categorylist=data;

        myApplication=new MyApplication();
        myApplication.loadimg(mcontext);

        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.profile_bg)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(170))
                .build();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return categorylist.size();
    }

}
