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
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import com.webmazix.shmooops.Models.OfferModel;
import com.webmazix.shmooops.R;
import com.webmazix.shmooops.Tech.Settings.MyApplication;
import com.webmazix.shmooops.Tech.utils.Utilities;

import java.util.Collections;
import java.util.List;

/**
 * Created by Krishna Computers on 23-06-2016.
 */
public class HomeOfferAdapter extends RecyclerView.Adapter<HomeOfferAdapter.myViewHolder> {

    public MyApplication myApplication;
    private LayoutInflater LIoffer;
    DisplayImageOptions options;
    //private ImageLoader imageLoading;
    List<OfferModel> offerlist= Collections.emptyList();
    //offer adapter constructor
    public HomeOfferAdapter(Context mcontext, List<OfferModel> data) {
        LIoffer=LayoutInflater.from(mcontext);
        this.offerlist=data;

        myApplication=new MyApplication();
        //imageLoading = new ImageLoader(mcontext);

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

    // for hold view...
    public class myViewHolder extends RecyclerView.ViewHolder {
        // offer details...
        public TextView TV_title,TV_offer_percentage;
        public ImageView IV_offer;
        public myViewHolder(View itemView) {
            super(itemView);
            IV_offer= (ImageView) itemView.findViewById(R.id.offer_img);
            TV_title= (TextView) itemView.findViewById(R.id.offer_description);
            TV_offer_percentage= (TextView) itemView.findViewById(R.id.offer_on);
        }
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LIoffer.inflate(R.layout.hm_item_offer,parent,false);
        myViewHolder holder=new myViewHolder(view);
        return holder;
    }

    ///set all data in ...
    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        OfferModel  offer=offerlist.get(position);
        holder.TV_title.setText(offer.title);
        holder.TV_offer_percentage.setText(offer.off);

       Utilities.downloadImage(offer.image, holder.IV_offer, options, null);
        //imageLoading.DisplayImage(offer.image, holder.IV_offer);
        // myApplication.initImageLoader();
        //holder.TV_title.setText(current.);
    }

    @Override
    public int getItemCount() {
        return offerlist.size();
    }


}
