package app.rappla.activities;

import java.net.URL;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import app.rappla.OnTaskCompleted;
import app.rappla.R;
import app.rappla.RapplaBackgroundUpdateService;
import app.rappla.RapplaGestureListener;
import app.rappla.RapplaTabListener;
import app.rappla.calendar.RaplaCalendar;
import app.rappla.inet.DownloadRaplaTask;
import app.rappla.ui.fragments.DayPagerFragment;
import app.rappla.ui.fragments.RapplaFragment;
import app.rappla.ui.fragments.RapplaPagerFragment;
import app.rappla.ui.fragments.WeekPagerFragment;

public class RapplaActivity extends Activity
{
	public static final String lastCalendarHashString = "lastCalendarHash";

	private static final String ICAL_URL_KEY = "ICAL_URL";
	// "http://rapla.dhbw-karlsruhe.de/rapla?page=iCal&user=vollmer&file=tinf12b3";

	private static final int TAB_CNT = 2;
	private static final int WEEKPAGER_FRAGMENT_INDEX = 0;
	private static final int DAYPAGER_FRAGMENT_INDEX = 1;
	private static final String selectedTabKey = "selectedTab";

	private static final int SETTINGS_REQ_CODE = 3456;
	private static final int NOTIFIER_REQ_CODE = 1234;

	private RapplaFragment[] fragments;
	private Tab[] tabs;
	private Menu menu;

	private RaplaCalendar calendar;
	private GestureDetector gestDetector;
	private static RapplaActivity instance;

