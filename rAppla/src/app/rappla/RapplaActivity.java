package app.rappla;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import app.rappla.calendar.CalendarFormatException;
import app.rappla.calendar.RaplaCalendar;
import app.rappla.calendar.RaplaEvent;
import app.rappla.fragments.DayFragment;
import app.rappla.fragments.RapplaFragment;
import app.rappla.fragments.TrainFragment;
import app.rappla.fragments.WeekFragment;

public class RapplaActivity extends Activity
{

	RapplaFragment[] fragments = new RapplaFragment[] { new WeekFragment(), new DayFragment(), new TrainFragment() };
	Tab[] tabs = new Tab[fragments.length];
	RaplaCalendar calender;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_rappla);
		configureActionBar();
	}

	public void onStart()
	{
		super.onStart();
		calender = readCalenderFile("tinf12b3.ics", true);
	}

	public static RaplaCalendar readCalenderFile(String fileName, boolean doDebugOutput)
	{

		AssetManager am = StaticContext.getContext().getAssets();
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
		RaplaCalendar rapCal = new RaplaCalendar();

		InputStream is;
		try
		{
			is = am.open(fileName);
			rapCal.parse(new InputStreamReader(is));

			if (!doDebugOutput)
				return rapCal;

			Calendar today = Calendar.getInstance();

			System.out.println("today's events:");
			Set<RaplaEvent> events = rapCal.getEventsAtDate(today);
			if (events == null)
				System.out.println("none");
			else
				for (RaplaEvent e : events)
				{
					System.out.println("from " + timeFormat.format(e.getStartTime().getTime()) + " to " + timeFormat.format(e.getEndTime().getTime())
							+ ": " + e.getTitle());
				}
		} catch (IOException e)
		{
			// some error reading the stream
			e.printStackTrace();
		} catch (CalendarFormatException e)
		{
			// iCal syntax error or necessary event attributes
			// (type, title, resources, start/end time or id) missing
			e.printStackTrace();
		}
		return rapCal;
	}

	private void configureActionBar()
	{
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setTitle(R.string.app_name);
		actionBar.setDisplayHomeAsUpEnabled(false);

		for (int i = 0; i < tabs.length; i++)
		{
			// Set title
			tabs[i] = actionBar.newTab().setText(fragments[i].getTitle());
			// Add Listener
			tabs[i].setTabListener(new RapplaTabListener(fragments[i]));
			// Add Tab to actionBar
			actionBar.addTab(tabs[i]);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_settings:
			return onSettingsButtonPressed(item);
		case R.id.action_refresh:
			return onRefreshButtonPressed(item);
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean onRefreshButtonPressed(MenuItem item)
	{
		Toast.makeText(this, "Not implemented yet.", Toast.LENGTH_SHORT).show();
		return true;
	}

	public boolean onSettingsButtonPressed(MenuItem item)
	{
		Intent myIntent = new Intent(this, SettingsActivity.class);
		startActivity(myIntent);
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{

		if (resultCode == RESULT_OK)
		{
			Toast.makeText(this, "An Activity has ended", Toast.LENGTH_LONG).show();
		}
	}

}