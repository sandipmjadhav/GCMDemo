package com.androidmyway.demo.gcmdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		
		final String regId = GCMRegistrar.getRegistrationId(this);
		
		if (regId.equals("")) {
			GCMRegistrar.register(this, Utils.GCMSenderId);
		} else {
			Log.v("", "Already registered:  "+regId);
		}
        
		if (Utils.notificationReceived) {
			onNotification();
		}
    }
    
    @Override
    protected void onDestroy() {
        GCMRegistrar.onDestroy(this);
        super.onDestroy();
    }
    
    public void onNotification(){
		Utils.notificationReceived=false;
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		WakeLock  wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
		wl.acquire();

		AlertDialog.Builder mAlert=new AlertDialog.Builder(this);
		mAlert.setCancelable(true);

		mAlert.setTitle(Utils.notiTitle);
		mAlert.setMessage(Utils.notiMsg);
		mAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(Utils.notiUrl));
				startActivity(i);
				finish();
			}
		});
		mAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		mAlert.show();
	}
    
}
