package app.rappla.ui.fragments;

import java.lang.reflect.Field;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.rappla.R;
import app.rappla.StaticContext;
import app.rappla.WeekSlidePagerAdaper;

public class WeekPagerFragment extends RapplaFragment
{
	private ViewPager pager;
	private FragmentPagerAdapter pageAdapter;
	private int pagePosition;

	public WeekPagerFragment()
	{
		super();
		setTitle(StaticContext.getContext().getResources().getString(R.string.week));
		setBackground = true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Infinite scrolling realized by setting the starting position
		// to Integer.MAX_VALUE / 2 and therefore being able to scroll
		// in both directions (almost endlessly)
		int halfMax = Integer.MAX_VALUE / 2;
		pager = (ViewPager) inflater.inflate(R.layout.pager_week, container, false);
		pageAdapter = new WeekSlidePagerAdaper(getChildFragmentManager(), halfMax);
		pagePosition = halfMax;
		pager.setAdapter(pageAdapter);
		pager.setCurrentItem(pagePosition);
		return pager;
	}
	
	@Override
	public void onDetach()
	{
		super.onDetach();

		// workaround for bug:
		// https://code.google.com/p/android/issues/detail?id=42601
		// from:
		// https://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed

		try
		{
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e)
		{
			throw new RuntimeException(e);
		} catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}
}
