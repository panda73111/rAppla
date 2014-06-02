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

import java.util.Locale;

/**
 * iCalendar Description class - This object represents a description and
 * corresponds to the DESCRIPTION property.
 * 
 * @version $Id: Description.java,v 1.7 2007/04/09 12:35:12 cknudsen Exp $
 * @author Craig Knudsen, craig@k5n.us
 */
public class Description extends Property
{
	/** Alternate representation URI */
	public String altrep = null;
	/** Language specification */
	public String language = null;

	/**
	 * Constructor
	 */
	public Description()
	{
		super("DESCRIPTION", "");
	}

	/**
	 * Constructor
	 * 
	 * @param icalStr
	 *            One or more lines of that specifies an event/todo description
	 * @param parseMode
	 *            PARSE_STRICT or PARSE_LOOSE
	 */
	public Description(String icalStr) throws ParseException
	{
		super(icalStr);

		for (int i = 0; i < attributeList.size(); i++)
		{
			Attribute a = attributeAt(i);
			String aname = a.name.toUpperCase(Locale.ENGLISH);
			if (aname.equals("ALTREP"))
			{
				altrep = a.value;
			} else if (aname.equals("LANGUAGE"))
			{
				language = a.value;
			}
		}
	}

}
