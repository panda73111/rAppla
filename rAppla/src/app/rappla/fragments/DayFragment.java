package app.rappla.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import RapplaGrid.RapplaGrid;
import RapplaGrid.RapplaGridElement;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.rappla.R;
import app.rappla.StaticContext;
import app.rappla.calendar.RaplaEvent;

public class DayFragment extends CalenderFragment
{
	public DayFragment()
	{
		setTitle(StaticContext.getContext().getResources().getString(R.string.day));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.daylayout, container, false);
		return rootView;
	}

	protected void configureGrid()
	{
		calenderGrid = (RapplaGrid) getActivity().findViewById(R.id.dayGrid);

		Calendar today = Calendar.getInstance();
		Set<RaplaEvent> events = getCalender().getEventsAtDate(today);
		
		ArrayList<RapplaGridElement> dayElements = createDayGrid(events, 0);

		for (RapplaGridElement eventGridElement : dayElements)
		{
			calenderGrid.addElementAt(eventGridElement);
		}
/*
		Button b = new Button(getActivity());
		b.setGravity(Gravity.FILL_HORIZONTAL);
		calenderGrid.addElementAt(b, 0, 2, 5);*/
	}

}