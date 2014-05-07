package app.rappla;

import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import app.rappla.activities.RapplaActivity;

public class RapplaGestureListener extends SimpleOnGestureListener
{
	RapplaActivity rapplaAct;
	
	public RapplaGestureListener()
	{
		rapplaAct = RapplaActivity.getInstance();
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
		if (Math.abs(velocityX) < Math.abs(velocityY))
		{
			Log.d("gest", "fling vertical");
			return false;
		}
		
		if (velocityX < 0)
		{
			Log.d("gest", "fling right");
		}
		else if (velocityX > 0)
		{
			Log.d("gest", "fling left");
		}
		return true;
	}
	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		return super.onScroll(e1, e2, distanceX, distanceY);
	}
}
