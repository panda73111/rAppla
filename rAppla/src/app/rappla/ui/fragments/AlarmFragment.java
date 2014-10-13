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

import app.rappla.R;
import app.rappla.StaticContext;
import app.rappla.activities.EventActivity;
import app.rappla.alarms.Alarm;
import app.rappla.alarms.AlarmHolder;
import app.rappla.calendar.RaplaEvent;

public class AlarmFragment extends RapplaFragment {
    LinearLayout alarmList = null;
    ArrayList<AlarmHolder> eventAlarms = null;
    ArrayList<AlarmHolder> unsetAlarms = null;

    String eventID = null;
    ButtonListener myButtonListener = null;

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
        setRetainInstance(true);
        getEventId();
    }
    public void onStart() {
        super.onStart();

        myButtonListener = new ButtonListener();

        ArrayList<Alarm> allAlarm = Alarm.getAlarmsAtEvent(getActivity(), eventID);
        eventAlarms = generateAlarmHolder(allAlarm);
        unsetAlarms = new ArrayList<>();
        configureViews();
    }
    public void onResume() {
        super.onResume();
        refreshPanel();
    }


    public void refreshPanel() {
        alarmList.removeAllViews();
        for (AlarmHolder holder : eventAlarms) {
            alarmList.addView(holder);
        }
        for (AlarmHolder holder : unsetAlarms) {
            alarmList.addView(holder);
        }
    }

    public void removeAlarm(AlarmHolder holder) {
        Alarm alarm = holder.getAlarm();
        alarm.setActive(false);
        alarm.applyState(getActivity());

        eventAlarms.remove(holder);
        unsetAlarms.remove(holder);
        refreshPanel();
    }


    private void getEventId() {
        RaplaEvent event = ((EventActivity) getActivity()).getEvent();
        eventID = event.getUniqueEventID();
    }

    private void configureViews() {
        Button addBtn = (Button) getActivity().findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(myButtonListener);
        Button saveBtn = (Button) getActivity().findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(myButtonListener);
        alarmList = (LinearLayout) getActivity().findViewById(R.id.alarmHolder);
    }

    private ArrayList<AlarmHolder> generateAlarmHolder(ArrayList<Alarm> alarms) {
        ArrayList<AlarmHolder> holders = new ArrayList<>();
        for (Alarm a : alarms) {
            AlarmHolder holder = new AlarmHolder(a, this, getActivity());
            holders.add(holder);
        }
        return holders;
    }

    private ArrayList<Alarm> generateAlarms(ArrayList<AlarmHolder> alarmHolders) {
        ArrayList<Alarm> alarms = new ArrayList<>();
        for (AlarmHolder a : alarmHolders) {
            alarms.add(a.getAlarm());
        }
        return alarms;
    }


    public class ButtonListener implements OnClickListener {
        private void onClickedAdd() {
            RaplaEvent event = ((EventActivity) getActivity()).getEvent();

            Alarm newAlarm = new Alarm(event.getDate(), eventID);
            AlarmHolder alarmHolder = new AlarmHolder(newAlarm, AlarmFragment.this, getActivity());

            unsetAlarms.add(alarmHolder);

            refreshPanel();
        }

        private void onClickedSave() {
            eventAlarms.addAll(unsetAlarms);
            for (AlarmHolder holder : eventAlarms) {
                holder.getAlarm().applyState(getActivity());
            }
            Alarm.setAlarmsAtEvent(getActivity(), eventID, generateAlarms(eventAlarms));
            Alarm.saveAlarmFile(getActivity());
            Toast.makeText(getActivity(), R.string.alarmsaved, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAdd:
                    onClickedAdd();
                    break;
                case R.id.btnSave:
                    onClickedSave();
                    break;
            }
        }
    }
}