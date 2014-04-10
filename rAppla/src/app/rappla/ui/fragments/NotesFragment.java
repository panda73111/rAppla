package app.rappla.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.rappla.R;
import app.rappla.StaticContext;


public class NotesFragment extends RapplaFragment
{
	public NotesFragment()
	{
		setTitle(StaticContext.getContext().getResources().getString(R.string.notes));
		setBackground = true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.layout_notes, container, false);

		if (LOG_EVENTS)
			Log.d(title, "onCreateView");

		return rootView;
	}
}
