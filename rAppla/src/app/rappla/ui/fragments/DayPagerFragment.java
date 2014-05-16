package app.rappla.ui.fragments;

import java.lang.reflect.Field;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.rappla.DaySlidePagerAdaper;
import app.rappla.R;
import app.rappla.StaticContext;

public class DayPagerFragment extends RapplaFragment implements RapplaPagerFragment, OnPageChangeListener
{
	private ViewPager pager;
	private FragmentPagerAdapter pageAdapter;
	private int pagePosition;
	private static final int PAGECOUNT		= Integer.MAX_VALUE ; 			
	private static final int TODAY_PAGE  	= PAGECOUNT/2 ;

	public DayPagerFragment()
	{
		super();
		setTitle(StaticContext.getContext().getResources().getString(R.string.day));
		setBackground = true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Infinite scrolling realized by setting the starting position
		// to Integer.MAX_VALUE / 2 and therefore being able to scroll
		// in both directions (almost endlessly)

		pager = (ViewPager) inflater.inflate(R.layout.pager_day, container, false);
		pageAdapter = new DaySlidePagerAdaper(getChildFragmentManager(), TODAY_PAGE);
		pagePosition = TODAY_PAGE;
		pager.setAdapter(pageAdapter);
		pager.setCurrentItem(pagePosition);
		pager.setOnPageChangeListener(this);
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
	
	public void goToToday()
	{
		while(pagePosition > TODAY_PAGE)
		{
			pagePosition--;
			pager.setCurrentItem(pagePosition);
		}
		while(pagePosition < TODAY_PAGE)
		{
			pagePosition++;
			pager.setCurrentItem(pagePosition);
		}		
	}

	@Override
	public void onPageScrollStateChanged(int arg0){}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2){}
	@Override
	public void onPageSelected(int arg0)
	{
		pagePosition = arg0;
	}
}
