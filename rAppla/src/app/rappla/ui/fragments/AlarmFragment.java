package app.rappla.ui.fragments;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
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
	public static final String serializedAlarmsFileName = "RapplaAlarms.ser";

	LinearLayout alarmHolder = null;
	String eventID;

	static HashMap<String, ArrayList<Alarm>> alarms = null;
	ArrayList<Alarm> alarmList = null;

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

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		RaplaEvent event = ((EventActivity)getActivity()).getEvent();
		eventID = event.getUniqueEventID();
	}
	
	public void onStart()
	{
		super.onStart();

		if (alarms == null)
			loadAlarmFile();
		alarmList = alarms.get(eventID);

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

	public void onResume()
	{
		super.onResume();
		refreshPanel();
	}

	public void onClickedAdd()
	{
		RaplaEvent event = ((EventActivity)getActivity()).getEvent();
		
		Alarm newAlarm = new Alarm(event.getDate(), eventID);
		alarmList.add(newAlarm);
		alarms.put(eventID, alarmList);
		newAlarm.initViews(getActivity(), alarmHolder);
		newAlarm.setActive(true);
		refreshPanel();
	}

	public void onClickedSave()
	{
		for(Alarm a : alarmList)
		{
			a.applyState();
		}
		saveAlarmFile();
		getActivity().finish();
	}

	public void refreshPanel()
	{
		alarmHolder.removeAllViews();

		if (alarmList == null)
			alarmList = alarms.get(eventID);
		if (alarmList == null)
			alarmList = new ArrayList<Alarm>();

		for (Alarm a : alarmList)
		{
			a.initViews(getActivity(), alarmHolder);
		}
	}

	private void saveAlarmFile()
	{
		try
		{
			FileOutputStream fileOut = getActivity().openFileOutput(serializedAlarmsFileName, Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(alarms);
			out.close();
			fileOut.close();
		} catch (IOException i)
		{
			i.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void loadAlarmFile()
	{
		try
		{
			FileInputStream fis = getActivity().openFileInput(serializedAlarmsFileName);
			ObjectInputStream in = new ObjectInputStream(fis);
			alarms = (HashMap<String, ArrayList<Alarm>>) in.readObject();
			in.close();
			fis.close();
		} catch (FileNotFoundException e)
		{
		} catch (IOException i)
		{
			i.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} finally
		{
			if (alarms == null)
				alarms = new HashMap<String, ArrayList<Alarm>>();
		}
	}
}
