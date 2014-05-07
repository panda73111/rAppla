package app.rappla.calendar;

public class MissingAttributeException extends CalendarFormatException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7710102965595484109L;

	public MissingAttributeException(String error)
	{
		super(error);
	}
}
