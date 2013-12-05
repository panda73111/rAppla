package app.rappla.ui.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.rappla.R;
import app.rappla.calendar.RaplaEvent;
import app.rappla.ui.grid.RapplaGrid;
import app.rappla.ui.grid.RapplaGridElement;

public class WeekFragment extends CalenderFragment
{
	private Calendar date;

	public WeekFragment()
	{
		setDate(Calendar.getInstance());
	}

	public void setDate(Calendar date)
	{
		this.date = date;
		setTitle("Einzelwoche " + date.get(Calendar.YEAR) + "/" + date.get(Calendar.WEEK_OF_YEAR));
	}

	public Calendar getDate()
	{
		return date;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart()
	{
		super.onStart();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = (RapplaGrid) inflater.inflate(R.layout.layout_week, container, false);

		if (LOG_EVENTS)
			Log.d(title, "onCreateView");

		return rootView;
	}

	protected void configureGrid()
	{
		Log.d(title, "configuring grid");
		
		ArrayList<RapplaGridElement> dayElements;
		RapplaGrid calGrid = (RapplaGrid) getView();

		for (int i = 0; i < 5; i++)
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY + i);
			Set<RaplaEvent> events = getCalender().getEventsAtDate(date);
			dayElements = createDayGrid(getActivity(), events, i);

			for (RapplaGridElement eventGridElement : dayElements)
			{
				calGrid.addElement(eventGridElement);
			}

		}
	}
}