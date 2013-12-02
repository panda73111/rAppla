package app.rappla.fragments;

import android.app.Fragment;
import app.rappla.R;
import app.rappla.RapplaActivity;
import app.rappla.StaticContext;
import app.rappla.calendar.RaplaCalendar;

public abstract class RapplaFragment extends Fragment
{
	protected String title;
	
	public RapplaFragment()
	{
		setTitle(StaticContext.getContext().getResources().getString(R.string.DEFAULT_FRAGMENT_TITLE));
		setRetainInstance(true);
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
		return ((RapplaActivity)getActivity()).getCalender();
	}
}
