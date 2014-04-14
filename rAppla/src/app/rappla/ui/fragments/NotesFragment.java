package app.rappla.ui.fragments;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import app.rappla.R;
import app.rappla.StaticContext;
import app.rappla.activities.EventActivity;
import app.rappla.calendar.RaplaEvent;

public class NotesFragment extends RapplaFragment
{
	public static final String serializedNoteFileName = "RapplaNotes.ser";
	
	static HashMap<String, String> notes = null;
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
		if (notes == null)
			loadNoteFile();
		
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
		
		eventID					= event.getUid();
		
		noteTextView.setText((CharSequence) notes.get(eventID));
	}

	public void onClickedSave()
	{
		EditText noteTextView 	= (EditText) getActivity().findViewById(R.id.editText1);
		notes.put(eventID, noteTextView.getText().toString());
		
		saveNoteFile();
		
		getActivity().finish();
	}
	
	
	public void saveNoteFile()
	{
		try
		{
			FileOutputStream fileOut = getActivity().openFileOutput(serializedNoteFileName, Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(notes);
			out.close();
			fileOut.close();
		} catch (IOException i)
		{
			i.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void loadNoteFile()
	{
		try
		{
			FileInputStream fis 	= getActivity().openFileInput(serializedNoteFileName);
			ObjectInputStream in 	= new ObjectInputStream(fis);
			notes = (HashMap<String, String>) in.readObject();
			in.close();
			fis.close();
		} catch (IOException i)
		{
			i.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} finally
		{
			if(notes == null)
				notes = new HashMap<String, String>();
		}
	}
}
