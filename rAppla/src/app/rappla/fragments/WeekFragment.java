package app.rappla.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import app.rappla.R;
import app.rappla.StaticContext;

public class WeekFragment extends RapplaFragment implements OnClickListener{

	public WeekFragment()
	{
		setTitle(StaticContext.getContext().getResources().getString(R.string.week));
	}


	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weeklayout, container, false);
		return rootView;
	}
    public void onStart()
    {
    	super.onStart();
    	TextView tv1 = (TextView)getActivity().findViewById(R.id.TextView01);
    	TextView tv2 = (TextView)getActivity().findViewById(R.id.TextView02);
    	TextView tv3 = (TextView)getActivity().findViewById(R.id.TextView03);
    	TextView tv4 = (TextView)getActivity().findViewById(R.id.TextView04);
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

    	tv1.setText("Gmail sync is " + sp.getBoolean("gmailSync", false));
    	tv2.setText("Push notifications are " + sp.getBoolean("pushNotifications", false));
    	tv3.setText("Offline sync is " + sp.getBoolean("offlineSync", false));
    	tv4.setText("updateInterval is " + sp.getString("updateInterval", "0 min"));
    }
    public void onCalcButtonPressed(View view)
    {
    }
    public void onClick(View view)
    {
    	switch(view.getId())
    	{
//    	case R.id.action_refresh:
//    		onRefreshButtonPressed(view);
//    		break;
    	default:	
    	}
    }

}