package app.rappla;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.Log;
import app.rappla.ui.fragments.WeekFragment;

public class WeekSlidePagerAdaper extends FragmentStatePagerAdapter
{
	public WeekSlidePagerAdaper(FragmentManager fm)
	{
		super(fm);
	}

	@Override
	public Fragment getItem(int position)
	{
		Log.d("week adapter", "getting position: " + position);
		return new WeekFragment();
	}

	@Override
	public int getCount()
	{
		return 3;
	}

}
