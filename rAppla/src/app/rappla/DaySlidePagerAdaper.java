package app.rappla;

import java.util.Calendar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;
import app.rappla.ui.fragments.DayFragment;

public class DaySlidePagerAdaper extends FragmentPagerAdapter
{
	int posOffset;

	public DaySlidePagerAdaper(FragmentManager fm, int positionOffset)
	{
		super(fm);
		this.posOffset = positionOffset;
	}

	@Override
	public Fragment getItem(int position)
	{
		position -= posOffset;
		Log.d("day adapter", "getting position: " + position);
		Fragment fr = new DayFragment();
		Calendar date = Calendar.getInstance();
		int dayOfWeek;
		do
		{
			date.set(Calendar.DAY_OF_YEAR, date.get(Calendar.DAY_OF_YEAR) + position);
			dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		} while (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
		((DayFragment) fr).setDate(date);
		return fr;
	}

	@Override
	public int getCount()
	{
		return Integer.MAX_VALUE;
	}
}
