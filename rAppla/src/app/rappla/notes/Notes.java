package app.rappla.notes;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import app.rappla.RapplaUtils;

public class Notes {
    public static final String serializedNoteFileName = "RapplaNotes.ser";
    static HashMap<String, String> notes = null;

    public static boolean isInitialized() {
        return notes != null;
    }

    public static void saveNoteFile(Context c) {
        try {
            RapplaUtils.writeSerializedObject(c, serializedNoteFileName, notes);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void loadNoteFile(Context c) {
        try {
            notes = RapplaUtils.readSerializedObject(c, serializedNoteFileName);
        } catch (FileNotFoundException e)        // Thats no problem. The file does not exist yet
        {
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        } finally {
            if (notes == null)
                notes = new HashMap<>();
        }
    }

    public static void put(String eventID, String string) {
        notes.put(eventID, string);
    }

    public static CharSequence get(String eventID) {
        return notes.get(eventID);
    }
}
