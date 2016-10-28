package com.example.multiplexer.checkmate;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

/**
 * @author Prabu
 *
 */
public class AlarmReciver extends BroadcastReceiver {
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	@Override
	public void onReceive(Context context, Intent intent) {
	 




		    NotificationManager mNM;
	        mNM = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
	        // Set the icon, scrolling text and timestamp

		PendingIntent examIntent = PendingIntent.getActivity(context, 0, new Intent(context, Remind_note.class), 0);

	            // Notification notification = new Notification(R.drawable.four, "Demo !",System.currentTimeMillis());
	        // The PendingIntent to launch our activity if the user selects this notification

		pref = context.getSharedPreferences("MyPref", context.MODE_PRIVATE);
		editor = pref.edit();


		/*Notification.Builder builder = new Notification.Builder(context);
		builder.setSmallIcon(R.drawable.logo)
				.setContentText(pref.getString("alarm_note",""))
				.setContentTitle("CheckMate")
				.setContentIntent(examIntent);
		builder.setAutoCancel(true);
		Notification notification = builder.getNotification();
		mNM.notify(R.drawable.logo, notification);
*/
		/*Intent scheduledIntent = new Intent(context, Remind_note.class);
		scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(scheduledIntent);*/

		Intent alarmIntent = new Intent("android.intent.action.MAIN");
		alarmIntent.setClass(context, Remind_note.class);
		alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Start the popup activity

		context.startActivity(alarmIntent);

	}


 
}
