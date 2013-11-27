package app.rappla.fragments;

import RapplaGrid.RapplaGrid;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import app.rappla.R;
import app.rappla.StaticContext;

public class WeekFragment extends RapplaFragment implements OnClickListener
{
	String gridElementPrefix = "GL#";
	String weekGridPrefix = "WG#";
	RapplaGrid weekGrid;
	
	public WeekFragment()
	{
		setTitle(StaticContext.getContext().getResources().getString(R.string.week));
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.weeklayout, container, false);
		return rootView;
	}

	public void onStart()
	{
		super.onStart();
		configureWeekGrid();
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

	public void onCalcButtonPressed(View view)
	{
	}

	public void onClick(View view)
	{
		int itemId = view.getId();
		String itemTag = (String) view.getTag();
		if (itemTag != null && itemTag.startsWith(gridElementPrefix))
		{
			Toast.makeText(getActivity(), (String) view.getTag(), Toast.LENGTH_SHORT).show();
		}
		switch (itemId)
		{
		// case R.id.action_refresh:
		// onRefreshButtonPressed(view);
		// break;
		default:
		}
	}

	private void configureWeekGrid()
	{
		weekGrid = (RapplaGrid) getActivity().findViewById(R.id.weekGrid);
	    

//		Button b = new Button(getActivity());
//		b.setBackgroundResource(R.drawable.event);
//		weekGrid.addElementAt(b, 2, 10, 20);

	}


}