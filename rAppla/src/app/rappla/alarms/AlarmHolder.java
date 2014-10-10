package app.rappla.alarms;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import app.rappla.R;
import app.rappla.RapplaUtils;

public class AlarmHolder {
    private static final int alarmOnResource = R.drawable.alarmon;
    private static final int alarmOffResource = R.drawable.alarmoff;
    Alarm myAlarm = null;
    LinearLayout alarmGroup;
    RelativeLayout alarmSubGroup;
    Button dateButton;
    Button timeButton;
    Button alarmButton;


    public AlarmHolder(Alarm alarm) {
        myAlarm = alarm;
    }

    public void initViews(Context c, ViewGroup parent) {

        OnAlarmClickListener oacl = new OnAlarmClickListener(c, this, myAlarm);
        Drawable bd = c.getResources().getDrawable(alarmOnResource);
        int height = bd.getIntrinsicHeight();
        int width = bd.getIntrinsicWidth();

        alarmButton = new Button(c);
        alarmButton.setId(R.id.activateButtonID);
        alarmButton.setOnClickListener(oacl);

        dateButton = new Button(c);
        dateButton.setId(R.id.dateButtonID);
        dateButton.setOnClickListener(oacl);

        timeButton = new Button(c);
        timeButton.setId(R.id.timeButtonID);
        timeButton.setOnClickListener(oacl);

        alarmGroup = new LinearLayout(c);
        alarmSubGroup = new RelativeLayout(c);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, dateButton.getId());

        alarmSubGroup.addView(dateButton);
        alarmSubGroup.addView(timeButton, lp);
        alarmGroup.addView(alarmButton, width, height);
        alarmGroup.addView(alarmSubGroup);
        parent.addView(alarmGroup);

        updateViews();
    }

    public void updateViews() {
        if (dateButton != null) {
            dateButton.setText(RapplaUtils.toDateString(myAlarm.getDate()));
        }
        if (timeButton != null) {
            timeButton.setText(RapplaUtils.toTimeString(myAlarm.getDate()));
        }
        if (alarmButton != null) {
            if (myAlarm.isActive)
                alarmButton.setBackgroundResource(alarmOnResource);
            else
                alarmButton.setBackgroundResource(alarmOffResource);
        }
    }
}
