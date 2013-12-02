package app.rappla.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import RapplaGrid.RapplaGrid;
import RapplaGrid.RapplaGridElement;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import app.rappla.R;
import app.rappla.StaticContext;
import app.rappla.calendar.RaplaEvent;

public class WeekFragment extends CalenderFragment implements OnClickListener
{

	public WeekFragment()
	{
		setTitle(StaticContext.getContext().getResources().getString(R.string.week));
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.weeklayout, container, false);
		return rootView;
	}

	public void onClick(View view)
	{
		int itemId = view.getId();
		String itemTag = (String) view.getTag();
		if (itemTag != null && itemTag.startsWith(gridElementPrefix))
		{
			Toast.makeText(getActivity(), (String) view.getTag(), Toast.LENGTH_SHORT).show();
		}
		switch (itemId)
		{
		// case R.id.action_refresh:
		// onRefreshButtonPressed(view);
		// break;
		default:
		}
	}

	protected void configureGrid()
	{
		calenderGrid = (RapplaGrid) getActivity().findViewById(R.id.weekGrid);

		Calendar today = Calendar.getInstance();
		today.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		ArrayList<RapplaGridElement> dayElements;

		for (int i = 0; i < 5; i++)
		{
			today.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY + i);
			Set<RaplaEvent> events = getCalender().getEventsAtDate(today);
			dayElements = createDayGrid(events, i);
			
			for(RapplaGridElement eventGridElement : dayElements)
			{
				calenderGrid.addElementAt(eventGridElement);	
			}

		}

	}

}