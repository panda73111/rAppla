package app.rappla.ui.fragments;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;

import app.rappla.R;
import app.rappla.RapplaPreferences;
import app.rappla.activities.SettingsActivity;

public class SettingsFragment extends PreferenceFragment
{
    private static final String updateIntervalListKey = "updateIntervalString";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.fragment_settings);

	}

	public void onStart()
	{
		super.onStart();
        ListPreference lp = (ListPreference) findPreference(updateIntervalListKey);

        lp.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue)
			{
                String newValueString = (String) newValue;

				ListPreference lp = (ListPreference)preference;
                int index = lp.findIndexOfValue(newValueString);
                updateLP(index);
                RapplaPreferences.setSavedUpdateInterval(getActivity(), Long.valueOf(newValueString));
                return true;
            }
		});
		updateLP();
		EditTextPreference ep = (EditTextPreference) findPreference("ICAL_URL");
		ep.setOnPreferenceChangeListener(new OnPreferenceChangeListener()
		{	
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue)
			{
				preference.setSummary((String)newValue);
				SettingsActivity.calendarWasChanged = true;
				return true;
			}
		});

        String url = RapplaPreferences.getSavedCalendarURL(getActivity());
        if(url!=null)
			ep.setSummary(url);
	}

	private void updateLP()
	{
        ListPreference lp = (ListPreference) findPreference(updateIntervalListKey);
        updateLP(lp.findIndexOfValue(lp.getValue()));
    }
	private void updateLP(int index)
	{
        ListPreference lp = (ListPreference) findPreference(updateIntervalListKey);

		if (index == -1)
		{
			lp.setValueIndex(0);
			index = 0;
		}
		String summary = getResources().getString(R.string.updateInterval_summary).replace("_TIME_", lp.getEntries()[index]);

		lp.setSummary(summary);
	}

}