package app.rappla.calendar;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import app.rappla.activities.RapplaActivity;

public class ParseRaplaTask extends AsyncTask<InputStream, Integer, RaplaCalendar>
{
	private ProgressDialog dlg;
	
	public ParseRaplaTask(Context ctx)
	{
		dlg = new ProgressDialog(ctx);
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

		dlg.hide();
	}
	
	@Override
	protected void onPostExecute(RaplaCalendar result)
	{
		super.onPostExecute(result);
		
		dlg.dismiss();
		
		if (result == null)
			return;
		
		result.save();
		
		RapplaActivity activity = RapplaActivity.getInstance();
		activity.setCalendar(result);
		
		Activity act = RapplaActivity.getInstance();
		
		Intent intent = act.getIntent();
		act.finish();
		act.startActivity(intent);
	}
}
