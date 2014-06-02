package app.rappla.inet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import app.rappla.OnTaskCompleted;
import app.rappla.calendar.ParseRaplaTask;
import app.rappla.calendar.RaplaCalendar;

public class DownloadRaplaTask extends AsyncTask<String, Double, InputStream>
{
	private ProgressDialog dlg;
	private Context ctx;
	private OnTaskCompleted<RaplaCalendar> callbackListener;
	private boolean showDialog = true;
	
	public DownloadRaplaTask(Context context, OnTaskCompleted<RaplaCalendar> listener, boolean showDialog)
	{
		this(context, listener);
		this.showDialog = showDialog;
	}
	public DownloadRaplaTask(Context context, OnTaskCompleted<RaplaCalendar> listener)
	{
		ctx = context;
		callbackListener = listener;
		dlg = new ProgressDialog(context);
		dlg.setTitle("Aktualisiere");
		dlg.setMessage("Bitte warten...");
		dlg.setCancelable(true);
		dlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dlg.setMax(100);
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

		dlg.setProgress(0);
		if (showDialog)
			dlg.show();
	}

	@Override
	protected InputStream doInBackground(String... uri)
	{
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		try
		{
			if(uri[0]==null)
				return null;
			
			response = httpclient.execute(new HttpGet(uri[0]));
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK)
			{
				// a buffer is needed because reading the network stream
				// is not allowed from within the UI thread
				HttpEntity ent = response.getEntity();
				long totalLen = ent.getContentLength();

				if (totalLen < 0)
				{
					// ical file size not known
					ByteArrayOutputStream bOutStr = new ByteArrayOutputStream();
					ent.writeTo(bOutStr);
					return new ByteArrayInputStream(bOutStr.toByteArray());
				} else
				{
					byte[] buf = new byte[(int) totalLen];
					InputStream inStr = ent.getContent();

					int bytesRead = 0, totalBytesRead = 0;
					do
					{
						totalBytesRead += bytesRead;
						onProgressUpdate((double) totalBytesRead / totalLen * 100.0d);
						bytesRead = inStr.read(buf, totalBytesRead, 16);
					} while (bytesRead != -1);
					onProgressUpdate(100.0);

					return new ByteArrayInputStream(buf, 0, totalBytesRead);
				}

			} else
			{
				// Closes the connection
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (Exception e)
		{
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Double... values)
	{
		super.onProgressUpdate(values);
		
		dlg.setProgress(values[0].intValue());
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
	protected void onPostExecute(InputStream result)
	{
		super.onPostExecute(result);

		if (showDialog)
			dlg.dismiss();

		if (result == null)
		{
			if(callbackListener != null)
				callbackListener.onTaskCompleted(null);
			return;
		}

		new ParseRaplaTask(ctx, callbackListener, showDialog).execute(result);
	}
}
