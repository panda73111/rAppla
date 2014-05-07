package app.rappla.ui.fragments;

import java.util.*;

import android.os.Bundle;
import android.util.Log;
import android.view.*;
import app.rappla.*;
import app.rappla.calendar.*;
import app.rappla.ui.grid.*;

public class DayFragment extends CalenderFragment
{
	private Calendar date;

	public DayFragment()
	{
		setDate(Calendar.getInstance());
		setBackground = true;
	}
	
	public void setDate(Calendar date)
	{
		this.date = date;
		setTitle("Einzeltag " + date.get(Calendar.YEAR) + "/" + date.get(Calendar.DAY_OF_YEAR));
		setBackground = true;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.layout_day, container, false);

		if (LOG_EVENTS)
			Log.d(title, "onCreateView");

		return rootView;
	}
	
	public Calendar getDate()
	{
		return date;
	}
	
	protected void configureGrid()
	{
		if (LOG_EVENTS)
			Log.d(title, "configureGrid");
		
		RapplaGrid calGrid = (RapplaGrid) getView();

		Set<RaplaEvent> events = getCalender().getEventsAtDate(date);

		ArrayList<RapplaGridElement> dayElements = createDayGrid(getActivity(), events, 0);

		for (RapplaGridElement eventGridElement : dayElements)
		{
			calGrid.addElement(eventGridElement);
		}
	}

}