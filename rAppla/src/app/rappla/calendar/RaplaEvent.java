package app.rappla.calendar;

import java.util.Calendar;

public class RaplaEvent implements Comparable<RaplaEvent>
{
	private final String _uid;
	private final Calendar _startTime;
	private final Calendar _endTime;
	private final String _type;
	private final String _title;
	private final String _resources;
	private String _language = null;
	private String _course = null;
	private String _changesAllowedBy = null;
	private int _plannedHours = 0;
	private String _note = null;
	private String _createdBy = null;
	private String _lastChangeBy = null;
	private String _persons = null;

	public RaplaEvent(String uid, Calendar startTime,
			Calendar endTime, String type, String title, String resources)
	{
		_uid = uid;
		_startTime = startTime;
		_endTime = endTime;
		_type = type;
		_title = title;
		_resources = resources;
	}

	public static RaplaEvent FromEvent(Event e)
			throws MissingAttributeException
	{
		// all of these attributes are essential
		Uid uid = e.getUid();
		Date startTime = e.getStartDate();
		Date endTime = e.getEndDate();
		Categories categories = e.getCategories();
		Summary summary = e.getSummary();
		Location location = e.getLocation();

		if (uid == null)
			throw new MissingAttributeException("uid");
		if (startTime == null)
			throw new MissingAttributeException("startDate");
		if (endTime == null)
			throw new MissingAttributeException("endDate");
		if (categories == null)
			throw new MissingAttributeException("categories");
		if (summary == null)
			throw new MissingAttributeException("summary");
		if (location == null)
			throw new MissingAttributeException("location");

		RaplaEvent re = new RaplaEvent(uid.getValue(), startTime.toCalendar(), endTime.toCalendar(),
				categories.getValue(), summary.getValue(), location.getValue());

		return re;
	}
	
	public static RaplaEvent FromRecurringRaplaEvent(RaplaEvent e, Calendar newStartTime)
	{
		long oldStartTimeMillis = e._startTime.getTimeInMillis();
		long duration = e._endTime.getTimeInMillis() - oldStartTimeMillis;
		Calendar newEndTime = Calendar.getInstance();
		newEndTime.setTimeInMillis(oldStartTimeMillis + duration);
		
		RaplaEvent re = new RaplaEvent(e._uid, newStartTime, newEndTime, e._type, e._title, e._resources);
		re._changesAllowedBy = e._changesAllowedBy;
		re._course = e._course;
		re._createdBy = e._createdBy;
		re._language = e._language;
		re._lastChangeBy = e._lastChangeBy;
		re._note = e._note;
		re._persons = e._persons;
		re._plannedHours = e._plannedHours;
		return re;
	}

	public String getUid()
	{
		return _uid;
	}

	public Calendar getStartTime()
	{
		return _startTime;
	}

	public Calendar getEndTime()
	{
		return _endTime;
	}

	public String getType()
	{
		return _type;
	}

	public String getTitle()
	{
		return _title;
	}

	public String getResources()
	{
		return _resources;
	}

	public void setLanguage(String language)
	{
		_language = language;
	}

	public String getLanguage()
	{
		return _language;
	}

	public void setCourse(String course)
	{
		_course = course;
	}

	public String getCourse()
	{
		return _course;
	}

	public void setChangesAllowedBy(String changesAllowedBy)
	{
		_changesAllowedBy = changesAllowedBy;
	}

	public String getChangesAllowedBy()
	{
		return _changesAllowedBy;
	}

	public void setPlannedHours(int plannedHours)
	{
		_plannedHours = plannedHours;
	}

	public int getPlannedHours()
	{
		return _plannedHours;
	}

	public void setNote(String note)
	{
		_note = note;
	}

	public String getNote()
	{
		return _note;
	}

	public void setCreatedBy(String createdBy)
	{
		_createdBy = createdBy;
	}

	public String getCreatedBy()
	{
		return _createdBy;
	}

	public void setLastChangeBy(String lastChangeBy)
	{
		_lastChangeBy = lastChangeBy;
	}

	public String getLastChangeBy()
	{
		return _lastChangeBy;
	}

	public void setPersons(String persons)
	{
		_persons = persons;
	}

	public String getPersons()
	{
		return _persons;
	}

	@Override
	public int compareTo(RaplaEvent another)
	{
		return this._startTime.compareTo(another._startTime);
	}
}
