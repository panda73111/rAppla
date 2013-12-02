package app.rappla;

import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class RapplaGestureListener extends SimpleOnGestureListener
{
	public RapplaGestureListener()
	{
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
		
		return super.onFling(e1, e2, velocityX, velocityY);
	}
	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		return super.onScroll(e1, e2, distanceX, distanceY);
	}
}
