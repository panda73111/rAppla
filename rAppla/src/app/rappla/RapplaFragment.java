package app.rappla;

import android.app.Fragment;

public class RapplaFragment extends Fragment
{
	public void onStart()
	{
		super.onStart();
		setTitle(getResources().getString(R.string.DEFAULT_FRAGMENT_TITLE));
	}
	private String title = "No title";
	public String getTitle()
	{
		return title;
	}
	protected void setTitle(String newTitle)
	{
		title = newTitle;
	}
}
