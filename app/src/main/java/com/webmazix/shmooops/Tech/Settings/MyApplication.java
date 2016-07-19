package com.webmazix.shmooops.Tech.Settings;

import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;
import android.os.PowerManager;

import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import com.nostra13.universalimageloader.core.assist.QueueProcessingType;



import java.io.File;

public class MyApplication extends Application {
	public static  SharedPreferences preference;
	private static Context ctx;


	public MyApplication(){

	}

	public MyApplication(Context context) {
		this.ctx = context;
		preference = getSharedPreferences("ShmooopsApp",MODE_PRIVATE);
	}

	 public static void gotoRateUs(Context context)
	    {
	    	    Uri uri = Uri.parse("market://details?id=" + ctx.getPackageName());
	    	    Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
	    	    try {
	    	    	context.startActivity(myAppLinkToMarket);
	    	    } catch (ActivityNotFoundException e) {
	    	        Toast.makeText(context, " unable to find market app", Toast.LENGTH_LONG).show();
	    	    }
	    }

	public static void loadimg(Context mycontext) {
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(mycontext);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		//config.diskCacheSize(50,1024,1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}

	public static void register(final String regId) {

			/*UserFunctions user = new UserFunctions(ctx);
			user.registerGCM(regId, "android");
	        GCMRegistrar.setRegisteredOnServer(ctx, true);*/
	    }
	 
	public static void unregister( final String flag) {
         
/*	        UserFunctions user = new UserFunctions(ctx);
			user.unregisterGCM(flag);
	        GCMRegistrar.setRegisteredOnServer(ctx, false);*/
	    }

	static void displayMessageOnScreen(Context context, String message) {
	          
/*	        Intent intent = new Intent(Constants.DISPLAY_MESSAGE_ACTION);
	        intent.putExtra(Constants.EXTRA_MESSAGE, message);
	        context.sendBroadcast(intent);*/
	    }    

	private static PowerManager.WakeLock wakeLock;
	     
	public static void acquireWakeLock(Context context) {
	        if (wakeLock != null) wakeLock.release();
	        PowerManager pm = (PowerManager) 
	                          context.getSystemService(Context.POWER_SERVICE);
	        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
	                PowerManager.ACQUIRE_CAUSES_WAKEUP |
	                PowerManager.ON_AFTER_RELEASE, "WakeLock");
	        wakeLock.acquire();
	    }
	 
	public static  void releaseWakeLock() {
	        if (wakeLock != null) wakeLock.release(); wakeLock = null;
	    }
	}