package app.rappla.activities;

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
import android.os.Bundle;
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
import app.rappla.RaplaBackgroundService;
import app.rappla.RapplaGestureListener;
import app.rappla.RapplaPreferences;
import app.rappla.RapplaTabListener;
import app.rappla.RapplaUtils;
import app.rappla.calendar.RaplaCalendar;
import app.rappla.inet.DownloadRaplaTask;
import app.rappla.ui.fragments.DayPagerFragment;
import app.rappla.ui.fragments.RapplaFragment;
import app.rappla.ui.fragments.RapplaPagerFragment;
import app.rappla.ui.fragments.WeekPagerFragment;

public class RapplaActivity extends Activity
{
	private static final int TAB_CNT = 2;
	private static final int WEEKPAGER_FRAGMENT_INDEX = 0;
	private static final int DAYPAGER_FRAGMENT_INDEX = 1;


    private static RapplaActivity instance;
    private RapplaFragment[] fragments;
	private Tab[] tabs;
	private Menu menu;
    private GestureDetector gestDetector;


    private OnTaskCompleted<RaplaCalendar> raplaDownloadedHandler = new OnTaskCompleted<RaplaCalendar>()
	{
		@Override
		public void onTaskCompleted(RaplaCalendar result)
		{
			if (result == null)
				// something went wrong
				return;

            if (getActiveCalendar() != null) {
                if (result.hashCode() != getActiveCalendar().hashCode()) {
					result.save(RapplaActivity.getInstance());
                    setActiveCalendar(result);
                }
			}

			// hide spinning icon in the action bar
			setProgressBarIndeterminateVisibility(false);
			MenuItem item = menu.findItem(R.id.action_refresh);
			item.setVisible(true);
		}
	};


    public static RapplaActivity getInstance() {
        return instance;
    }


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        fragments = new RapplaFragment[TAB_CNT];
        fragments[WEEKPAGER_FRAGMENT_INDEX] = new WeekPagerFragment();
        fragments[DAYPAGER_FRAGMENT_INDEX] = new DayPagerFragment();
        tabs = new Tab[TAB_CNT];

        gestDetector = new GestureDetector(this, new RapplaGestureListener());
        RaplaCalendar.activeCalendar = RaplaCalendar.load();

        // enable spinning icon in the action bar
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        if (getActiveCalendar() == null) {
            // activeCalendar is not saved locally, download it

            if (RapplaPreferences.getSavedCalendarURL(this) == null) {
                openURLDialog();
            }

            RaplaCalendar.activeCalendar = new RaplaCalendar();
            // start background download
            new DownloadRaplaTask(this, raplaDownloadedHandler).execute(RapplaPreferences.getSavedCalendarURL(this));
        }

        configureActionBar(savedInstanceState);
        setContentView(R.layout.layout_rappla);


        configureNotifier();
    }

    public void onStart() {
        super.onStart();
        // Cancel still open notifications
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(R.integer.Notification_ID_RapplaUpdate);

        setActiveCalendar(getActiveCalendar());
    }

    private void openURLDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        LinearLayout vg = new LinearLayout(this);
        final EditText inputText = new EditText(this);
        inputText.setId(R.id.ID_URL_Edit);

        TextView message = new TextView(this);
        message.setText(R.string.url_dialog);
        message.setGravity(Gravity.CENTER_HORIZONTAL);

        vg.setOrientation(LinearLayout.VERTICAL);


        vg.addView(message);
        vg.addView(inputText);

        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
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

        builder.setTitle("Kalender URL")
                .setNegativeButton(R.string.cancel, listener)
                .setPositiveButton(R.string.ok, listener)
                .setView(vg);
        //.setMessage(R.string.url_dialog);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        RapplaPreferences.setSavedCalendarHash(this, getActiveCalendar().hashCode());
    }

	public WeekPagerFragment getWeekPagerFragment() {
        return (WeekPagerFragment) fragments[WEEKPAGER_FRAGMENT_INDEX];
    }

    public DayPagerFragment getDayPagerFragment() {
        return (DayPagerFragment) fragments[DAYPAGER_FRAGMENT_INDEX];
    }

	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int i = getActionBar().getSelectedNavigationIndex();
        outState.putInt(RapplaPreferences.PREF_KEY_selectedTab, i);
    }

	private void configureActionBar(Bundle savedInstanceState) {
        ActionBar actionBar = getActionBar();
        if (actionBar == null)
            return;

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayHomeAsUpEnabled(false);

        for (int i = 0; i < tabs.length; i++) {
            // Set title
            tabs[i] = actionBar.newTab().setText(fragments[i].getTitle());
            // Add Listener
            tabs[i].setTabListener(new RapplaTabListener(fragments[i]));
            // Add Tab to actionBar
            actionBar.addTab(tabs[i]);
        }

        int selectedIndex = 0;
        if (savedInstanceState != null)
            selectedIndex = savedInstanceState.getInt(RapplaPreferences.PREF_KEY_selectedTab);
        actionBar.selectTab(tabs[selectedIndex]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_main, menu);
        this.menu = menu;
        return true;
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    public boolean onRefreshButtonPressed(MenuItem item) {
        // show spinning icon in the action bar
        setProgressBarIndeterminateVisibility(true);
        // start background download

        if (item != null)
            item.setVisible(false);

        new DownloadRaplaTask(this, raplaDownloadedHandler).execute(RapplaPreferences.getSavedCalendarURL(this));
        return true;
    }

    public boolean onSettingsButtonPressed(MenuItem item) {
        Intent myIntent = new Intent(this, SettingsActivity.class);
        startActivityForResult(myIntent, R.integer.Request_code_Settings);
        return true;
    }

    public boolean onTodayButtonPressed(MenuItem item) {
        RapplaPagerFragment fragment;

        switch (getActionBar().getSelectedNavigationIndex()) {
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
        if (requestCode == R.integer.Request_code_Settings) {
			configureNotifier();
            applyURL(RapplaPreferences.getSavedCalendarURL(this));
            if (resultCode == R.integer.Request_code_updateCalendar)
                onRefreshButtonPressed(null);
        }
	}


    public RaplaCalendar getActiveCalendar() {
        return RaplaCalendar.activeCalendar;
    }

    public void setActiveCalendar(RaplaCalendar cal) {
        RaplaCalendar.activeCalendar = cal;

        // redraw grids
        FragmentManager frMan = getFragmentManager();
        FragmentTransaction fragTransaction = frMan.beginTransaction();
        ActionBar aBar = getActionBar();
        Fragment fr;
        switch (aBar.getSelectedNavigationIndex()) {
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
    public boolean onTouchEvent(MotionEvent event) {
        return gestDetector.onTouchEvent(event);
    }

    private void configureNotifier() {
        Intent intent = new Intent(this, RaplaBackgroundService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        boolean doNotify = RapplaPreferences.isOfflineSync(this);
        long interval = RapplaPreferences.getSavedUpdateInterval(this);

        if (!doNotify) {
            alarmManager.cancel(pendingIntent);
        } else {
            //                                                              Start first one second from now
            alarmManager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis() + 1000, interval, pendingIntent);
        }
    }

    public void applyURL(String url)
	{
        url = RapplaUtils.validateIcalUrl(url);

        if (url == null) {
            Toast.makeText(RapplaActivity.this, "Kein Kalender geladen.", Toast.LENGTH_LONG).show();
            return;
		}

        RapplaPreferences.setSavedCalendarURL(RapplaActivity.this, url);
    }
}
