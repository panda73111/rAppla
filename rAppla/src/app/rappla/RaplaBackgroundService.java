package app.rappla;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

import app.rappla.activities.RapplaActivity;
import app.rappla.alarms.RapplaNotification;
import app.rappla.calendar.EventModification;
import app.rappla.calendar.RaplaCalendar;
import app.rappla.inet.DownloadRaplaTask;

public class RaplaBackgroundService extends Service {

    private static final int numberOfChangesShows = 10;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //Log.v("RaplaBackgroundService", System.currentTimeMillis() + ": Service erstellt.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Log.v("RaplaBackgroundService", System.currentTimeMillis() + ": Service gestartet.");

        updateData();

        // Nachdem unsere Methode abgearbeitet wurde, soll sich der Service selbst stoppen.
        stopSelf();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //Log.v("RaplaBackgroundService", System.currentTimeMillis() + ": Service zerstoert.");
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
                if (result == null) {
                    Log.d("BackgroundUpdateService", "Kalender konte nicht heruntergeladen werden.");
                    return;
                }
                Log.d("BackgroundUpdateService", "new CalendarHash: " + result.hashCode());

                if (notificationHash != result.hashCode()) {
                    Log.d("BackgroundUpdateService", "new CalendarHash is different!");

                    ArrayList<EventModification> modifications = findModifications(result);

                    result.save(context);
                    Log.d("BackgroundUpdateService", "Showing Notification");
                    showNotification(StaticContext.getContext(), modifications);
                } else {
                    Log.d("BackgroundUpdateService", "new CalendarHash equals oldCalendarHash");
                }
            }
        }, false);

        Log.d("BackgroundUpdateService", "Downloading Rapla");
        downloadTask.execute(RapplaPreferences.getSavedCalendarURL(this));

    }

    private void showNotification(Context context, ArrayList<EventModification> modifications) {
        String note = null;
        String bigText = "";
        for (int i = 0; i < numberOfChangesShows && i < modifications.size(); i++) {
            bigText += modifications.get(i) + "\n";
        }
        if (bigText.equals(""))
            bigText = null;
        if (modifications.size() > 0)
            note = modifications.size() + " Termine wurden verändert.";
        else
            note = "Der Rapla wurde verändert.";

        Intent resultIntent = new Intent(context, RapplaActivity.class);
        resultIntent.setAction("NotificationButton");
        PendingIntent eventIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        RapplaNotification.showNotification(context, "Der Rapla hat sich verändert!", note, bigText, eventIntent, R.integer.Notification_ID_RapplaUpdate);
    }

    private ArrayList<EventModification> findModifications(RaplaCalendar newCalendar) {
        if (RaplaCalendar.activeCalendar == null)
            RaplaCalendar.activeCalendar = RaplaCalendar.load();

        return RaplaCalendar.activeCalendar.compare(newCalendar);
    }


}