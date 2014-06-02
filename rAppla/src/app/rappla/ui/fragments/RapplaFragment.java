package app.rappla.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import app.rappla.R;
import app.rappla.StaticContext;
import app.rappla.activities.RapplaActivity;
import app.rappla.calendar.RaplaCalendar;

public abstract class RapplaFragment extends Fragment
{
	protected static final boolean LOG_EVENTS = true;
	protected String title;
	protected boolean setBackground;
	private int fragmentWidth = 0;
	private int fragmentHeight = 0;
	
	
	
	public RapplaFragment()
	{
		super();
		setTitle(StaticContext.getContext().getResources().getString(R.string.DEFAULT_FRAGMENT_TITLE));
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		if (LOG_EVENTS)
			Log.d(title, "onAttach");
	}

	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
	          @Override
	          public void onGlobalLayout() {
	        	  if(getView() != null)
	        	  {
		              fragmentWidth = getView().getWidth();
		              fragmentHeight = getView().getHeight();
		              if(fragmentWidth > 0)
		              {
		            	  getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
		              }  
	        	  }
	              doGlobalLayout();
	          }
	        });
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (LOG_EVENTS)
			Log.d(title, "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		if (LOG_EVENTS)
			Log.d(title, "onCreateView");

		return null;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if (LOG_EVENTS)
			Log.d(title, "onDestroy");
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
		if (LOG_EVENTS)
			Log.d(title, "onDetach");
	}

	@Override
	public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState)
	{
		super.onInflate(activity, attrs, savedInstanceState);
		if (LOG_EVENTS)
			Log.d(title, "onInflate");
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		if (LOG_EVENTS)
			Log.d(title, "onDestroyView");
	}

	@Override
	public void onPause()
	{
		super.onPause();
		if (LOG_EVENTS)
			Log.d(title, "onPause");
	}

	@Override
	public void onResume()
	{
		super.onResume();

		if (LOG_EVENTS)
			Log.d(title, "onResume");

		if (setBackground)
			setBackground(getResources().getConfiguration().orientation);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		if (LOG_EVENTS)
			Log.d(title, "onStart");
	}

	@Override
	public void onStop()
	{
		super.onStop();
		if (LOG_EVENTS)
			Log.d(title, "onStop");
	}

	public String getTitle()
	{
		return title;
	}

	protected void setTitle(String newTitle)
	{
		title = newTitle;
	}

	protected RaplaCalendar getCalender()
	{
		return RapplaActivity.getInstance().getCalender();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);

		if (LOG_EVENTS)
			Log.d(title, "onConfigurationChanged");

		if (setBackground)
			setBackground(getResources().getConfiguration().orientation);
	}

	private void setBackground(int orientation)
	{
		if (orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			getView().setBackgroundResource(R.drawable.background_potrait);
		} else
		{
			getView().setBackgroundResource(R.drawable.background_landscape);
		}
	}
	
	public void doGlobalLayout()
	{
		
	}
	
	public int getWidth()
	{
		return fragmentWidth;
	}
	public int getHeight()
	{
		return fragmentHeight;
	}
}
