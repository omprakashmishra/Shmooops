package com.webmazix.shmooops.Tech.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by Krishna Computers on 28-06-2016.
 */
public class Utilities {
    public static void downloadImage(String IMAGE_URL,ImageView imageProjectHome,DisplayImageOptions options,final ProgressBar spinner)
    {


        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(IMAGE_URL, imageProjectHome, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(spinner!=null)
                {
                    spinner.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String message = null;
                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "Image not found";
                        break;
                    case DECODING_ERROR:
                        message = "Image can't be decoded";
                        break;
                    case NETWORK_DENIED:
                        message = "Downloads are denied";
                        break;
                    case OUT_OF_MEMORY:
                        message = "Out Of Memory error";
                        break;
                    case UNKNOWN:
                        message = "Unknown error";
                        break;
                }
                if(spinner!=null)
                {
                    spinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(spinner!=null)
                {
                    spinner.setVisibility(View.GONE);
                }
            }
        });
    }
}
