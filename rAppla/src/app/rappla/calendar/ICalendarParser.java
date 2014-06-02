package app.rappla.calendar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ICalendarParser extends CalendarParser implements Constants
{
	public ICalendarParser()
	{
		super();
	}

	String language = "EN"; // default language setting
	static final int STATE_NONE = 0;
	static final int STATE_VCALENDAR = 1;
	static final int STATE_VEVENT = 2;
	static final int STATE_VTIMEZONE = 5;
	static final int STATE_DONE = 7;

	/**
	 * Create an ICalendarParser object. By default, this will also setup the
	 * default DataStore object. To remove the default DataStore, you can call
	 * removeDataStoreAt(0).
	 * 
	 * @param parseMethod
	 *            Specifies the parsing method, which should be either
	 *            PARSE_STRICT or PARSE_LOOSE. The PARSE_STRICT method will
	 *            follow the RFC 2445 specification strictly and is intended to
	 *            be used to validate iCalendar data. Most clients should
	 *            specify PARSE_LOOSE to capture as much of the data as
	 *            possible.
	 * @param language
	 *            Default language setting. When parsing objects, the property
	 *            that matches this language setting will take priority. For
	 *            example, if "EN" is specified as a parameter here, and an
	 *            event in iCalendar has a summary in "EN" and also in "FR",
	 *            then the summary in "EN" will be returned when the event is
	 *            queries for a summary.
	 */
	public ICalendarParser(String language)
	{
		super();
		this.language = language;
	}

	/**
	 * Parse a File.
	 * 
	 * @param reader
	 *            The java.io.Reader object to read the iCalendar data from. To
	 *            parse a String object use java.io.StringReader.
	 * @return true if no parse errors encountered
	 */
	public void parse(Reader reader) throws IOException
	{
		String line, nextLine;
		BufferedReader r = new BufferedReader(reader);
		int state = STATE_NONE;
		int ln = 0; // line number
		int startLineNo = 0;
		List<String> textLines;
		boolean done = false;

		// Because iCalendar allows lines to be "folded" (continued) onto
		// multiple
		// lines, you need to peek ahead to the next line to know if you have
		// all the text for what you are trying to parse.
		// The "line" variable is what is currently being parsed. The "nextLine"
		// variable contains the next line of text to be processed.
		// TODO: line numbers in errors may be off for folded lines since the
		// last line number of the text will be reported.
		textLines = new ArrayList<String>();
		nextLine = r.readLine();
		if (nextLine == null)
		{
			// empty file
		} else
		{
			while (!done)
			{
				line = nextLine;
				ln++;
				if (nextLine != null)
				{
					nextLine = r.readLine();
					// if nextLine is null, don't set done to true yet since we
					// still
					// need another iteration through the while loop for the
					// text
					// to get processed.
				}
				// Check to see if next line is a continuation of the current
				// line. If it is, then append the contents of the next line
				// onto the current line.
				if (nextLine != null
						&& nextLine.length() > 0
						&& (nextLine.charAt(0) == SPACE || nextLine.charAt(0) == TAB))
				{
					// Line folding found. Add to previous line and continue.
					nextLine = line + CRLF + nextLine;
					continue; // skip back to start of while loop
				}
				String lineUp = line.toUpperCase(Locale.ENGLISH);

				// System.out.println ( "[DATA:" + state + "]" + line );
				switch (state)
				{

				case STATE_NONE:
					if (lineUp.startsWith("BEGIN:VCALENDAR"))
						state = STATE_VCALENDAR;
					break;

				case STATE_VCALENDAR:
					if (lineUp.startsWith("BEGIN:VTIMEZONE"))
					{
						state = STATE_VTIMEZONE;
						startLineNo = ln; // mark starting line number
						textLines.clear();
						textLines.add(line);
					} else if (lineUp.startsWith("BEGIN:VEVENT"))
					{
						state = STATE_VEVENT;
						startLineNo = ln; // mark starting line number
						textLines.clear();
						textLines.add(line);
					} else if (lineUp.startsWith("END:VCALENDAR"))
					{
						state = STATE_DONE;
					} else if (lineUp.startsWith("VERSION"))
					{
						// ignore version
					} else if (lineUp.startsWith("PRODID"))
					{
						// ignore product id
					} else if (lineUp.startsWith("CALSCALE"))
					{
						// ignore calendat scale
					} else if (lineUp.startsWith("METHOD"))
					{
						// ignore method
					} else
					{
						// ignore unknown property
					}
					break;

				case STATE_VTIMEZONE:
					textLines.add(line);
					if (lineUp.startsWith("END:VTIMEZONE"))
					{
						state = STATE_VCALENDAR;
						Timezone timezone = new Timezone(this, startLineNo,
								textLines);
						if (timezone.isValid())
						{
							dataStore.storeTimezone(timezone);
						}
						textLines.clear(); // truncate Vector
					}
					break;

				case STATE_VEVENT:
					textLines.add(line);
					if (lineUp.startsWith("END:VEVENT"))
					{
						state = STATE_VCALENDAR;
						Event event = new Event(this, startLineNo, textLines);
						if (event.isValid())
						{
							dataStore.storeEvent(event);
						} else
						{
							System.err.println("ERROR: Invalid VEVENT found");
						}
						textLines.clear(); // truncate Vector
					}
					break;

				case STATE_DONE:
					// should be nothing else after "END:VCALENDAR"
					if (lineUp.trim().length() == 0)
					{
						// ignore blank lines at end of file
					}
					break;
				}
				if (nextLine == null)
					done = true;
			}
		}
		r.close();

		return;
	}
}
