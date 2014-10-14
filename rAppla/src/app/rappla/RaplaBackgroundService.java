package app.rappla;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import app.rappla.activities.RapplaActivity;
import app.rappla.alarms.RapplaNotification;
import app.rappla.calendar.RaplaCalendar;
import app.rappla.inet.DownloadRaplaTask;

public class RaplaBackgroundService extends Service {

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.v("RaplaBackgroundService", System.currentTimeMillis() + ": Service erstellt.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("RaplaBackgroundService", System.currentTimeMillis() + ": Service gestartet.");

        updateData();

        // Nachdem unsere Methode abgearbeitet wurde, soll sich der Service selbst stoppen.
        stopSelf();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.v("RaplaBackgroundService", System.currentTimeMillis() + ": Service zerstoert.");
    }

    private void updateData() {
        if (RapplaPreferences.isWifiOnlySync(this)) {
            ConnectivityManager connManager = (ConnectivityManager) StaticContext.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!mWifi.isConnected()) {
                Log.d("BackgroundUpdateService", "Not wifi, aborting");
                return;
            }
        }

        final int notificationHash = RapplaPreferences.getSavedCalendarHash(this);

        final Context context = getApplicationContext();

        Log.d("BackgroundUpdateService", "Last Calendar Hash: " + notificationHash);

        DownloadRaplaTask downloadTask = new DownloadRaplaTask(this, new OnTaskCompleted<RaplaCalendar>() {
            public void onTaskCompleted(RaplaCalendar result) {
                Log.d("BackgroundUpdateService", "new CalendarHash: " + result.hashCode());

                if (notificationHash != result.hashCode()) {
                    Log.d("BackgroundUpdateService", "new CalendarHash is different!");


                    result.save(context);
                    Log.d("BackgroundUpdateService", "Showing Notification");
                    showNotification(StaticContext.getContext());
                } else {
                    Log.d("BackgroundUpdateService", "new CalendarHash equals oldCalendarHash");
                }
            }
        }, false);

        Log.d("BackgroundUpdateService", "Downloading Rapla");
        downloadTask.execute(RapplaPreferences.getSavedCalendarURL(this));

    }

    private void showNotification(Context context) {
        Intent resultIntent = new Intent(context, RapplaActivity.class);
        resultIntent.setAction("NotificationButton");
        PendingIntent eventIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RapplaNotification.showNotification(context, "Der Rapla hat sich verändert!", "Öffnen Sie rAppla um die Änderungen einzusehen.", eventIntent, R.integer.Notification_ID_RapplaUpdate);
    }


}