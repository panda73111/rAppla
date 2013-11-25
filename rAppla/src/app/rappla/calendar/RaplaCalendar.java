package app.rappla.calendar;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class RaplaCalendar
{
	private Hashtable<Integer, Set<RaplaEvent>> _eventCal;

	public RaplaCalendar()
	{
		_eventCal = new Hashtable<Integer, Set<RaplaEvent>>();
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
		Integer key = getDateHash(date);

		return _eventCal.get(key);
	}

	private void addEvent(RaplaEvent event)
	{
		Integer key = getDateHash(event.getStartTime());

		if (_eventCal.containsKey(key))
		{
			_eventCal.get(key).add(event);
		} else
		{
			Set<RaplaEvent> l = new TreeSet<RaplaEvent>();
			l.add(event);
			_eventCal.put(key, l);
		}
	}

	private int getDateHash(Calendar date)
	{
		// we need that (whole) day's date as key
		int hash = date.get(Calendar.YEAR) * 365 + date.get(Calendar.MONTH)
				* 31 + date.get(Calendar.DAY_OF_MONTH);

		return hash;
	}
}
