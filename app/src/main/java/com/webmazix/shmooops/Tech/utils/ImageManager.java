package com.webmazix.shmooops.Tech.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import com.webmazix.shmooops.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;




public class ImageManager {

    final int stub_id = R.drawable.profile_bg;
    MemoryCache memoryCache = new MemoryCache();
    FileCache fileCache;
    PhotosQueue photosQueue = new PhotosQueue();
    PhotosLoader photoLoaderThread = new PhotosLoader();
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());

    public ImageManager(Context context) {
        //Make the background thead low priority. This way it will not affect the UI performance
        photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);
        fileCache = new FileCache(context);
    }

    public void DisplayImage(String url, ImageView imageView) {
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null)

            imageView.setImageBitmap(Utils.GetBitmapClippedCircle(bitmap, 7));
        else {
            queuePhoto(url, imageView);
            imageView.setImageResource(stub_id);
        }
    }

    private void queuePhoto(String url, ImageView imageView) {
        // This ImageView may be used for other images before. So there may be
        // some old tasks in the queue. We need to discard them.
        photosQueue.Clean(imageView);
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        synchronized (photosQueue.photosToLoad) {
            photosQueue.photosToLoad.push(p);
            photosQueue.photosToLoad.notifyAll();
        }

        //start thread if it's not started yet
        if (photoLoaderThread.getState() == Thread.State.NEW)
            photoLoaderThread.start();
    }

    private Bitmap getBitmap(String url) {
        File f = fileCache.getFile(url);

        //from SD cache..
        Bitmap b = decodeFile(f);
        if (b != null)
            return b;

        //from web
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            conn.setConnectTimeout(50000);
            conn.setReadTimeout(50000);
            InputStream is = conn.getInputStream();
            // Utils.printLogObject(is);
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);

            os.flush();
            is.close();
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f) {
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public void stopThread() {
        photoLoaderThread.interrupt();
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }

    public boolean isLoaderEmpty() {

        if (memoryCache.isCacheEmplty() && fileCache.isFileEmpity())
            return false;
        else
            return true;
    }

    //Task for the queue
    private class PhotoToLoad {
        public String url;
        public ImageView imageView;

        public PhotoToLoad(String u, ImageView i) {
            url = u;
            imageView = i;
        }
    }

    //stores list of photos to download
    class PhotosQueue {
        private Stack<PhotoToLoad> photosToLoad = new Stack<PhotoToLoad>();

        //removes all instances of this ImageView
        public void Clean(ImageView image) {
            for (int j = 0; j < photosToLoad.size(); ) {
                if (photosToLoad.get(j).imageView == image)
                    photosToLoad.remove(j);
                else
                    ++j;
            }
        }
    }

    class PhotosLoader extends Thread {
        public void run() {
            try {
                while (true) {
                    //thread waits until there are any images to load in the queue
                    if (photosQueue.photosToLoad.size() == 0)
                        synchronized (photosQueue.photosToLoad) {
                            photosQueue.photosToLoad.wait();
                        }
                    if (photosQueue.photosToLoad.size() != 0) {
                        PhotoToLoad photoToLoad;
                        synchronized (photosQueue.photosToLoad) {
                            photoToLoad = photosQueue.photosToLoad.pop();
                        }
                        Bitmap bmp = getBitmap(photoToLoad.url);
                        memoryCache.put(photoToLoad.url, bmp);
                        String tag = imageViews.get(photoToLoad.imageView);
                        if (tag != null && tag.equals(photoToLoad.url)) {
                            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad.imageView);
                            Activity a = (Activity) photoToLoad.imageView.getContext();
                            a.runOnUiThread(bd);
                        }
                    }
                    if (Thread.interrupted())
                        break;
                }
            } catch (InterruptedException e) {
                //allow thread to exit
            }
        }
    }

    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        ImageView imageView;

        public BitmapDisplayer(Bitmap b, ImageView i) {
            bitmap = b;
            imageView = i;
        }

        public void run() {
            if (bitmap != null)
                imageView.setImageBitmap(Utils.GetBitmapClippedCircle(bitmap, 7));
            else
                imageView.setImageResource(stub_id);
        }
    }

}
