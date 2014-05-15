package app.rappla.inet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import app.rappla.OnTaskCompleted;

public class GetRaplaModifiedDateTask extends AsyncTask<String, Double, Date>
{
	private OnTaskCompleted<Date> callbackListener;

	public GetRaplaModifiedDateTask(OnTaskCompleted<Date> listener)
	{
		callbackListener = listener;
	}

	@Override
	protected Date doInBackground(String... uri)
	{
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		try
		{
			response = httpclient.execute(new HttpHead(uri[0]));
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK)
			{
				Header[] header = response.getHeaders("Last-Modified");
				if (header.length == 0)
					throw new HttpException("Last-Modified header not found");

				SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
				Date modifiedTime = format.parse(header[0].getValue());
				// Log.d("CheckForRaplaChangesTask", "modifiedTime: " +
				// modifiedTime);
				return modifiedTime;
			} else
			{
				// Closes the connection
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (Exception e)
		{
			// Log.d("CheckForRaplaChangesTask", "got error: " +
			// e.getMessage());
		}
		return null;
	}

	@Override
	protected void onCancelled(Date result)
	{
		super.onCancelled(result);
		callbackListener.onTaskCompleted(result);
	}

	@Override
	protected void onPostExecute(Date result)
	{
		super.onPostExecute(result);
		callbackListener.onTaskCompleted(result);
	}
}
