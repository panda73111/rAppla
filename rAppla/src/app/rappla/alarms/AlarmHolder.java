package app.rappla.alarms;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import app.rappla.R;
import app.rappla.RapplaUtils;
import app.rappla.ui.fragments.AlarmFragment;

public class AlarmHolder extends LinearLayout {

    private static final int alarmOnResource = R.drawable.alarmon;
    private static final int alarmOffResource = R.drawable.alarmoff;

    Alarm myAlarm = null;
    RelativeLayout dateAndTimeGroup = null;
    AlarmFragment parentFragment = null;

    Button dateButton;
    Button timeButton;
    Button alarmButton;
    Button removeButton;


    public AlarmHolder(Alarm alarm, AlarmFragment alarmFragment, Context context) {
        super(context);
        parentFragment = alarmFragment;
        myAlarm = alarm;
        initViews(context);
    }

    public Alarm getAlarm() {
        return myAlarm;
    }

    public void updateViews() {
        if (dateButton != null) {
            dateButton.setText(RapplaUtils.toDateString(myAlarm.getDate()));
        }
        if (timeButton != null) {
            timeButton.setText(RapplaUtils.toTimeString(myAlarm.getDate()));
        }
        if (alarmButton != null) {
            setAlarmIconBackground();
        }
    }


    private void initViews(Context c) {

        OnAlarmClickListener alarmClickListener = new OnAlarmClickListener(c, this, parentFragment);

        configureActivateButton(c, alarmClickListener);
        configureDateButton(c, alarmClickListener);
        configureTimeButton(c, alarmClickListener);
        configureRemoveButton(c, alarmClickListener);

        configureDateAndTimeGroup(c);
        configureButtonGroup(c);

        updateViews();
    }

    private void setAlarmIconBackground() {
        if (myAlarm.isActive)
            alarmButton.setBackgroundResource(alarmOnResource);
        else
            alarmButton.setBackgroundResource(alarmOffResource);
    }

    private void configureActivateButton(Context c, OnAlarmClickListener alarmClickListener) {
        alarmButton = new Button(c);
        alarmButton.setId(R.id.ID_activateButton);
        alarmButton.setOnClickListener(alarmClickListener);
    }

    private void configureDateButton(Context c, OnAlarmClickListener alarmClickListener) {
        dateButton = new Button(c);
        dateButton.setId(R.id.ID_dateButton);
        dateButton.setOnClickListener(alarmClickListener);
    }

    private void configureTimeButton(Context c, OnAlarmClickListener alarmClickListener) {
        timeButton = new Button(c);
        timeButton.setId(R.id.ID_timeButton);
        timeButton.setOnClickListener(alarmClickListener);
    }

    private void configureRemoveButton(Context c, OnAlarmClickListener alarmClickListener) {
        removeButton = new Button(c);
        removeButton.setId(R.id.ID_removeButton);
        removeButton.setOnClickListener(alarmClickListener);
        removeButton.setText("X");
    }

    private void configureDateAndTimeGroup(Context c) {
        dateAndTimeGroup = new RelativeLayout(c);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, dateButton.getId());
        dateAndTimeGroup.addView(dateButton);
        dateAndTimeGroup.addView(timeButton, lp);
    }

    private void configureButtonGroup(Context c) {
        Drawable bd = c.getResources().getDrawable(alarmOnResource);
        int height = bd.getIntrinsicHeight();
        int width = bd.getIntrinsicWidth();
        addView(alarmButton, width, height);
        addView(dateAndTimeGroup);
        addView(removeButton, width / 3, height);
    }

}
