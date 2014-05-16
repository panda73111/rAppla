package app.rappla.calendar;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * The CalendarParser is an abstract class that should be extended to implement
 * a specific parser (iCalendar, CSV, etc.)
 * 
 * @author Craig Knudsen, craig@k5n.us
 * @version $Id: CalendarParser.java,v 1.1 2007/06/15 13:55:59 cknudsen Exp $
 */
public abstract class CalendarParser implements Constants
{
	protected List<ParseErrorListener> errorListeners;
	protected List<ParseError> errors;
	protected DataStore dataStore;

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
	 */
	public CalendarParser()
	{
		errorListeners = new ArrayList<ParseErrorListener>();
		errors = new ArrayList<ParseError>();
		dataStore = new DefaultDataStore();
	}

	/**
	 * Add a listener for parse error messages.
	 * 
	 * @pel The listener for parse errors
	 */
	public void addParseErrorListener(ParseErrorListener pel)
	{
		errorListeners.add(pel);
	}

	/**
	 * Send a parse error message to all parse error listeners
	 * 
	 * @param msg
	 *            The error message
	 * @param icalStr
	 *            The offending line(s) of iCalendar
	 */
	public void reportParseError(ParseError error)
	{
		errors.add(error);
		for (int i = 0; i < errorListeners.size(); i++)
		{
			ParseErrorListener pel = errorListeners.get(i);
			pel.reportParseError(error);
		}
	}

	/**
	 * Get a Vector of all errors encountered;.
	 * 
	 * @return A Vector of ParseError objects
	 */
	public List<ParseError> getAllErrors()
	{
		return errors;
	}

	/**
	 * Parse a Reader object.
	 * 
	 * @param reader
	 * @return true if no errors, false if errors found
	 * @throws IOException
	 */
	public abstract void parse(Reader reader) throws IOException;

}
