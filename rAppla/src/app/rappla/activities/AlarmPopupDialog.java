package app.rappla.activities;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import app.rappla.R;
import app.rappla.alarms.RapplaNotification;
import app.rappla.calendar.RaplaCalendar;
import app.rappla.calendar.RaplaEvent;
import app.rappla.notes.Notes;

public class AlarmPopupDialog extends Activity implements OnClickListener {
    private static final String defaultNote = "Keine Notiz";

    RaplaEvent event;
    CharSequence note;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_alarm_popup);
        this.setFinishOnTouchOutside(true);

        Bundle extras = getIntent().getExtras();
        if (extras == null)
            return;

        note = getNote(extras);
        event = findEvent(extras);


        setTitle(event.getEventNameWithoutProfessor());

        configureButton();

        notificationOnStartup();
    }

    private void configureButton() {
        Button okButton = (Button) findViewById(R.id.okButton);
        Button toEventButton = (Button) findViewById(R.id.toEventButton);
        okButton.setOnClickListener(this);
        toEventButton.setOnClickListener(this);

    }

    public void onStart() {
        super.onStart();
        TextView noteView = (TextView) findViewById(R.id.noteText);
        noteView.setText(note);
    }

    public void onDestroy() {
        super.onDestroy();
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(R.integer.Notification_ID_Alarm);
    }

    private CharSequence getNote(Bundle extras) {
        if (!Notes.isInitialized())
            Notes.loadNoteFile(this);

        String uniqueID = extras.getString(EventActivity.eventIDKey);
        if (uniqueID == null)
            return defaultNote;

        CharSequence note = Notes.get(uniqueID);
        if (note == null || note.equals(""))
            return defaultNote;
        return note;

    }

    private RaplaEvent findEvent(Bundle extras) {
        RaplaEvent event;

        // Find the Calendar

        RapplaActivity activity = RapplaActivity.getInstance();
        RaplaCalendar calendar = null;
        if (activity != null) {
            calendar = activity.getActiveCalendar();
        }
        if (activity == null || calendar == null) {
            calendar = RaplaCalendar.load();
        }

        if (calendar == null) {
            Log.e("RaplaAlarmPupupDialog", "No calendar found!");
            return null;
        }

        try {
            String uniqueID = extras.getString(EventActivity.eventIDKey);
            event = calendar.getElementByUniqueID(uniqueID);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }

        return event;
    }

    private void notificationOnStartup() {
        PendingIntent eventIntent = PendingIntent.getActivity(this, 0, getEventActivityIntent(), PendingIntent.FLAG_UPDATE_CURRENT);
        RapplaNotification.showNotification(getApplicationContext(), event.getTitle(), note.toString(), eventIntent, R.integer.Notification_ID_Alarm);
    }

    private Intent getEventActivityIntent() {
        Intent resultIntent = new Intent(this, EventActivity.class);
        resultIntent.putExtra(EventActivity.eventIDKey, event.getUniqueEventID());
        return resultIntent;
    }

    @Override
    public void onClick(View v) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        switch (v.getId()) {
            case R.id.okButton:
                onClickOK();
                nm.cancel(R.integer.Notification_ID_Alarm);
                break;
            case R.id.toEventButton:
                onClickToEventButton();
                nm.cancel(R.integer.Notification_ID_Alarm);
                break;
            default:
                break;
        }
    }

    private void onClickToEventButton() {
        EventActivity eventActivity = EventActivity.getInstance();
        if (eventActivity != null) {
            eventActivity.finish();
        }
        startActivity(getEventActivityIntent());
    }

    private void onClickOK() {
        finish();
    }

}