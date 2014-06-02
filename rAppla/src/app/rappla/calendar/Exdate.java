package app.rappla.calendar;

import java.util.ArrayList;
import java.util.List;

public class Exdate extends Property implements Constants
{
	ArrayList<Date> dateList;

	public Exdate(String icalStr) throws ParseException
	{
		super(icalStr);

		dateList = new ArrayList<Date>();
		String[] arguments = value.split(",");

		try
		{
			for (int i = 0; i < arguments.length; i++)
				dateList.add(new Date("EXDATE:" + arguments[i]));
		} catch (BogusDataException e)
		{
			return;
		}

	}
	public List<Date> getDates()
	{
		return dateList;
	}

}
