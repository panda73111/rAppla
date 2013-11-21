package app.rappla.fragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import app.rappla.R;
import app.rappla.StaticContext;

public class WeekFragment extends RapplaFragment implements OnClickListener
{
	String gridElementPrefix = "GL#";
	
	
	
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
    	configureWeekGrid();
    	/*SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

    	tv1.setText("Gmail sync is " + sp.getBoolean("gmailSync", false));
    	tv2.setText("Push notifications are " + sp.getBoolean("pushNotifications", false));
    	tv3.setText("Offline sync is " + sp.getBoolean("offlineSync", false));
    	tv4.setText("updateInterval is " + sp.getString("updateInterval", "0 min"));*/
    }
    public void onCalcButtonPressed(View view)
    {
    }
    public void onClick(View view)
    {
    	int itemId = view.getId();
    	String itemTag = (String)view.getTag();
    	if(itemTag!=null && itemTag.startsWith(gridElementPrefix))
    	{
    		Toast.makeText(getActivity(), (String) view.getTag(), Toast.LENGTH_SHORT).show();
    	}
    	switch(itemId)
    	{
//    	case R.id.action_refresh:
//    		onRefreshButtonPressed(view);
//    		break;
    	default:	
    	}
    }
    
    private void configureWeekGrid()
    {
    	final int COL_COUNT = 5;
    	
        GridLayout weekGrid = (GridLayout)getActivity().findViewById(R.id.weekGrid);
        weekGrid.setColumnCount(COL_COUNT);
        weekGrid.setRowCount(40);
        weekGrid.setUseDefaultMargins(false);
        weekGrid.setClipToPadding(true);


        Point screenSize = getScreenSize();
        int buttonWidth = screenSize.x/COL_COUNT;
        for(int i=0; i< 40; i++)
        {
        	Button b = new Button(getActivity());
        	b.setBackgroundResource(R.drawable.unbenannt);
        	b.setLayoutParams(new LinearLayout.LayoutParams(buttonWidth, buttonWidth));
        	weekGrid.addView(b);
        	// X#Y
        	b.setTag(gridElementPrefix + i%COL_COUNT + "#" + i/COL_COUNT);
        	b.setOnClickListener(this);
        }
    }
    public Point getScreenSize()
    {
    	WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
    	Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

}