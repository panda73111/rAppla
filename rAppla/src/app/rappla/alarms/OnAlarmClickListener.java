package app.rappla.alarms;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import app.rappla.R;
import app.rappla.ui.fragments.AlarmFragment;

public class OnAlarmClickListener implements OnClickListener {
    Context context;
    AlarmHolder alarmHolder;
    AlarmFragment alarmFragment;
    Alarm alarm;

    public OnAlarmClickListener(Context c, AlarmHolder alarmHolder, AlarmFragment parent) {
        this.context = c;
        this.alarmHolder = alarmHolder;
        this.alarmFragment = parent;
        alarm = alarmHolder.getAlarm();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ID_dateButton:
                onDateClick();
                break;
            case R.id.ID_timeButton:
                onTimeClick();
                break;
            case R.id.ID_removeButton:
                onRemoveClick();
                break;
            case R.id.ID_activateButton:
                alarm.setActive(!alarm.isActive);
                alarmHolder.updateViews();
                break;
        }
    }

    private void onRemoveClick() {
        alarmFragment.removeAlarm(alarmHolder);
    }

    private void onDateClick() {
        OnDateSetListener dateSetListener = new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                alarm.getDate().set(year, monthOfYear, dayOfMonth);
                alarmHolder.updateViews();
            }
        };
        new DatePickerDialog(context, dateSetListener, alarm.getDate().get(Calendar.YEAR), alarm.getDate().get(Calendar.MONTH),
                alarm.getDate().get(Calendar.DAY_OF_MONTH)).show();
    }

    private void onTimeClick() {

        OnTimeSetListener timeSetListener = new OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker arg0, int hour, int minute) {
                alarm.getDate().set(Calendar.HOUR_OF_DAY, hour);
                alarm.getDate().set(Calendar.MINUTE, minute);
                alarmHolder.updateViews();
            }
        };
        new TimePickerDialog(context, timeSetListener, alarm.getDate().get(Calendar.HOUR_OF_DAY),
                alarm.getDate().get(Calendar.MINUTE), true).show();
    }

}
