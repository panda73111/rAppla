package app.rappla.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import app.rappla.R;
import app.rappla.RapplaTabListener;
import app.rappla.calendar.RaplaEvent;
import app.rappla.ui.fragments.AlarmFragment;
import app.rappla.ui.fragments.NotesFragment;
import app.rappla.ui.fragments.RapplaFragment;

public class EventActivity extends Activity
{
	RaplaEvent myEvent;
	RapplaFragment[] fragments = new RapplaFragment[] { new NotesFragment(), new AlarmFragment()};
	private Tab[] tabs = new Tab[fragments.length];

	private static EventActivity instance;
	public final static String eventIDKey = "eventID";
	


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		instance = this;
		
		String eventID = getIntent().getExtras().getString(eventIDKey);
		
		myEvent = RapplaActivity.getInstance().getCalender().getElementByUID(eventID);
		
		setContentView(R.layout.layout_rappla);
		configureActionBar(savedInstanceState);
	}
	
	private void configureActionBar(Bundle savedInstanceState)
	{
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setTitle(myEvent.getTitle());
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		
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
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		int i = getActionBar().getSelectedNavigationIndex();
		outState.putInt("selectedTab", i);
	}
	
	public RaplaEvent getEvent()
	{
		return myEvent;
	}
	
	public static EventActivity getInstance()
	{
		return instance;
	}
	
}