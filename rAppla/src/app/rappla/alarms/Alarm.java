package app.rappla.alarms;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Alarm
{
	Calendar alarmDate;

	LinearLayout 	alarmGroup;
	RelativeLayout 	alarmSubGroup;
	Button 			dateButton;
	Button 			timeButton;
	Button   		alarmButton;
	
	public Alarm(Calendar a)
	{
		if(a==null)
			a = Calendar.getInstance();
		
		this.alarmDate = a;
	}
	
	public void initViews(Context c, ViewGroup parent)
	{
		int w = 100;
		int h = 100;
		
		alarmButton  = new Button(c);
		alarmButton.setWidth(100);
		alarmButton.setHeight(200);
		
		dateButton = new Button(c);
		dateButton.setId(1);
		timeButton = new Button(c);
		timeButton.setId(2);
		alarmGroup	 = new LinearLayout(c);
		alarmSubGroup= new RelativeLayout(c);
		
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.BELOW, dateButton.getId());
		
		alarmSubGroup.addView(dateButton);
		alarmSubGroup.addView(timeButton, lp);
		alarmGroup.addView(alarmButton);
		alarmGroup.addView(alarmSubGroup);		
		parent.addView(alarmGroup);
	}

	public void updateViews()
	{
		if(dateButton != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dateButton.setText(sdf.format(alarmDate.getTime()));
		}
		if(timeButton != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			timeButton.setText(sdf.format(alarmDate.getTime()));
		}
	}

}
