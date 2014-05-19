package app.rappla.calendar;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import app.rappla.OnTaskCompleted;

public class ParseRaplaTask extends AsyncTask<InputStream, Integer, RaplaCalendar>
{
	private ProgressDialog dlg;
	private OnTaskCompleted<RaplaCalendar> callbackListener;
	private boolean showDialog = true;
	
	public ParseRaplaTask(Context context, OnTaskCompleted<RaplaCalendar> listener, boolean showDialog)
	{
		this(context, listener);
		this.showDialog = showDialog;
	}
	public ParseRaplaTask(Context context, OnTaskCompleted<RaplaCalendar> listener)
	{
		callbackListener = listener;
		dlg = new ProgressDialog(context);
		dlg.setTitle("Lade Kalender");
		dlg.setMessage("Bitte warten...");
		dlg.setCancelable(true);
		dlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dlg.setButton(ProgressDialog.BUTTON_NEGATIVE, "Abbrechen", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				cancel(true);
			}
		});
	}

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();

		if (showDialog)
			dlg.show();
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
	protected void onCancelled()
	{
		super.onCancelled();

		if (showDialog)
			dlg.dismiss();

		if(callbackListener != null)
			callbackListener.onTaskCompleted(null);
	}

	@Override
	protected void onPostExecute(RaplaCalendar result)
	{
		super.onPostExecute(result);
	
		if (showDialog)
			dlg.dismiss();

		if(callbackListener != null)
			callbackListener.onTaskCompleted(result);
	}
}
