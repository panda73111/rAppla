package app.rappla;

import java.util.Calendar;
import java.util.HashMap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import app.rappla.ui.fragments.WeekFragment;

public class WeekSlidePagerAdaper extends FragmentPagerAdapter
{
	SparseArray<Fragment> fragments;

	public WeekSlidePagerAdaper(FragmentManager fm)
	{
		super(fm);

		fragments = new SparseArray<Fragment>();
	}

	@Override
	public Fragment getItem(int position)
	{
		Log.d("week adapter", "getting position: " + position);
		Fragment fr = fragments.get(position);
		if (fr != null)
			return fr;
		else
		{
			fr = new WeekFragment();
			Calendar date = Calendar.getInstance();
			date.set(Calendar.WEEK_OF_YEAR, date.get(Calendar.WEEK_OF_YEAR) + position);
			((WeekFragment) fr).setDate(date);
			fragments.put(position, fr);
			return fr;
		}
	}

	@Override
	public int getCount()
	{
		return Integer.MAX_VALUE;
	}
}
