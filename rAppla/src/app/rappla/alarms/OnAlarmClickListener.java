package app.rappla.alarms;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class OnAlarmClickListener implements OnClickListener
{
	Context c;
	Calendar eventDate;
	Alarm alarm;
	
	public OnAlarmClickListener(Context c, Calendar eventDate, Alarm alarm)
	{
		this.c = c;
		this.eventDate = eventDate;
		this.alarm = alarm;
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case Alarm.dateButtonID:
			OnDateSetListener odsl = new OnDateSetListener()
			{
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
				{
					alarm.getDate().set(year, monthOfYear, dayOfMonth);
					alarm.updateViews();
				}
			};
			new DatePickerDialog(c, odsl, eventDate.get(Calendar.YEAR), eventDate.get(Calendar.MONTH), eventDate.get(Calendar.DAY_OF_MONTH))
					.show();
			break;
		case Alarm.timeButtonID:
			OnTimeSetListener otsl = new OnTimeSetListener()
			{
				@Override
				public void onTimeSet(TimePicker arg0, int hour, int minute)
				{
					alarm.getDate().set(Calendar.HOUR, hour);
					alarm.getDate().set(Calendar.MINUTE, minute);
					alarm.updateViews();
				}
			};
			new TimePickerDialog(c, otsl, eventDate.get(Calendar.HOUR), eventDate.get(Calendar.MINUTE), true).show();
			break;
		}
	}

}
