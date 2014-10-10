package app.rappla.alarms;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import app.rappla.RapplaUtils;


public class Alarm implements Serializable {
    public static final String serializedAlarmsFileName = "RapplaAlarms.ser";
    public static HashMap<String, ArrayList<Alarm>> allAlarms = null;
    Calendar alarmDate;
    boolean isActive = true;
    transient boolean wasModified = false;
    String eventID;


    public Alarm(Calendar a, String eventID) {
        this.alarmDate = Calendar.getInstance();
        this.isActive = true;
        this.eventID = eventID;
        this.wasModified = false;

        if (a != null) {
            alarmDate.setTime(a.getTime());
        }
    }

    public static boolean alarmsAreLoaded() {
        return allAlarms != null;
    }

    public static void saveAlarmFile(Context context) {
        try {
            RapplaUtils.writeSerializedObject(context, serializedAlarmsFileName, allAlarms);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void loadAlarmFile(Context context) {
        try {
            allAlarms = RapplaUtils.readSerializedObject(context, serializedAlarmsFileName);
        } catch (FileNotFoundException e) {
        } catch (ClassNotFoundException | IOException i) {
            i.printStackTrace();
        } finally {
            if (allAlarms == null)
                allAlarms = new HashMap<>();
        }
    }

    public static ArrayList<Alarm> getAlarmsAtEvent(Context context, String eventID) {
        if (!alarmsAreLoaded())
            loadAlarmFile(context);
        ArrayList<Alarm> alarms = allAlarms.get(eventID);
        if (alarms == null)
            alarms = new ArrayList<>();
        return alarms;
    }

    public static void setAlarmsAtEvent(Context context, String eventID, ArrayList<Alarm> alarms) {
        if (!alarmsAreLoaded())
            loadAlarmFile(context);
        allAlarms.put(eventID, alarms);
    }

    public Calendar getDate() {
        return alarmDate;
    }

    private void startAlarm(Context context) {
        AlarmFactory.startAlarm(eventID, alarmDate, context);
    }

    private void cancelAlarm(Context context) {
        AlarmFactory.cancelAlarm(eventID, alarmDate, context);
    }

    public void applyState(Context c) {
        if (isActive)
            startAlarm(c);
        else
            cancelAlarm(c);
    }

    public void setActive(boolean isActive) {
        if (this.isActive == isActive)
            return;

        this.isActive = isActive;
        this.wasModified = true;
    }
}