package app.rappla;

import java.util.Calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import app.rappla.activities.RapplaActivity;
import app.rappla.calendar.RaplaEvent;

public class RapplaWidgetProvider extends AppWidgetProvider
{
	Context context;

	public void onEnabled(Context context)
	{
		super.onEnabled(context);
	}

	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		PendingIntent pendingIntent;
		RemoteViews views;
		String text;
		try
		{
			Intent intent = new Intent(context, RapplaActivity.class);
			pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);

			
			RaplaEvent nextEvent = RapplaActivity.getInstance().getCalender().getNextEvent();

			
			
			Calendar startTime 	= nextEvent.getStartTime();
			Calendar endTime 	= nextEvent.getEndTime();

			// if this ever makes problems, its because of the "%12" on month
			
			text = nextEvent.getTitle() + '\n' + 
					startTime.get(Calendar.DAY_OF_MONTH) + '/' + (startTime.get(Calendar.MONTH)%12+1) + '/' + startTime.get(Calendar.YEAR) + '\n' +
					
					startTime.get(Calendar.HOUR_OF_DAY) + ':' + startTime.get(Calendar.MINUTE) + 
					'-' + 
					endTime.get(Calendar.HOUR_OF_DAY) + ':' + endTime.get(Calendar.MINUTE);
		}
		catch(NullPointerException e)
		{
			Log.e("RaplaWidget", "Nulpointerexception");
			e.printStackTrace();
			return; // Something somewhere went terribly wrong
		}
		for (int i = 0; i < appWidgetIds.length; i++)
		{
			int appWidgetId = appWidgetIds[i];

			views.setOnClickPendingIntent(R.id.widgetImage, pendingIntent);
			views.setOnClickPendingIntent(R.id.widgetText, pendingIntent);
			views.setTextViewText(R.id.widgetText, text);
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}
