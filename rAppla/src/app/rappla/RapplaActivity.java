package app.rappla;

import android.app.*;
import android.app.ActionBar.Tab;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import app.rappla.calendar.*;
import app.rappla.fragments.*;
import app.rappla.inet.*;

public class RapplaActivity extends Activity
{
	private static final String ICAL_URL = "http://rapla.dhbw-karlsruhe.de/rapla?page=iCal&user=vollmer&file=tinf12b3";
	
	RapplaFragment[] fragments = new RapplaFragment[] { new WeekFragment(), new DayFragment(), new TrainFragment() };
	private Tab[] tabs = new Tab[fragments.length];
	private int selectedTab = 0;

	private RaplaCalendar calendar;
	private GestureDetector gestDetector;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		gestDetector = new GestureDetector(this, new RapplaGestureListener());
		calendar = RaplaCalendar.load();
		
		if (calendar == null)
		{
			// calendar is not saved locally, download it
			calendar = new RaplaCalendar();
			new DownloadRaplaTask(this).execute(ICAL_URL);
		}
		
		setContentView(R.layout.activity_rappla);
		configureActionBar(savedInstanceState);
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
		new DownloadRaplaTask(this).execute(ICAL_URL);
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

	public RaplaCalendar getCalender()
	{
		return calendar;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return gestDetector.onTouchEvent(event);
	}
}