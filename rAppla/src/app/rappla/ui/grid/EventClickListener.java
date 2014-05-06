package app.rappla.ui.grid;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import app.rappla.activities.EventActivity;

public class EventClickListener implements View.OnClickListener
{
	String eventID;
	Context context;
	
	public EventClickListener(String eventID, Context context)
	{
		this.eventID	= eventID;
		this.context	= context;
	}

	@Override
	public void onClick(View arg0)
	{
		Log.d("EventClicked", "EventID: " + eventID);
		 
		Intent intent = new Intent(context, EventActivity.class);
		intent.putExtra(EventActivity.eventIDKey, eventID);
		context.startActivity(intent);
	}
	
}
