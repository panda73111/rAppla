package app.rappla.ui.fragments;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import app.rappla.R;

public class SettingsFragment extends PreferenceFragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.fragment_settings);

	}

	public void onStart()
	{
		super.onStart();
		ListPreference lp = (ListPreference) findPreference("updateInterval");
		
		lp.setOnPreferenceChangeListener(new OnPreferenceChangeListener()
		{	
			public boolean onPreferenceChange(Preference preference, Object newValue)
			{
				ListPreference lp = (ListPreference)preference;
				int index = lp.findIndexOfValue((String)newValue);
				updateLP(index);
				return true;
			}
		});
		updateLP();
	}

	private void updateLP()
	{
		ListPreference lp = (ListPreference) findPreference("updateInterval");
		updateLP(lp.findIndexOfValue(lp.getValue()));
	}
	private void updateLP(int index)
	{
		ListPreference lp = (ListPreference) findPreference("updateInterval");

		if (index == -1)
			lp.setValueIndex(0);

		String summary = getResources().getString(R.string.updateInterval_summary).replace("_TIME_", lp.getEntries()[index]);

		lp.setSummary(summary);
	}

}