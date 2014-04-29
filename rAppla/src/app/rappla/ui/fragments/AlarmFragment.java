package app.rappla.ui.fragments;

import java.util.HashMap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import app.rappla.R;
import app.rappla.StaticContext;
import app.rappla.activities.EventActivity;
import app.rappla.alarms.Alarm;
import app.rappla.calendar.RaplaEvent;

public class AlarmFragment extends RapplaFragment
{
	public static final String serializedNoteFileName = "RapplaAlarms.ser";
	
	static HashMap<String, String> notes = null;
	LinearLayout alarmHolder = null;
	String eventID;
	
	
	
	public AlarmFragment()
	{
		setTitle(StaticContext.getContext().getResources().getString(R.string.alarms));
		setBackground = true;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.layout_alarms, container, false);

		if (LOG_EVENTS)
			Log.d(title, "onCreateView");

		return rootView;
	}
	
	
	public void onStart()
	{
		super.onStart();
		Button addBtn = (Button) getActivity().findViewById(R.id.btnAdd);
		addBtn.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				onClickedAdd();
			}
		});
		Button saveBtn = (Button) getActivity().findViewById(R.id.btnSave);
		saveBtn.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				onClickedSave();
			}
		});
		alarmHolder = (LinearLayout) getActivity().findViewById(R.id.alarmHolder);
	}
	
	public void onClickedAdd()
	{
		RaplaEvent event = EventActivity.getInstance().getEvent();
		
		Alarm newAlarm = new Alarm(event.getDate());
		newAlarm.initViews(getActivity(), alarmHolder);
		newAlarm.updateViews();
	}
	public void onClickedSave()
	{
		getActivity().finish();
	}
}
