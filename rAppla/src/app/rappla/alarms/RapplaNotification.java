package app.rappla.alarms;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import app.rappla.R;

/**
 * Amazingly Created by lorenzo on 14.10.14.
 */
public class RapplaNotification {
    private static final int[] ledSettings = new int[]{0xFFff4500, 100, 100};
    private static final long[] vibrationPattern = new long[]{0, 150, 150, 250, 100, 100};
    private static final int defaultIcon = R.drawable.ic_launcher;

    private static Uri getDefaultNotificationSound() {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }


    /*
       BigText and note are optional
     */
    public static void showNotification(Context context, String title, String note, String bigText, PendingIntent clickAction, int notificationID) {
        Uri notificationSound = getDefaultNotificationSound();

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(defaultIcon)
                .setContentTitle(title)
                .setLights(ledSettings[0], ledSettings[1], ledSettings[2])
                .setSound(notificationSound).setVibrate(vibrationPattern)
                .setContentIntent(clickAction);

        if (bigText != null)
            mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(bigText));
        if (note != null)
            mBuilder.setContentText(note);


        Notification notification = mBuilder.build();

        nm.notify(notificationID, notification);
        Log.d("Notification", title + ": " + note);
    }


}
