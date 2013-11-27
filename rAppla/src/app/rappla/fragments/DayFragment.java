package app.rappla.fragments;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import RapplaGrid.RapplaGrid;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

		for(RaplaEvent currentEvent : events)
		{
			long durationInMinutes 	= currentEvent.getDurationInMinutes();
			int eventLength			= (int) (durationInMinutes/minimumTimeInterval);
			
			
			Calendar startTime		= currentEvent.getStartTime();
			Calendar earliestStart 	= Calendar.getInstance();
			earliestStart.setTime(startTime.getTime());
			earliestStart.set(Calendar.HOUR, 8);
			earliestStart.set(Calendar.MINUTE, 0);

			int offset = (int) ((startTime.getTimeInMillis()-earliestStart.getTimeInMillis())/1000/60/15);
			
			Button eventButton 		= new Button(getActivity());
			eventButton.setBackgroundResource(R.drawable.event);
			eventButton.setText(currentEvent.getTitle());
			calenderGrid.addElementAt(eventButton, 0, offset, eventLength);
		}

	}

}