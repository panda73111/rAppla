package app.rappla.test;

import android.content.Intent;
import android.os.Bundle;
import app.rappla.activities.RapplaActivity;
import app.rappla.calendar.RaplaCalendar;
import app.rappla.inet.DownloadRaplaTask;
import app.rappla.ui.fragments.RapplaFragment;

public class basicTests extends  android.test.ActivityUnitTestCase<RapplaActivity> {

	private RapplaActivity activity;

	public basicTests() {
		super(RapplaActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
				RapplaActivity.class);
		Bundle noDlg = new Bundle();
		noDlg.putBoolean("isTest", true);
		startActivity(intent, noDlg, null);
		activity = getActivity();
	}
	public void testTrue()
	{
		assertTrue(true);
	}
	public void testCalendarAvailable()
	{
		RaplaCalendar c = activity.getCalender();
		assertTrue(c!=null);
	}
	public void testFragmentsCreated()
	{
		assertTrue(activity.getFragmentCount()==3);
	}
	public void testRaplaDownloadTask()
	{
		DownloadRaplaTask task =  new DownloadRaplaTask(activity, null);
		task.execute(RapplaActivity.ICAL_URL);
	}
	public void testTabsAreCreated()
	{		
		RapplaFragment fragment = (RapplaFragment) activity.getFragment(0);
		assertTrue(fragment.getTitle().equals("Woche"));
		fragment = (RapplaFragment) activity.getFragment(1);
		assertTrue(fragment.getTitle().equals("Tag"));
		fragment = (RapplaFragment) activity.getFragment(2);
		assertTrue(fragment.getTitle().equals("Bahn"));
	}
}