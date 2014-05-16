package app.rappla.calendar;

import java.util.ArrayList;

public class Exdate extends Property implements Constants
{
	ArrayList<Date> dateList;

	public Exdate(String icalStr) throws ParseException
	{
		super(icalStr);

		dateList = new ArrayList<Date>();
		
		
	}

}
