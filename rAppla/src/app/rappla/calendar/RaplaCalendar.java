package app.rappla.calendar;

import java.io.*;
import java.util.*;

import android.content.Context;
import app.rappla.StaticContext;

public class RaplaCalendar implements Serializable
{
	private static final String CALENDAR_DB_FILE = "rapla.db";

	private Hashtable<Integer, Set<RaplaEvent>> eventCal;

	public RaplaCalendar()
	{
		eventCal = new Hashtable<Integer, Set<RaplaEvent>>();
	}

	public void parse(Reader reader) throws CalendarFormatException, IOException
	{
		CalendarParser calParser = new ICalendarParser();
		calParser.parse(reader);
		List<ParseError> errors = calParser.getAllErrors();
		if (errors.size() > 0)
			throw new CalendarFormatException(errors.size() + " iCal parsing errors");

		for (Event e : calParser.dataStore.getAllEvents())
		{
			RaplaEvent re = RaplaEvent.FromEvent(e);
			addEvent(re);

			Rrule rule = e.getRrule();
			if (rule != null)
			{
				// is recurring event
				List<Date> recurrences = rule.generateRecurrances(e.getStartDate(), null);

				for (Date d : recurrences)
				{
					addEvent(RaplaEvent.FromRecurringRaplaEvent(re, d.toCalendar()));
				}
			}

		}
	}

	public Set<RaplaEvent> getEventsAtDate(Calendar date)
	{
		return eventCal.get(getDateHash(date));
	}

	public Set<RaplaEvent> getEventsAtDate(int day, int month, int year)
	{
		// the month in the Calendar class is zero based
		return eventCal.get(getDateHash(day, month - 1, year));
	}

	private void addEvent(RaplaEvent event)
	{
		Integer key = getDateHash(event.getStartTime());

		if (eventCal.containsKey(key))
		{
			eventCal.get(key).add(event);
		} else
		{
			Set<RaplaEvent> l = new TreeSet<RaplaEvent>();
			l.add(event);
			eventCal.put(key, l);
		}
	}

	private int getDateHash(Calendar date)
	{
		// we need that (whole) day's date as key
		return getDateHash(date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH), date.get(Calendar.YEAR));
	}

	private int getDateHash(int day, int month, int year)
	{
		return year * 365 + month * 31 + day - 1;
	}

	public static RaplaCalendar load()
	{
		android.util.Log.d("cal", "loading calendar");
		try
		{
			InputStream inStr = StaticContext.getContext().openFileInput(CALENDAR_DB_FILE);
			ObjectInputStream objInStr = new ObjectInputStream(inStr);
			RaplaCalendar ret = (RaplaCalendar) objInStr.readObject();
			objInStr.close();
			return ret;
		} catch (IOException ex)
		{
			android.util.Log.d("cal", "error loading calendar: " + ex);
			return null;
		} catch (ClassNotFoundException ex)
		{
			android.util.Log.d("cal", "error loading calendar: " + ex);
			return null;
		}
	}

	public void save()
	{
		android.util.Log.d("cal", "saving calendar");
		try
		{
			OutputStream outStr = StaticContext.getContext().openFileOutput(CALENDAR_DB_FILE, Context.MODE_PRIVATE);
			ObjectOutputStream objOutStr = new ObjectOutputStream(outStr);
			objOutStr.writeObject(this);
			objOutStr.close();
		} catch (IOException ex)
		{
			android.util.Log.d("cal", "error saving calendar: " + ex);
		}
	}
}
