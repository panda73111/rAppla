package app.rappla;

import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar;
import android.util.Log;

public class RapplaTabListener implements ActionBar.TabListener
{

	Fragment fragment;

	public RapplaTabListener(Fragment fragment)
	{
		super();
		this.fragment = fragment;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		ft.add(R.id.layout_rappla, fragment, null);
		Log.d("TabListener", "Tab changed to: " + tab.getText());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft)
	{
		ft.remove(fragment);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft)
	{
	}
}