package app.rappla.ui.fragments;

import java.util.HashMap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.rappla.R;
import app.rappla.StaticContext;

public class AlarmFragment extends RapplaFragment
{
	public static final String serializedNoteFileName = "RapplaAlarms.ser";
	
	static HashMap<String, String> notes = null;
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
	}
}
