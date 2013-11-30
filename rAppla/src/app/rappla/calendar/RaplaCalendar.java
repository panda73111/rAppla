package app.rappla.calendar;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class RaplaCalendar
{
	private Hashtable<Integer, Set<RaplaEvent>> eventCal;

	public RaplaCalendar()
	{
		eventCal = new Hashtable<Integer, Set<RaplaEvent>>();
	}

	public void parse(Reader reader) throws CalendarFormatException,
			IOException
	{
		CalendarParser calParser = new ICalendarParser();
		calParser.parse(reader);
		List<ParseError> errors = calParser.getAllErrors();
		if (errors.size() > 0)
			throw new CalendarFormatException(errors.size()
					+ " iCal parsing errors");

		for (Event e : calParser.dataStore.getAllEvents())
		{
			RaplaEvent re = RaplaEvent.FromEvent(e);
			addEvent(re);

			Rrule rule = e.getRrule();
			if (rule != null)
			{
				// is recurring event
				List<Date> recurrences = rule.generateRecurrances(
						e.getStartDate(), null);

				for (Date d : recurrences)
				{
					addEvent(RaplaEvent.FromRecurringRaplaEvent(re,
							d.toCalendar()));
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
}
