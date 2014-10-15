package app.rappla.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;

import app.rappla.R;
import app.rappla.ui.fragments.SettingsFragment;

public class SettingsActivity extends Activity
{
	public static boolean calendarWasChanged = false;
    ViewGroup myViewGroup = null;
    SettingsFragment settingsFragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addSettingsFragment();
		setActionBar();
	}

	public void onStart()
	{
		super.onStart();
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			onBackPressed();
			break;
		default:
		}
		return true;
	}

	public void onBackPressed()
	{
		Intent intent = this.getIntent();
        if (calendarWasChanged) {
            calendarWasChanged = false;
            setResult(R.integer.Request_code_updateCalendar, intent);
        }
        finish();
    }

	public void addSettingsFragment()
	{
		settingsFragment = new SettingsFragment();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(android.R.id.content, settingsFragment);
		fragmentTransaction.commit();
	}

	public void setActionBar()
	{
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(getResources().getString(R.string.settings));
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
}