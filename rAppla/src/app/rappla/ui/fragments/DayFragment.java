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
	public DayFragment()
	{
		setTitle(StaticContext.getContext().getResources().getString(R.string.day));
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

	protected void configureGrid()
	{
		if (LOG_EVENTS)
			Log.d(title, "configureGrid");
		
		RapplaGrid calGrid = (RapplaGrid) getView();

		Calendar today = Calendar.getInstance();
		Set<RaplaEvent> events = getCalender().getEventsAtDate(today);

		ArrayList<RapplaGridElement> dayElements = createDayGrid(getActivity(), events, 0);

		for (RapplaGridElement eventGridElement : dayElements)
		{
			calGrid.addElement(eventGridElement);
		}
	}

}