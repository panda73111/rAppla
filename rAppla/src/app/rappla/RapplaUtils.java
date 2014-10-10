package app.rappla;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

public class RapplaUtils {

    public static String toDateString(Calendar date) {
        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH);
        int year = date.get(Calendar.YEAR);
        return day + "/" + (month + 1) + "/" + year;
    }

    public static String toTimeString(Calendar date) {
        String hour = "" + date.get(Calendar.HOUR_OF_DAY);
        String minute = "" + date.get(Calendar.MINUTE);

        if (minute.length() == 1)
            minute = "0" + minute;

        return hour + ":" + minute;
    }

    @SuppressWarnings("unchecked")
    public static <T> T readSerializedObject(Context context, String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = context.openFileInput(fileName);
        ObjectInputStream in = new ObjectInputStream(fis);

        T out = (T) in.readObject();

        in.close();
        fis.close();
        return out;
    }

    public static <T> void writeSerializedObject(Context context, String fileName, T objectToWrite) throws IOException {
        FileOutputStream fileOut = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(objectToWrite);
        out.close();
        fileOut.close();
    }
}
