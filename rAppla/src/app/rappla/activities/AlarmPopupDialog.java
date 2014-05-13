package app.rappla.activities;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import app.rappla.R;
import app.rappla.calendar.RaplaCalendar;
import app.rappla.calendar.RaplaEvent;
import app.rappla.notes.Notes;

public class AlarmPopupDialog extends Activity implements OnClickListener
{
	RaplaEvent event;
	CharSequence note;
	private static final int notificationID = 132; // This number is totally random.

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_alarm_popup);
		this.setFinishOnTouchOutside(true);

		Bundle extras = getIntent().getExtras();
		if (extras == null)
			return;

		note = getNote(extras);
		event = findEvent(extras);
		
		
		setTitle(event.getEventNameWithoutProfessor());

		configureButton();
		
		notificationOnStartup();
	}

	private void configureButton()
	{
		Button okButton = (Button) findViewById(R.id.okButton);
		Button toEventButton = (Button) findViewById(R.id.toEventButton);
		okButton.setOnClickListener(this);
		toEventButton.setOnClickListener(this);
		
	}

	public void onStart()
	{
		super.onStart();
		TextView noteView = (TextView) findViewById(R.id.noteText);
		noteView.setText(note);
	}
	public void onDestroy()
	{
		super.onDestroy();
    	NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	nm.cancel(notificationID);
	}

	private CharSequence getNote(Bundle extras)
	{
		if (!Notes.isInitialized())
			Notes.loadNoteFile(this);

		String uniqueID = extras.getString(EventActivity.eventIDKey);
		if (uniqueID == null)
			return null;

		return Notes.get(uniqueID);

	}

	private RaplaEvent findEvent(Bundle extras)
	{
		RaplaEvent event;

		try
		{
			RapplaActivity main = RapplaActivity.getInstance();
			String uniqueID = extras.getString(EventActivity.eventIDKey);
			RaplaCalendar calendar = main.getCalender();
			event = calendar.getElementByUniqueID(uniqueID);
		} catch (NullPointerException e)
		{
			e.printStackTrace();
			return null;
		}

		return event;
	}

	private void notificationOnStartup()
	{
		Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    	NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	
    	PendingIntent eventIntent = PendingIntent.getActivity(this, 0, getEventActivityIntent(), PendingIntent.FLAG_UPDATE_CURRENT);
    	
    	NotificationCompat.Builder mBuilder =
    	        new NotificationCompat.Builder(this)
    	        .setSmallIcon(R.drawable.ic_launcher)
    	        .setContentTitle(event.getTitle())
    	        .setContentText(note)
    	        .setLights(0xFFff4500, 100, 100)
    	        .setSound(notificationSound)
    	        .setVibrate(new long[] { 0, 150, 150, 250, 100, 100 })
    	        .setContentIntent(eventIntent);
    	Notification notification = mBuilder.build();
    	
		nm.notify(notificationID, notification);
	}
	private Intent getEventActivityIntent()
	{
    	Intent resultIntent = new Intent(this, EventActivity.class);	
    	resultIntent.putExtra(EventActivity.eventIDKey, event.getUniqueEventID());
    	return resultIntent;
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.okButton:
			onClickOK();
			break;
		case R.id.toEventButton:
			onClickToEventButton();
			break;
		default:
			break;
		}
	}

	private void onClickToEventButton()
	{
		EventActivity eventActivity = EventActivity.getInstance();
		if(eventActivity != null)
		{
			eventActivity.finish();
		}
		startActivity(getEventActivityIntent());
	}

	private void onClickOK()
	{
		finish();
	}

}