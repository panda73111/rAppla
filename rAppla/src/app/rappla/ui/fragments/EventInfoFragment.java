package app.rappla.ui.fragments;

import java.util.Calendar;
import java.util.HashMap;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.rappla.R;
import app.rappla.StaticContext;
import app.rappla.activities.EventActivity;
import app.rappla.calendar.RaplaEvent;

public class EventInfoFragment extends RapplaFragment
{
	public static final String serializedNoteFileName = "RapplaNotes.ser";

	private static Typeface font = null;
	static HashMap<String, String> notes = null;
	String eventID;
	int width;
	int height;

	public EventInfoFragment()
	{
		Context context = StaticContext.getContext();

		if (font == null)
			font = Typeface.createFromAsset(context.getAssets(), "fonts/Graziano.ttf");

		setTitle(context.getResources().getString(R.string.infos));
		setBackground = true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.layout_info, container, false);

		if (LOG_EVENTS)
			Log.d(title, "onCreateView");
		return rootView;
	}

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		repaintViews();
	}

	public void onResume()
	{
		super.onResume();
		repaintViews();
	}

	private void repaintViews()
	{
		RaplaEvent event = EventActivity.getInstance().getEvent();

		TextView titleView = (TextView) getActivity().findViewById(R.id.titleView);
		titleView.setText(event.getEventNameWithoutProfessor());
		titleView.setTypeface(font);
		titleView.setMovementMethod(new ScrollingMovementMethod());

		TextView profView = (TextView) getActivity().findViewById(R.id.profView);
		profView.setText(event.getProfessor());
		profView.setTypeface(font);
		profView.setMovementMethod(new ScrollingMovementMethod());

		Calendar startTime = event.getStartTime();
		Calendar endTime = event.getEndTime();

		TextView beginView = (TextView) getActivity().findViewById(R.id.beginView);
		beginView.setText(startTime.get(Calendar.HOUR_OF_DAY) + ":" + startTime.get(Calendar.MINUTE));
		beginView.setTypeface(font);

		TextView endView = (TextView) getActivity().findViewById(R.id.endView);
		endView.setText(endTime.get(Calendar.HOUR_OF_DAY) + ":" + endTime.get(Calendar.MINUTE));
		endView.setTypeface(font);

		TextView resView = (TextView) getActivity().findViewById(R.id.ressourcesView);
		resView.setText(event.getResources());
		resView.setTypeface(font);
		resView.setMovementMethod(new ScrollingMovementMethod());

		TextView dateView = (TextView) getActivity().findViewById(R.id.dateView);
		dateView.setText(startTime.get(Calendar.DAY_OF_MONTH) + "/" + (startTime.get(Calendar.MONTH) + 1) + "/" + startTime.get(Calendar.YEAR));
		dateView.setTypeface(font);
	}

	public void doGlobalLayout()
	{
		TextView titleView = (TextView) getActivity().findViewById(R.id.titleView);
		TextView profView = (TextView) getActivity().findViewById(R.id.profView);
		TextView beginView = (TextView) getActivity().findViewById(R.id.beginView);
		TextView endView = (TextView) getActivity().findViewById(R.id.endView);
		TextView resView = (TextView) getActivity().findViewById(R.id.ressourcesView);
		TextView dateView = (TextView) getActivity().findViewById(R.id.dateView);

		titleView.setWidth((int) (getWidth() * 0.6));
		profView.setWidth((int) (getWidth() * 0.6));
		beginView.setWidth((int) (getWidth() * 0.6));
		endView.setWidth((int) (getWidth() * 0.6));
		resView.setWidth((int) (getWidth() * 0.6));
		dateView.setWidth((int) (getWidth() * 0.6));
	}

}
