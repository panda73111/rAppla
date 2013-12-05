package app.rappla.calendar;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.os.AsyncTask;

public class ParseRaplaTask extends AsyncTask<InputStream, Integer, RaplaCalendar>
{
	public ParseRaplaTask()
	{
	}
	
	@Override
	protected RaplaCalendar doInBackground(InputStream... stream)
	{
		RaplaCalendar cal = new RaplaCalendar();
		try
		{
			cal.parse(new InputStreamReader(stream[0]));
		} catch (CalendarFormatException e)
		{
			return null;
		} catch (IOException e)
		{
			return null;
		}
		return cal;
	}
	
	@Override
	protected void onPostExecute(RaplaCalendar result)
	{
		super.onPostExecute(result);
		
		if (result == null)
			return;
		
		result.save();
	}
}
