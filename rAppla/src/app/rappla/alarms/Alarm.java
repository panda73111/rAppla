package app.rappla.alarms;

import java.io.Serializable;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import app.rappla.R;

@SuppressWarnings("serial")
@SuppressLint("SimpleDateFormat")
public class Alarm implements Serializable
{
	public static final int dateButtonID = 1;
	public static final int timeButtonID = 2;
	public static final int activateButtonID = 3;

	private static final int alarmOnResource = R.drawable.alarmicon2on;
	private static final int alarmOffResource = R.drawable.alarmicon2off;
	
	Calendar alarmDate;
	boolean isActive = true;
	String eventID;
	
	transient LinearLayout alarmGroup;
	transient RelativeLayout alarmSubGroup;
	transient Button dateButton;
	transient Button timeButton;
	transient Button alarmButton;
	transient Context context;
	
	public Alarm(Calendar a, String eventID)
	{
		this.alarmDate = Calendar.getInstance();
		this.isActive = true;
		this.eventID = eventID;

		if (a != null)
		{
			alarmDate.setTime(a.getTime());
		}
	}

	public void initViews(Context c, ViewGroup parent)
	{
		this.context = c;
		
		OnAlarmClickListener oacl = new OnAlarmClickListener(c, alarmDate, this);
		Drawable bd = c.getResources().getDrawable(alarmOnResource);
		int height = bd.getIntrinsicHeight();
		int width = bd.getIntrinsicWidth();
		
		
		alarmButton = new Button(c);
		alarmButton.setId(activateButtonID);
		alarmButton.setOnClickListener(oacl);

		dateButton = new Button(c);
		dateButton.setId(dateButtonID);
		dateButton.setOnClickListener(oacl);

		timeButton = new Button(c);
		timeButton.setId(timeButtonID);
		timeButton.setOnClickListener(oacl);

		alarmGroup = new LinearLayout(c);
		alarmSubGroup = new RelativeLayout(c);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.BELOW, dateButton.getId());

		alarmSubGroup.addView(dateButton);
		alarmSubGroup.addView(timeButton, lp);
		alarmGroup.addView(alarmButton, width, height);
		alarmGroup.addView(alarmSubGroup);
		parent.addView(alarmGroup);

		updateViews();
	}

	public void updateViews()
	{
		if (dateButton != null)
		{
			dateButton.setText(toDateString(alarmDate));
		}
		if (timeButton != null)
		{
			timeButton.setText(toTimeString(alarmDate));
		}
		if(alarmButton != null)
		{
			if(isActive)
				alarmButton.setBackgroundResource(alarmOnResource);
			else
				alarmButton.setBackgroundResource(alarmOffResource);				
		}
	}

	public Calendar getDate()
	{
		return alarmDate;
	}

	public void startAlarm()
	{
		AlarmFactory.startAlarm(eventID, alarmDate, context);
	}
	public void cancelAlarm()
	{
		AlarmFactory.cancelAlarm(eventID, alarmDate, context);
	}
	public void updateState()
	{
		if(isActive)
			startAlarm();
		else
			cancelAlarm();
	}
	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
		if(isActive)
			startAlarm();
		else
			cancelAlarm();
		updateViews();
	}
	
	private String toDateString(Calendar date)
	{
		int day 	= date.get(Calendar.DAY_OF_MONTH);
		int month 	= date.get(Calendar.MONTH);
		int year 	= date.get(Calendar.YEAR);
		return day + "/" + (month + 1) + "/" + year;
	}
	private String toTimeString(Calendar date)
	{
		int hour = date.get(Calendar.HOUR_OF_DAY);
		int minute = date.get(Calendar.MINUTE);
		return hour + ":" + minute;
	}
	
	
}
