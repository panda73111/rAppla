package app.rappla.inet;

import java.io.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import app.rappla.calendar.ParseRaplaTask;

public class DownloadRaplaTask extends AsyncTask<String, Double, InputStream>
{
	@Override
	protected InputStream doInBackground(String... uri)
	{
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		try
		{
			response = httpclient.execute(new HttpGet(uri[0]));
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK)
			{
				// a buffer is needed because reading the network stream
				// is not allowed from  within the UI thread
				ByteArrayOutputStream buf = new ByteArrayOutputStream();
				response.getEntity().writeTo(buf);
				buf.close();
				return new ByteArrayInputStream(buf.toByteArray());
				
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
	protected void onPostExecute(InputStream result)
	{
		super.onPostExecute(result);
		
		if (result != null)
			new ParseRaplaTask().execute(result);
	}
}
