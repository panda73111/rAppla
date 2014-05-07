package app.rappla.alarms;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import app.rappla.activities.AlarmPopupDialog;
import app.rappla.activities.EventActivity;

public class AlarmFactory
{
	
	private static PendingIntent createPendingIntent(String eventID, Calendar alarmDate, Context context)
	{
		//Intent intent = new Intent(context, EventActivity.class);
		Intent intent = new Intent(context, AlarmPopupDialog.class);
	    
	    intent.putExtra(EventActivity.eventIDKey, eventID);
	    intent.putExtra(EventActivity.isAlarmKey, true);

	    return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

	}
	public static boolean startAlarm(String eventID, Calendar alarmDate, Context context)
	{
	    AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);  
	    
	    PendingIntent pendingIntent = createPendingIntent(eventID, alarmDate, context);
	    
	    if( pendingIntent == null )
	    {
	        Log.e("AlarmFactory", "PendingIntent came back as null");
	        return false;
	    }
	    
	    if(alarmDate.getTimeInMillis() < System.currentTimeMillis())
	    {
	    	Log.d("Alarm", "Alarm not set. It is in the past!");
		    return false;
	    }
	    
	    alarmMgr.set(AlarmManager.RTC_WAKEUP, alarmDate.getTimeInMillis(), pendingIntent);
	    Log.d("Alarm", "Alarm set to " + alarmDate.toString());
	    
	    return true;
	}

	public static boolean cancelAlarm(String eventID, Calendar alarmDate, Context context)
	{
	    AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	    PendingIntent pendingIntent = createPendingIntent(eventID, alarmDate, context);
	    if( pendingIntent == null )
	    {
	        Log.e("AlarmFactory", "PendingIntent came back as null");
	        return false;
	    }
	    Log.d("Alarm", "Alarm canceled at " + alarmDate.toString());
	    alarmMgr.cancel(pendingIntent);
	    return true;
	}
}
