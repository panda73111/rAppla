/*
 * Copyright (C) 2005-2006 Craig Knudsen and other authors
 * (see AUTHORS for a complete list)
 *
 * JavaCalTools is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * A copy of the GNU Lesser General Public License is included in the Wine
 * distribution in the file COPYING.LIB. If you did not receive this copy,
 * write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA.
 */

package app.rappla.calendar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple implementation of the DataStore interface. This is the default
 * implementation used by the IcalParser class.
 * 
 * @version $Id: DefaultDataStore.java,v 1.6 2007/06/15 13:55:59 cknudsen Exp $
 * @author Craig Knudsen, craig@k5n.us
 * @see CalendarParser
 */
public class DefaultDataStore implements DataStore
{
	ArrayList<Timezone> timezones;
	ArrayList<Event> events;

	/**
	 * Constructor
	 */
	public DefaultDataStore()
	{
		timezones = new ArrayList<Timezone>();
		events = new ArrayList<Event>();
	}

	/**
	 * This method will be called the parser finds a VTIMEZONE object.
	 */
	public void storeTimezone(Timezone timezone)
	{
		timezones.add(timezone);
	}

	/**
	 * This method will be called the parser finds a VEVENT object.
	 */
	public void storeEvent(Event event)
	{
		events.add(event);
	}

	/**
	 * Get a Vector of all Event objects
	 */
	public List<Event> getAllEvents()
	{
		return events;
	}

}
