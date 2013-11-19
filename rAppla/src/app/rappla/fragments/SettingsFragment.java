package app.rappla.fragments;

import android.os.Bundle;
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
	

}