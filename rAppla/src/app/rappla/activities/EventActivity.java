package app.rappla.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MenuItem;

import app.rappla.R;
import app.rappla.RapplaTabListener;
import app.rappla.calendar.RaplaCalendar;
import app.rappla.calendar.RaplaEvent;
import app.rappla.ui.fragments.AlarmFragment;
import app.rappla.ui.fragments.EventInfoFragment;
import app.rappla.ui.fragments.NotesFragment;
import app.rappla.ui.fragments.RapplaFragment;

public class EventActivity extends Activity {
    public final static String eventIDKey = "eventID";
    public final static String isAlarmKey = "isAlarm";
    public final static String selectedTabKey = "selectedTab";


    private static EventActivity instance;
    RaplaEvent myEvent;
    RapplaFragment[] fragments = new RapplaFragment[3];

    private Tab[] tabs = new Tab[fragments.length];

    public static EventActivity getInstance() {
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("EventActivity", "onCreate");

        createInstance();

        Bundle extras = getIntent().getExtras();
        String eventID = extras.getString(eventIDKey);
        loadEvent(eventID);

        setContentView(R.layout.layout_rappla);

        configureFragments(savedInstanceState);

        // if this Activity is started as a result of an Alarm going off
        if (extras.getBoolean(isAlarmKey, false)) {
            vibrate();
        }
    }

    private void configureFragments(Bundle savedInstanceState) {
        fragments = new RapplaFragment[]{new EventInfoFragment(), new NotesFragment(), new AlarmFragment()};
        configureActionBar(savedInstanceState);
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(new long[]{0, 150, 150, 250, 100, 100}, -1);
        getActionBar().setSelectedNavigationItem(1);
    }

    private void createInstance() {
        if (instance != null) {
            instance.finish();
            instance = null;
        }
        instance = this;
    }

    private void loadEvent(String eventID) {
        RapplaActivity rapplaActivity = RapplaActivity.getInstance();
        RaplaCalendar calendar;

        if (rapplaActivity != null) {
            calendar = rapplaActivity.getActiveCalendar();
        } else {
            calendar = RaplaCalendar.load();
        }
        myEvent = calendar.getElementByUniqueID(eventID);
    }

    public void onDestroy() {
        super.onDestroy();
        instance = null;
        Log.d("EventActivity", "onDestroy");
    }

    private void configureActionBar(Bundle savedInstanceState) {
        ActionBar actionBar = getActionBar();
        if (actionBar == null)
            return;

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle(myEvent.getTitle());
        actionBar.setDisplayHomeAsUpEnabled(true);

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
            selectedIndex = savedInstanceState.getInt(selectedTabKey);
        actionBar.selectTab(tabs[selectedIndex]);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ActionBar actionBar = getActionBar();
        int i = actionBar.getSelectedNavigationIndex();
        actionBar.removeAllTabs();
        outState.putInt(selectedTabKey, i);
        outState.putString(eventIDKey, myEvent.getUniqueEventID());

        super.onSaveInstanceState(outState);
    }

    public RaplaEvent getEvent() {
        return myEvent;
    }
}
