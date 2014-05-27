package app.rappla.ui.grid;

import app.rappla.calendar.RaplaEvent;
import app.rappla.ui.fragments.CalendarFragment;

public class RapplaGridElementLayout
{
	int column;
	int offset;
	int rowSpan;

	public RapplaGridElementLayout(int column, int offset, int rowSpan)
	{
		this.column = column;
		this.offset = offset;
		this.rowSpan = rowSpan;
	}

	public RapplaGridElementLayout(int column, RaplaEvent raplaEvent)
	{
		//int eventOffset = (raplaEvent.getTimeDifferenceInMinutes(CalendarFragment.getEarliestStart(raplaEvent.getDate())) / CalendarFragment.timeInterval);
		this(column, 
				(int) (raplaEvent.getTimeDifferenceInMinutes(CalendarFragment.getEarliestStart(raplaEvent.getDate())) / CalendarFragment.timeInterval),
				(int) Math.min(CalendarFragment.getDayDuration() / CalendarFragment.timeInterval - (raplaEvent.getTimeDifferenceInMinutes(CalendarFragment.getEarliestStart(raplaEvent.getDate())) / CalendarFragment.timeInterval),(raplaEvent.getDurationInMinutes() / CalendarFragment.timeInterval))
				);
		

	}
}
