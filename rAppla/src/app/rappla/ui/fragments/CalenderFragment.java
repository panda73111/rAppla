package app.rappla.ui.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import android.content.Context;
import app.rappla.calendar.RaplaEvent;
import app.rappla.ui.grid.RapplaGridElement;

public abstract class CalenderFragment extends RapplaFragment
{
	protected static final String gridElementPrefix = "GL#";
	protected static final String weekGridPrefix = "WG#";

	public static final int timeInterval = 15; // In minutes

	private static final int earliestHour = 8;
	private static final int earliestMinute = 0;
	private static final int earliestAMPM = Calendar.AM;
	private static final int latestHour = 7;
	private static final int latestMinute = 0;
	private static final int latestAMPM = Calendar.PM;

	public void onStart()
	{
		super.onStart();
		configureGrid();
	}

	protected void configureGrid()
	{
	}

	protected static ArrayList<RapplaGridElement> createDayGrid(Context context, Set<RaplaEvent> eventSet, int column)
	{
		ArrayList<RapplaGridElement> allEventButtons = new ArrayList<RapplaGridElement>();

		for (RaplaEvent currentEvent : eventSet)
		{
			RapplaGridElement eventElement = new RapplaGridElement(context, currentEvent, column);
			allEventButtons.add(eventElement);
		}
		return allEventButtons;
	}

	public static Calendar getEarliestStart(Calendar day)
	{
		Calendar earliestStart = Calendar.getInstance();
		earliestStart.setTime(day.getTime());
		earliestStart.set(Calendar.AM_PM, earliestAMPM);
		earliestStart.set(Calendar.HOUR, earliestHour);
		earliestStart.set(Calendar.MINUTE, earliestMinute);
		return earliestStart;
	}

	public static Calendar getLatestEnd(Calendar day)
	{
		Calendar latestEnd = Calendar.getInstance();
		latestEnd.setTime(day.getTime());
		latestEnd.set(Calendar.AM_PM, latestAMPM);
		latestEnd.set(Calendar.HOUR, latestHour);
		latestEnd.set(Calendar.MINUTE, latestMinute);
		return latestEnd;
	}

	public static long getDayDuration()
	{
		Calendar today = Calendar.getInstance();
		return RaplaEvent.getTimeDifferenceInMinutes(getEarliestStart(today), getLatestEnd(today));
	}
}
