package app.rappla;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RapplaBackgroundUpdateService extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent arg1)
	{
		Log.d("BackgroundUpdateService", "Intent recieved");
		if(!isUpdateAvailable())
			return;
		
	}
	
	public boolean isUpdateAvailable()
	{
		return false;
	}

}