	private OnTaskCompleted<RaplaCalendar> raplaDownloadedHandler = new OnTaskCompleted<RaplaCalendar>()
	{
		@Override
		public void onTaskCompleted(RaplaCalendar result)
		{
			if (result == null)
				// something went wrong
				return;

			if (getCalender() != null)
			{
				if (result.hashCode() != getCalender().hashCode())
				{
					result.save(RapplaActivity.getInstance());
					setCalendar(result);
				}
			}

			// hide spinning icon in the action bar
			setProgressBarIndeterminateVisibility(false);
			MenuItem item = menu.findItem(R.id.action_refresh);
			item.setVisible(true);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		instance = this;
		fragments = new RapplaFragment[TAB_CNT];
		fragments[WEEKPAGER_FRAGMENT_INDEX] = new WeekPagerFragment();
		fragments[DAYPAGER_FRAGMENT_INDEX] = new DayPagerFragment();
		tabs = new Tab[TAB_CNT];

		gestDetector = new GestureDetector(this, new RapplaGestureListener());
		calendar = RaplaCalendar.load();

		// enable spinning icon in the action bar
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		if (calendar == null)
		{
			// calendar is not saved locally, download it

			if (getCalendarURL(this) == null)
			{
				openURLDialog();
			}

			calendar = new RaplaCalendar();
			// start background download
			new DownloadRaplaTask(this, raplaDownloadedHandler).execute(getCalendarURL(this));
		}

		configureActionBar(savedInstanceState);
		setContentView(R.layout.layout_rappla);

		
		configureNotifier();
	}
	
	public void onStart()
	{
		super.onStart();
		// Cancel still open notifications
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(RapplaBackgroundUpdateService.ID_UPDATE_NOTIFICATION);
		
		setCalendar(calendar);
	}

	private void openURLDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		
		LinearLayout vg = new LinearLayout(this);
		final EditText inputText = new EditText(this);
		inputText.setId(123456);
		
		TextView message = new TextView(this);
		message.setText(R.string.url_dialog);
		message.setGravity(Gravity.CENTER_HORIZONTAL);
		
		vg.setOrientation(LinearLayout.VERTICAL);
		
		
		vg.addView(message);
		vg.addView(inputText);
		
		OnClickListener listener = new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				switch (which)
				{
				case Dialog.BUTTON_POSITIVE:
					applyURL(inputText.getText().toString());
					onRefreshButtonPressed(null);
					break;
				case Dialog.BUTTON_NEGATIVE:
					Toast.makeText(RapplaActivity.this, "Kein Kalendar geladen.", Toast.LENGTH_LONG).show();
					break;
				}
			}
		};

		builder	.setTitle("Kalender URL")
				.setNegativeButton(R.string.cancel, listener)
				.setPositiveButton(R.string.ok, listener)
				.setView(vg);
				//.setMessage(R.string.url_dialog);

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		saveSettings();
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

	public DayPagerFragment getDayPagerFragment()
	{
		return (DayPagerFragment) fragments[DAYPAGER_FRAGMENT_INDEX];
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		int i = getActionBar().getSelectedNavigationIndex();
		outState.putInt(selectedTabKey, i);
	}

	private void configureActionBar(Bundle savedInstanceState)
	{
		ActionBar actionBar = getActionBar();
		if (actionBar == null)
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
			selectedIndex = savedInstanceState.getInt(selectedTabKey);
		actionBar.selectTab(tabs[selectedIndex]);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_main, menu);
		this.menu = menu;
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
		case R.id.action_today:
			return onTodayButtonPressed(item);
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean onRefreshButtonPressed(MenuItem item)
	{
		// show spinning icon in the action bar
		setProgressBarIndeterminateVisibility(true);
		// start background download

		if(item!=null)
			item.setVisible(false);

		new DownloadRaplaTask(this, raplaDownloadedHandler).execute(getCalendarURL(this));
		return true;
	}

	public boolean onSettingsButtonPressed(MenuItem item)
	{
		Intent myIntent = new Intent(this, SettingsActivity.class);
		startActivityForResult(myIntent, SETTINGS_REQ_CODE);
		return true;
	}

	public boolean onTodayButtonPressed(MenuItem item)
	{
		RapplaPagerFragment fragment;

		switch (getActionBar().getSelectedNavigationIndex())
		{
		case WEEKPAGER_FRAGMENT_INDEX:
			fragment = getWeekPagerFragment();
			break;
		case DAYPAGER_FRAGMENT_INDEX:
			fragment = getDayPagerFragment();
			break;
		default:
			return false;
		}
		fragment.goToToday();
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SETTINGS_REQ_CODE)
		{
			configureNotifier();
			applyURL(getCalendarURL(this));
			if(resultCode == SettingsActivity.RESULT_UPDATE_CALENDAR)
				onRefreshButtonPressed(null);
		}
	}

	private boolean isValidURL(String url)
	{	
		try
		{
			new URL(url).toURI(); // this is made to check whether its a valid URL
			return true;
			
		} catch (Exception ex)
		{
			return false;
		}
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
		case DAYPAGER_FRAGMENT_INDEX:
			fr = getDayPagerFragment();
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

	private void saveSettings()
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(lastCalendarHashString, getCalender().hashCode());
		editor.commit();
	}

	private void configureNotifier()
	{
		Intent intent = new Intent(this, RapplaBackgroundUpdateService.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), NOTIFIER_REQ_CODE, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

		boolean doNotify = preferences.getBoolean("offlineSync", false);
		long interval = Long.valueOf(preferences.getString("updateInterval", "86400"));

		if (!doNotify)
		{
			alarmManager.cancel(pendingIntent);
		} else
		{
			alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, interval, interval, pendingIntent);
		}
	}

	public static String getCalendarURL(Context context)
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getString(ICAL_URL_KEY, null);
	}
	public static void setCalendarURL(Context context, String url)
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = preferences.edit();
		editor.putString(ICAL_URL_KEY, url);
		editor.commit();
	}
	public void applyURL(String url)
	{
		url = url.replace("page=calendar", "page=ical");
		
		if(!isValidURL(url))
		{
			Toast.makeText(RapplaActivity.this, "Kein Kalendar geladen.", Toast.LENGTH_LONG).show();
			return;
		}
		
		setCalendarURL(RapplaActivity.this, url);
	}
}
