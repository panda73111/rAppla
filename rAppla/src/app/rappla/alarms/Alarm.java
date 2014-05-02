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

@SuppressLint("SimpleDateFormat")
public class Alarm implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final int dateButtonID = 1;
	public static final int timeButtonID = 2;

	Calendar alarmDate;

	transient LinearLayout alarmGroup;
	transient RelativeLayout alarmSubGroup;
	transient Button dateButton;
	transient Button timeButton;
	transient Button alarmButton;

	public Alarm(Calendar a)
	{
		this.alarmDate = Calendar.getInstance();

		if (a != null)
		{
			alarmDate.setTime(a.getTime());
		}
	}

	public void initViews(Context c, ViewGroup parent)
	{
		alarmButton = new Button(c);
		alarmButton.setBackgroundResource(R.drawable.alarm_on);

		Drawable bd = c.getResources().getDrawable(R.drawable.alarm_on);
		int height = bd.getIntrinsicHeight();
		int width = bd.getIntrinsicWidth();

		OnAlarmClickListener oacl = new OnAlarmClickListener(c, alarmDate, this);

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
	}

	public Calendar getDate()
	{
		return alarmDate;
	}
	
	private String toDateString(Calendar date)
	{
		return date.get(Calendar.DAY_OF_MONTH) + "/" + date.get(Calendar.MONTH) + 1 + "/" + date.get(Calendar.YEAR);
	}

	private String toTimeString(Calendar date)
	{
		return date.get(Calendar.HOUR) + ":" + date.get(Calendar.MINUTE);
	}
}
