package app.rappla.calendar;

import app.rappla.RapplaUtils;

/**
 * Well Created by lorenzo on 14.10.14.
 */
public class EventModification {
    private RaplaEvent oldEvent;
    private RaplaEvent newEvent;
    private ModificationType modification;

    public EventModification(RaplaEvent oldEvent, RaplaEvent newEvent, ModificationType modification) {
        this.oldEvent = oldEvent;
        this.newEvent = newEvent;
        this.modification = modification;
    }

    public int hashCode() {
        int hashOldEvent = oldEvent != null ? oldEvent.hashCode() : 0;
        int hashNewEvent = newEvent != null ? newEvent.hashCode() : 0;
        int hashModification = modification != null ? modification.hashCode() : 0;

        return (hashOldEvent + hashNewEvent + hashModification) * hashModification / hashOldEvent + hashNewEvent;
    }

    public boolean equals(Object other) {
        if (other instanceof EventModification) {
            EventModification otherModification = (EventModification) other;
            if (oldEvent.hashCode() == otherModification.getOldEvent().hashCode()
                    && newEvent.hashCode() == otherModification.getNewEvent().hashCode()
                    && modification == otherModification.getModification())
                return true;
        }

        return false;
    }

    public String toString() {
        switch (modification) {
            case ADDED:
                return newEvent.getEventNameWithoutProfessor() + " wurde am " + RapplaUtils.toDateString(newEvent.getStartTime()) + " um " + RapplaUtils.toTimeString(newEvent.getStartTime()) + " hinzugefügt.";
            case REMOVED:
                return oldEvent.getEventNameWithoutProfessor() + " am " + RapplaUtils.toDateString(oldEvent.getStartTime()) + " wurde entfernt.";
            case MODIFIED:
                return oldEvent.getEventNameWithoutProfessor() + " wurde verändert.";
        }
        return "(" + oldEvent + ", " + newEvent + ", " + modification + ")";
    }

    public RaplaEvent getOldEvent() {
        return oldEvent;
    }

    public RaplaEvent getNewEvent() {
        return newEvent;
    }

    public ModificationType getModification() {
        return modification;
    }

    public enum ModificationType {
        ADDED, REMOVED, MODIFIED
    }
}
