package app.rappla.ui.fragments;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.rappla.R;
import app.rappla.StaticContext;
import app.rappla.WeekSlidePagerAdaper;

public class WeekPagerFragment extends RapplaFragment
{
	private ViewPager pager;
	private PagerAdapter pageAdapter;

	public WeekPagerFragment()
	{
		setTitle(StaticContext.getContext().getResources().getString(R.string.week));
		setBackground = true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		pager = (ViewPager) inflater.inflate(R.layout.pager_week, container, false);

		pageAdapter = new WeekSlidePagerAdaper(getFragmentManager());
		pager.setAdapter(pageAdapter);
		
		if (LOG_EVENTS)
			Log.d(title, "onCreateView");

		return pager;
	}
}
