package app.rappla.activities;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;
import app.rappla.R;
import app.rappla.calendar.RaplaCalendar;
import app.rappla.calendar.RaplaEvent;
import app.rappla.notes.Notes;

public class AlarmPopupDialog extends Activity
{
	RaplaEvent event;
	CharSequence note;

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

		vibrateOnStartup();
	}

	public void onStart()
	{
		super.onStart();
		TextView noteView = (TextView) findViewById(R.id.noteText);
		noteView.setText(note);
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
			return null;
		}

		return event;
	}

	private void vibrateOnStartup()
	{
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notif = new Notification();
		notif.ledARGB = 0xFFff4500;
		notif.flags = Notification.FLAG_SHOW_LIGHTS;
		notif.ledOnMS = 100;
		notif.ledOffMS = 100;
		nm.notify(0, notif);

		Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(new long[] { 0, 150, 150, 250, 100, 100 }, -1);
	}

}