package app.rappla.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import app.rappla.R;

public class AlarmPopupDialog extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_alarm_popup);
		this.setFinishOnTouchOutside(false);
		
		vibrateOnStartup();
	}

	private void vibrateOnStartup()
	{
		Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(new long[]{0,150,150,250, 100, 100}, -1);
	}
	
	
}