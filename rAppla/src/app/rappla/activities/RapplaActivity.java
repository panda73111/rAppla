package app.rappla.activities;

import android.app.*;
import android.app.ActionBar.Tab;
import android.content.*;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.*;
import app.rappla.*;
import app.rappla.calendar.*;
import app.rappla.inet.*;
import app.rappla.ui.fragments.*;

public class RapplaActivity extends FragmentActivity
{
	private static final String ICAL_URL = "http://rapla.dhbw-karlsruhe.de/rapla?page=iCal&user=vollmer&file=tinf12b3";

	RapplaFragment[] fragments = new RapplaFragment[] { new WeekPagerFragment(), new DayFragment(), new TrainFragment() };
	private Tab[] tabs = new Tab[fragments.length];

	private RaplaCalendar calendar;
	private GestureDetector gestDetector;
	private static RapplaActivity instance;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		instance = this;

		gestDetector = new GestureDetector(this, new RapplaGestureListener());
		calendar = RaplaCalendar.load();

		if (calendar == null)
		{
			// calendar is not saved locally, download it
			calendar = new RaplaCalendar();
			new DownloadRaplaTask(this).execute(ICAL_URL);
		}

		configureActionBar(savedInstanceState);
		setContentView(R.layout.layout_rappla);
	}

	public WeekPagerFragment getWeekPagerFragment()
	{
		return (WeekPagerFragment) fragments[0];
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		int i = getActionBar().getSelectedNavigationIndex();
		outState.putInt("selectedTab", i);
	}

	public void onStart()
	{
		super.onStart();
	}

	private void configureActionBar(Bundle savedInstanceState)
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

		int selectedIndex = 0;
		if (savedInstanceState != null)
			selectedIndex = savedInstanceState.getInt("selectedTab");
		actionBar.selectTab(tabs[selectedIndex]);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
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
		new DownloadRaplaTask(this).execute(ICAL_URL);
		return true;
	}

	public boolean onSettingsButtonPressed(MenuItem item)
	{
		Intent myIntent = new Intent(this, SettingsActivity.class);
		startActivity(myIntent);
		return true;
	}

	public RaplaCalendar getCalender()
	{
		return calendar;
	}

	public void setCalendar(RaplaCalendar cal)
	{
		calendar = cal;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return gestDetector.onTouchEvent(event);
	}

	public static RapplaActivity getInstance()
	{
		return instance;
	}
}