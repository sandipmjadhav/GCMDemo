package com.androidmyway.demo.gcmdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService{

	public GCMIntentService(){
		super(Utils.GCMSenderId);
	}

	@Override
	protected void onError(Context context, String regId) {
		// TODO Auto-generated method stub
		Log.e("", "error registration id : "+regId);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		// TODO Auto-generated method stub
		handleMessage(context, intent);
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		// TODO Auto-generated method stub
//		Log.e("", "registration id : "+regId);
		handleRegistration(context, regId);
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	private void handleMessage(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Utils.notiMsg = intent.getStringExtra("msg");
		Utils.notiTitle = intent.getStringExtra("title");
		Utils.notiType = intent.getStringExtra("type");
		Utils.notiUrl = intent.getStringExtra("url");
		
		int icon = R.drawable.ic_launcher;        // icon from resources
		CharSequence tickerText = Utils.notiTitle;//intent.getStringExtra("me");              // ticker-text
		
		long when = System.currentTimeMillis();         // notification time
		CharSequence contentTitle = ""+Utils.notiMsg; //intent.getStringExtra("me");  // message title

		NotificationManager notificationManager =
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, tickerText, when);
		Intent notificationIntent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, "", pendingIntent);
		notification.flags|=notification.FLAG_INSISTENT|notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS;
		notification.vibrate=new long[] {100L, 100L, 200L, 500L};
		notificationManager.notify(1, notification);
		Utils.notificationReceived=true;
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
		wl.acquire();
		
	}
	
	private void handleRegistration(Context context, String regId) {
		// TODO Auto-generated method stub
			Utils.registrationId = regId;
			Log.e("", "registration id : "+regId);
			
	}
}
