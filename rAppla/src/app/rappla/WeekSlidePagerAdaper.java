package app.rappla;

import java.util.Calendar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;
import app.rappla.ui.fragments.WeekFragment;

public class WeekSlidePagerAdaper extends FragmentPagerAdapter
{
	int posOffset;

	public WeekSlidePagerAdaper(FragmentManager fm, int positionOffset)
	{
		super(fm);
		this.posOffset = positionOffset;
	}

	@Override
	public Fragment getItem(int position)
	{
		position -= posOffset;
		Log.d("week adapter", "getting position: " + position);
		Fragment fr = new WeekFragment();
		Calendar date = Calendar.getInstance();
		date.set(Calendar.WEEK_OF_YEAR, date.get(Calendar.WEEK_OF_YEAR) + position);
		((WeekFragment) fr).setDate(date);
		return fr;
	}

	@Override
	public int getCount()
	{
		return Integer.MAX_VALUE;
	}
}
