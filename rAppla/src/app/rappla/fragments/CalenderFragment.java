package app.rappla.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import RapplaGrid.RapplaGrid;
import RapplaGrid.RapplaGridElement;
import app.rappla.calendar.RaplaEvent;

public abstract class CalenderFragment extends RapplaFragment
{
	protected static final String gridElementPrefix = "GL#";
	protected static final String weekGridPrefix = "WG#";
	protected RapplaGrid calendarGrid;

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
		/*
		 * SharedPreferences sp =
		 * PreferenceManager.getDefaultSharedPreferences(getActivity());
		 * 
		 * tv1.setText("Gmail sync is " + sp.getBoolean("gmailSync", false));
		 * tv2.setText("Push notifications are " +
		 * sp.getBoolean("pushNotifications", false));
		 * tv3.setText("Offline sync is " + sp.getBoolean("offlineSync",
		 * false)); tv4.setText("updateInterval is " +
		 * sp.getString("updateInterval", "0 min"));
		 */
	}

	protected void configureGrid()
	{/*
	 * int rowCount=0;
	 * 
	 * calenderGrid.setRowCount(rowCount);
	 */
		// Set up your grid here
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

	protected ArrayList<RapplaGridElement> createDayGrid(Set<RaplaEvent> eventSet, int column)
	{
		ArrayList<RapplaGridElement> allEventButtons = new ArrayList<RapplaGridElement>();

		if (eventSet != null)
			for (RaplaEvent currentEvent : eventSet)
			{
				RapplaGridElement eventElement = new RapplaGridElement(getActivity(), currentEvent, column);
				allEventButtons.add(eventElement);
			}
		return allEventButtons;
	}
}
