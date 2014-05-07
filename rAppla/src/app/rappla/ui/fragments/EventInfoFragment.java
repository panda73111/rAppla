package app.rappla.ui.fragments;

import java.util.Calendar;
import java.util.HashMap;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.rappla.R;
import app.rappla.StaticContext;
import app.rappla.activities.EventActivity;
import app.rappla.calendar.RaplaEvent;

public class EventInfoFragment extends RapplaFragment
{
	public static final String serializedNoteFileName = "RapplaNotes.ser";
	
	static HashMap<String, String> notes = null;
	String eventID;
	int width;
	int height;
	
	
	
	public EventInfoFragment()
	{
		setTitle(StaticContext.getContext().getResources().getString(R.string.infos));
		setBackground = true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.layout_info, container, false);

		if (LOG_EVENTS)
			Log.d(title, "onCreateView");
		return rootView;
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    repaintViews();
	}
	
	public void onResume(){
		super.onResume();
		repaintViews();
	}
	
	private void repaintViews()
	{
		RaplaEvent event 		= EventActivity.getInstance().getEvent();	
		
		TextView titleView		= (TextView) getActivity().findViewById(R.id.titleView);
		titleView.setText(event.getTitle());
		
		Calendar startTime		= event.getStartTime();
		Calendar endTime		= event.getStartTime();
		
		TextView beginView		= (TextView) getActivity().findViewById(R.id.beginView);
		beginView.setText(startTime.get(Calendar.HOUR_OF_DAY) + ":" + startTime.get(Calendar.MINUTE));
		TextView endView		= (TextView) getActivity().findViewById(R.id.endView);
		endView.setText(endTime.get(Calendar.HOUR_OF_DAY) + ":" + endTime.get(Calendar.MINUTE));
		TextView resView		= (TextView) getActivity().findViewById(R.id.ressourcesView);
		resView.setText(event.getResources());
		TextView dateView		= (TextView) getActivity().findViewById(R.id.dateView);
		dateView.setText(startTime.get(Calendar.DAY_OF_MONTH) + "/" + (startTime.get(Calendar.MONTH) + 1) + "/" + startTime.get(Calendar.YEAR));
		
	}
	public void doGlobalLayout()
	{
		TextView titleView		= (TextView) getActivity().findViewById(R.id.titleView);
		TextView beginView		= (TextView) getActivity().findViewById(R.id.beginView);
		TextView endView		= (TextView) getActivity().findViewById(R.id.endView);
		TextView resView		= (TextView) getActivity().findViewById(R.id.ressourcesView);
		TextView dateView		= (TextView) getActivity().findViewById(R.id.dateView);
		
		titleView.setWidth(getWidth()/2);
		beginView.setWidth(getWidth()/2);
		endView.setWidth(getWidth()/2);
		resView.setWidth(getWidth()/2);
		dateView.setWidth(getWidth()/2);
	}

}
