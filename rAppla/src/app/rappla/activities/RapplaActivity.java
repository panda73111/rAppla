/*<<<<<<< HEAD
package app.rappla.activities;

import android.app.*;
import android.app.ActionBar.Tab;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import app.rappla.*;
import app.rappla.calendar.*;
import app.rappla.inet.*;
import app.rappla.ui.fragments.*;

public class RapplaActivity extends Activity
{
	public static final String ICAL_URL = "http://rapla.dhbw-karlsruhe.de/rapla?page=iCal&user=vollmer&file=tinf12b3";
	
	RapplaFragment[] fragments = new RapplaFragment[] { new WeekPagerFragment(), new DayFragment()};
	private Tab[] tabs = new Tab[fragments.length];

	private RaplaCalendar calendar;
	private GestureDetector gestDetector;
	private static RapplaActivity instance;
	public static boolean isTest = false;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState!=null)
		{
			RapplaActivity.isTest = savedInstanceState.getBoolean("isTest", false);
		}
		
		instance = this;
		
		gestDetector = new GestureDetector(this, new RapplaGestureListener());
		calendar = RaplaCalendar.load();
		
		if (calendar == null)
		{
			// calendar is not saved locally, download it
			calendar = new RaplaCalendar();
			new DownloadRaplaTask(this).execute(ICAL_URL);
		}
		
		setContentView(R.layout.layout_rappla);
		configureActionBar(savedInstanceState);
	}

	public int getFragmentCount()
	{
		return fragments.length;
	}
	public Fragment getFragment(int i)
	{
		return fragments[i];
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
		if(actionBar==null)
			return;
		
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
=======*/
package app.rappla.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import app.rappla.R;
import app.rappla.RapplaGestureListener;
import app.rappla.RapplaTabListener;
import app.rappla.calendar.RaplaCalendar;
import app.rappla.inet.DownloadRaplaTask;
import app.rappla.ui.fragments.DayFragment;
import app.rappla.ui.fragments.RapplaFragment;
import app.rappla.ui.fragments.WeekPagerFragment;

public class RapplaActivity extends Activity
{
	private static final String ICAL_URL = "http://rapla.dhbw-karlsruhe.de/rapla?page=iCal&user=vollmer&file=tinf12b3";
	private static final int TAB_CNT = 2;
	private static final int WEEKPAGER_FRAGMENT_INDEX = 0;
	private static final int DAY_FRAGMENT_INDEX = 1;

	private RapplaFragment[] fragments;
	private Tab[] tabs;

	private RaplaCalendar calendar;
	private GestureDetector gestDetector;
	private static RapplaActivity instance;
	public static boolean isTest = false;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState!=null)
		{
			RapplaActivity.isTest = savedInstanceState.getBoolean("isTest", false);
		}
		
		instance = this;
		fragments = new RapplaFragment[TAB_CNT];
		fragments[WEEKPAGER_FRAGMENT_INDEX] = new WeekPagerFragment();
		fragments[DAY_FRAGMENT_INDEX] = new DayFragment();
		tabs = new Tab[TAB_CNT];

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

	public int getFragmentCount()
	{
		return fragments.length;
	}
	public Fragment getFragment(int i)
	{
		return fragments[i];
	}
	public WeekPagerFragment getWeekPagerFragment()
	{
		return (WeekPagerFragment) fragments[WEEKPAGER_FRAGMENT_INDEX];
	}

	public DayFragment getDayFragment()
	{
		return (DayFragment) fragments[DAY_FRAGMENT_INDEX];
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
		if(actionBar==null)
			return;
		
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

		// redraw grids
		FragmentManager frMan = getFragmentManager();
		FragmentTransaction fragTransaction = frMan.beginTransaction();
		ActionBar aBar = getActionBar();
		Fragment fr;
		switch (aBar.getSelectedNavigationIndex())
		{
		case WEEKPAGER_FRAGMENT_INDEX:
			fr = getWeekPagerFragment();
			break;
		case DAY_FRAGMENT_INDEX:
			fr = getDayFragment();
			break;
		default:
			return;
		}
		fragTransaction.detach(fr);
		fragTransaction.attach(fr);
		fragTransaction.commit();
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
