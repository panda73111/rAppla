package app.rappla.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import app.rappla.R;
import app.rappla.StaticContext;
import app.rappla.activities.EventActivity;
import app.rappla.alarms.Alarm;
import app.rappla.alarms.AlarmHolder;
import app.rappla.calendar.RaplaEvent;

public class AlarmFragment extends RapplaFragment {
    LinearLayout alarmList = null;

    String eventID = null;
    ArrayList<Alarm> eventAlarms = null;
    HashMap<Alarm, AlarmHolder> alarmGUI = null;

    public AlarmFragment() {
        setTitle(StaticContext.getContext().getResources().getString(R.string.alarms));
        setBackground = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_alarms, container, false);

        if (LOG_EVENTS)
            Log.d(title, "onCreateView");

        return rootView;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RaplaEvent event = ((EventActivity) getActivity()).getEvent();
        eventID = event.getUniqueEventID();
    }

    public void onStart() {
        super.onStart();

        eventAlarms = Alarm.getAlarmsAtEvent(getActivity(), eventID);

        Button addBtn = (Button) getActivity().findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedAdd();
            }
        });
        Button saveBtn = (Button) getActivity().findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedSave();
            }
        });
        alarmList = (LinearLayout) getActivity().findViewById(R.id.alarmHolder);
        alarmGUI = new HashMap<>();
    }

    public void onResume() {
        super.onResume();
        refreshPanel();
    }

    public void onClickedAdd() {
        RaplaEvent event = ((EventActivity) getActivity()).getEvent();
        Alarm newAlarm = new Alarm(event.getDate(), eventID);

        eventAlarms.add(newAlarm);
        Alarm.setAlarmsAtEvent(getActivity(), eventID, eventAlarms);
        refreshPanel();
    }

    public void onClickedSave() {
        for (Alarm a : eventAlarms) {
            a.applyState(getActivity());
        }
        Alarm.saveAlarmFile(getActivity());
        Toast.makeText(getActivity(), R.string.alarmsaved, Toast.LENGTH_LONG).show();
    }

    public void refreshPanel() {
        for (Alarm alarm : eventAlarms) {
            drawAlarm(alarm);
        }
    }

    private void drawAlarm(Alarm alarm) {
        AlarmHolder holder = alarmGUI.get(alarm);
        if (holder == null) {
            holder = new AlarmHolder(alarm);
            holder.initViews(getActivity(), alarmList);
            return;
        }
        holder.updateViews();
    }
}