package app.rappla;

import android.app.Application;
import android.content.Context;

public class StaticContext extends Application
{
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
    
//	public static Point getScreenSize()
//	{
//		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//		Display display = wm.getDefaultDisplay();
//		Point size = new Point();
//		display.getSize(size);
//		return size;
//	}
}