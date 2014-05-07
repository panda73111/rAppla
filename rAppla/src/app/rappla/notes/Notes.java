package app.rappla.notes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import android.content.Context;

public class Notes
{
	public static final String serializedNoteFileName = "RapplaNotes.ser";
	static HashMap<String, String> notes = null;
	
	public static boolean isInitialized()
	{
		return notes!=null;
	}
	
	public static void saveNoteFile(Context c)
	{
		try
		{
			FileOutputStream fileOut = c.openFileOutput(serializedNoteFileName, Context.MODE_PRIVATE);
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
	public static void loadNoteFile(Context c)
	{
		try
		{
			FileInputStream fis 	= c.openFileInput(serializedNoteFileName);
			ObjectInputStream in 	= new ObjectInputStream(fis);
			notes = (HashMap<String, String>) in.readObject();
			in.close();
			fis.close();
		} catch(FileNotFoundException e)		// Thats no problem. The file does not exist yet
		{}
		catch (IOException i)
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

	public static void put(String eventID, String string)
	{
		notes.put(eventID, string);
	}

	public static CharSequence get(String eventID)
	{
		return notes.get(eventID);
	}
}
