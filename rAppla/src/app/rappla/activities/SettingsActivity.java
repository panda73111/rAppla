package app.rappla.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import app.rappla.R;
import app.rappla.ui.fragments.SettingsFragment;

public class SettingsActivity extends Activity 
{
	ViewGroup myViewGroup = null;
	SettingsFragment settingsFragment = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addSettingsFragment();
		setActionBar();
	}
	public void onStart() {
		super.onStart();
		// GO
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

	public void onBackPressed() {
		Intent intent = this.getIntent();
		setResult(RESULT_OK, intent);
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