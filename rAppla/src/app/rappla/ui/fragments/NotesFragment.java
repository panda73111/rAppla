package app.rappla.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import app.rappla.R;
import app.rappla.StaticContext;
import app.rappla.activities.EventActivity;
import app.rappla.calendar.RaplaEvent;
import app.rappla.notes.Notes;

public class NotesFragment extends RapplaFragment
{	
	String eventID;
	
	
	
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

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	
	@SuppressWarnings("deprecation")
	public void onStart()
	{
		super.onStart();
		if (!Notes.isInitialized())
			Notes.loadNoteFile(getActivity());
		
		Button		saveButton	= (Button)		getActivity().findViewById(R.id.button1);
		EditText 	noteTextView= (EditText) 	getActivity().findViewById(R.id.editText1);
		
		noteTextView.setBackgroundDrawable(null);
		saveButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0)
			{
				onClickedSave();
			}}
		);
		
		RaplaEvent event 		= EventActivity.getInstance().getEvent();		
		eventID					= event.getUniqueEventID();		
		noteTextView.setText(Notes.get(eventID));
	}

	public void onClickedSave()
	{
		EditText noteTextView 	= (EditText) getActivity().findViewById(R.id.editText1);
		Notes.put(eventID, noteTextView.getText().toString());
		
		Notes.saveNoteFile(getActivity());
		Toast.makeText(getActivity(), R.string.notesaved, Toast.LENGTH_LONG).show();
	}
	
	

}
