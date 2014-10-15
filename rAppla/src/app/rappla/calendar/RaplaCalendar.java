package app.rappla.calendar;

import android.content.Context;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import app.rappla.RapplaPreferences;
import app.rappla.RapplaUtils;
import app.rappla.StaticContext;

@SuppressWarnings("serial")
public class RaplaCalendar implements Serializable {
    private static final String CALENDAR_DB_FILE = "rapla.db";
    public static RaplaCalendar activeCalendar = null;

    private Hashtable<Integer, Set<RaplaEvent>> eventCal;


    public RaplaCalendar() {
        eventCal = new Hashtable<>();
    }

    public static RaplaCalendar load() {
        android.util.Log.d("RaplaCalendar", "load");

        try {
            return RapplaUtils.readSerializedObject(StaticContext.getContext(), CALENDAR_DB_FILE);
        } catch (IOException ex) {
            android.util.Log.d("RaplaCalendar", "error loading calendar: " + ex);
            return null;
        } catch (ClassNotFoundException ex) {
            android.util.Log.d("cal", "error loading calendar: " + ex);
            return null;
        }
    }

    public void parse(Reader reader) throws CalendarFormatException, IOException {
        CalendarParser calParser = new ICalendarParser();
        calParser.parse(reader);
        List<ParseError> errors = calParser.getAllErrors();
        if (errors.size() > 0)
            throw new CalendarFormatException(errors.size() + " iCal parsing errors");

        for (Event e : calParser.dataStore.getAllEvents()) {
            RaplaEvent re = RaplaEvent.FromEvent(e);
            addEvent(re);

            Rrule rule = e.getRrule();
            Exdate exdate = e.getExdate();

            if (rule != null) {
                // is recurring event
                List<Date> recurrences = rule.generateRecurrances(e.getStartDate(), null);
                List<Date> exDates = null;
                if (exdate != null)
                    exDates = exdate.getDates();


                for (Date d : recurrences) {
                    if (exDates == null || !exDates.contains(d))
                        addEvent(RaplaEvent.FromRecurringRaplaEvent(re, d.toCalendar()));
                }
            }

        }
    }

    public RaplaEvent getElementByUniqueID(String uid) {
        for (Set<RaplaEvent> entry : eventCal.values()) {
            for (RaplaEvent event : entry) {
                if (event.getUniqueEventID().equals(uid))
                    return event;
            }
        }
        return null;
    }

    public Set<RaplaEvent> getEventsAtDate(Calendar date) {
        return eventCal.get(getDateHash(date));
    }

    public Set<RaplaEvent> getEventsAtDate(int day, int month, int year) {
        // the month in the Calendar class is zero based
        return eventCal.get(getDateHash(day, month - 1, year));
    }

    public RaplaEvent getNextEvent() {
        return getNextEvent(Calendar.getInstance());
    }

    public RaplaEvent getNextEvent(Calendar date) {
        RaplaEvent result = null;

        while (result == null) {
            Set<RaplaEvent> eventsToday = getEventsAtDate(date);

            Iterator<RaplaEvent> dayIterator = null;

            if (eventsToday != null)
                dayIterator = eventsToday.iterator();

            while (dayIterator != null && dayIterator.hasNext()) {
                RaplaEvent event = dayIterator.next();
                Calendar eventStart = event.getStartTime();
                if (eventStart.after(date) && (result == null || eventStart.before(result))) {
                    result = event;
                }
            }
            date.set(Calendar.DAY_OF_YEAR, date.get(Calendar.DAY_OF_YEAR) + 1);
            date.set(Calendar.HOUR_OF_DAY, 0);
            date.set(Calendar.MINUTE, 0);
            date.set(Calendar.SECOND, 0);

        }

        return result;
    }

    private void addEvent(RaplaEvent event) {
        Integer key = getDateHash(event.getStartTime());

        if (eventCal.containsKey(key)) {
            eventCal.get(key).add(event);
        } else {
            Set<RaplaEvent> l = new TreeSet<>();
            l.add(event);
            eventCal.put(key, l);
        }
    }

    private int getDateHash(Calendar date) {
        // we need that (whole) day's date as key
        return getDateHash(date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH), date.get(Calendar.YEAR));
    }

    private int getDateHash(int day, int month, int year) {
        return year * 365 + month * 31 + day - 1;
    }

    public void save(Context context) {
        android.util.Log.d("RaplaCalendar", "saving calendar");
        try {
            RapplaUtils.writeSerializedObject(context, CALENDAR_DB_FILE, this);

            RapplaPreferences.setSavedCalendarHash(context, hashCode());
            activeCalendar = this;
        } catch (IOException ex) {
            android.util.Log.d("RaplaCalendar", "error saving calendar: " + ex);
        }
    }

    public int hashCode() {
        int hash = 0;
        Collection<Set<RaplaEvent>> allEventSets = eventCal.values();

        for (Set<RaplaEvent> eventSet : allEventSets) {
            for (RaplaEvent event : eventSet) {
                hash += event.hashCode();
            }
        }
        return hash;
    }

    public ArrayList<EventModification> compare(RaplaCalendar otherCalendar) {
        ArrayList<EventModification> allModifications = new ArrayList<>();
        if (hashCode() == otherCalendar.hashCode())
            return allModifications;

        // GET ALL EVENTS INTO 2 ARRAYLISTS

        ArrayList<RaplaEvent> allMyEvents = new ArrayList<>();
        ArrayList<RaplaEvent> allOtherEvents = new ArrayList<>();
        ArrayList<RaplaEvent> allMyRemainingEvents = new ArrayList<>();
        ArrayList<RaplaEvent> allOtherRemainingEvents = new ArrayList<>();

        for (Set<RaplaEvent> eventSet : this.eventCal.values()) {
            allMyEvents.addAll(eventSet);
            allMyRemainingEvents.addAll(eventSet);
        }
        for (Set<RaplaEvent> eventSet : otherCalendar.eventCal.values()) {
            allOtherEvents.addAll(eventSet);
            allOtherRemainingEvents.addAll(eventSet);
        }

        boolean eventFound = false;

        // Now start comparing
        for (RaplaEvent myEvent : allMyEvents) {
            String uniqueID = myEvent.getUniqueEventID();
            for (RaplaEvent otherEvent : allOtherEvents) {
                if (otherEvent.getUniqueEventID().equals(uniqueID)) {
                    // Same Event
                    if (otherEvent.hashCode() != myEvent.hashCode()) {
                        // Something changed
                        allModifications.add(new EventModification(myEvent, otherEvent, EventModification.ModificationType.MODIFIED));
                    }

                    allMyRemainingEvents.remove(myEvent);
                    allOtherRemainingEvents.remove(otherEvent);
                    eventFound = true;
                    break;
                }
            }
            if (!eventFound) {
                allModifications.add(new EventModification(myEvent, null, EventModification.ModificationType.REMOVED));
                allMyRemainingEvents.remove(myEvent);
            }
            eventFound = false;
        }

        // Add all remaining events as new
        for (RaplaEvent otherEvent : allOtherRemainingEvents) {
            allModifications.add(new EventModification(null, otherEvent, EventModification.ModificationType.ADDED));
        }

        return allModifications;
    }
}
