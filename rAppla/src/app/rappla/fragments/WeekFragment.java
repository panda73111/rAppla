package app.rappla.fragments;

import java.util.Calendar;
import java.util.Set;

import RapplaGrid.RapplaGrid;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
		
		for (int i = 0; i < 5; i++)
		{
			today.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY+i);
			Set<RaplaEvent> events = getCalender().getEventsAtDate(today);

			for (RaplaEvent currentEvent : events)
			{
				long durationInMinutes = currentEvent.getDurationInMinutes();
				int eventLength = (int) (durationInMinutes / minimumTimeInterval);

				Calendar startTime = currentEvent.getStartTime();
				Calendar earliestStart = Calendar.getInstance();
				earliestStart.setTime(startTime.getTime());
				earliestStart.set(Calendar.AM_PM, Calendar.AM);
				earliestStart.set(Calendar.HOUR, 8);
				earliestStart.set(Calendar.MINUTE, 0);
				
				
				int offset = (int) ((startTime.getTimeInMillis() - earliestStart.getTimeInMillis()) / 1000 / 60 / 15);

				Button eventButton = new Button(getActivity());
				eventButton.setBackgroundResource(R.drawable.event);
				eventButton.setText(currentEvent.getTitle());
				calenderGrid.addElementAt(eventButton, i, offset, eventLength);
			}
		}

	}

}