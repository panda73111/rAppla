package app.rappla;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Totally Created by lorenzo on 14.10.14.
 */
public class RapplaPreferences {
    public static final String PREF_KEY_lastCalendarHash = "lastCalendarHash";
    public static final String PREF_KEY_wifiOnlySync = "onlyWifiSync";
    public static final String PREF_KEY_ICAL_URL = "ICAL_URL";
    public static final String PREF_KEY_offlineSync = "offlineSync";
    public static final String PREF_KEY_syncInterval = "syncInterval";
    public static final String PREF_KEY_selectedTab = "selectedTab";

    private static final long DEFAULT_updateInterval = 86400;

    public static int getSavedCalendarHash(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(PREF_KEY_lastCalendarHash, 0);
    }

    public static void setSavedCalendarHash(Context context, int calendarHash) {
        SharedPreferences.Editor editor = getPreferenceEditor(context);
        editor.putInt(PREF_KEY_lastCalendarHash, calendarHash);
        editor.commit();

    }

    public static boolean isWifiOnlySync(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(PREF_KEY_wifiOnlySync, false);
    }

    public static void setWifiOnlySync(Context context, boolean wifiOnly) {
        SharedPreferences.Editor editor = getPreferenceEditor(context);
        editor.putBoolean(PREF_KEY_wifiOnlySync, wifiOnly);
        editor.commit();
    }


    public static String getSavedCalendarURL(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PREF_KEY_ICAL_URL, null);
    }

    public static void setSavedCalendarURL(Context context, String url) {
        SharedPreferences.Editor editor = getPreferenceEditor(context);
        editor.putString(PREF_KEY_ICAL_URL, url);
        editor.commit();
    }


    public static boolean isOfflineSync(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(PREF_KEY_offlineSync, false);
    }

    public static void setOfflineSync(Context context, boolean offlineSync) {
        SharedPreferences.Editor editor = getPreferenceEditor(context);
        editor.putBoolean(PREF_KEY_offlineSync, offlineSync);
        editor.commit();
    }

    public static long getSavedUpdateInterval(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(PREF_KEY_syncInterval, DEFAULT_updateInterval);
    }

    public static void setSavedUpdateInterval(Context context, long interval) {
        SharedPreferences.Editor editor = getPreferenceEditor(context);
        editor.putLong(PREF_KEY_syncInterval, interval);
        editor.commit();
    }

    private static SharedPreferences getPreferences(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c);
    }

    private static SharedPreferences.Editor getPreferenceEditor(Context c) {
        return getPreferences(c).edit();
    }
}
