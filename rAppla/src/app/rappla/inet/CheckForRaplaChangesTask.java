package app.rappla.inet;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;

import android.os.AsyncTask;
import app.rappla.OnTaskCompleted;

public class CheckForRaplaChangesTask extends AsyncTask<String, Double, Boolean>
{
	private OnTaskCompleted<Boolean> callbackListener;

	public CheckForRaplaChangesTask(OnTaskCompleted<Boolean> listener)
	{
		callbackListener = listener;
	}

	@Override
	protected Boolean doInBackground(String... uri)
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

				DateTime modifiedTime = DateTime.parse(header[0].toString());
				return modifiedTime.isAfterNow();
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
	protected void onCancelled(Boolean result)
	{
		super.onCancelled(result);
		callbackListener.onTaskCompleted(result);
	}

	@Override
	protected void onPostExecute(Boolean result)
	{
		super.onPostExecute(result);
		callbackListener.onTaskCompleted(result);
	}
}
