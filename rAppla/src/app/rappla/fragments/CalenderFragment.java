package app.rappla.fragments;

import RapplaGrid.RapplaGrid;

public class CalenderFragment extends RapplaFragment
{
	String gridElementPrefix = "GL#";
	String weekGridPrefix = "WG#";
	RapplaGrid calenderGrid;
	
	int minimumTimeInterval = 15; // In minutes

	
	public void onStart()
	{
		super.onStart();
		configureGrid();
		/*
		 * SharedPreferences sp =
		 * PreferenceManager.getDefaultSharedPreferences(getActivity());
		 * 
		 * tv1.setText("Gmail sync is " + sp.getBoolean("gmailSync", false));
		 * tv2.setText("Push notifications are " +
		 * sp.getBoolean("pushNotifications", false));
		 * tv3.setText("Offline sync is " + sp.getBoolean("offlineSync",
		 * false)); tv4.setText("updateInterval is " +
		 * sp.getString("updateInterval", "0 min"));
		 */
	}
	protected void configureGrid()
	{
		// Set up your grid here
//		calenderGrid = (RapplaGrid) getActivity().findViewById(R.id.weekGrid);
	}
}
