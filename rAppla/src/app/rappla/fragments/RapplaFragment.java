package app.rappla.fragments;

import android.app.Fragment;
import app.rappla.R;
import app.rappla.StaticContext;

public abstract class RapplaFragment extends Fragment
{
	protected String title;
	
	public RapplaFragment()
	{
		setTitle(StaticContext.getContext().getResources().getString(R.string.DEFAULT_FRAGMENT_TITLE));
	}
	
	public String getTitle()
	{
		return title;
	}
	protected void setTitle(String newTitle)
	{
		title = newTitle;
	}
}
