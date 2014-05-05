package app.rappla.ui.grid;

import android.content.Context;
import android.widget.Button;
import app.rappla.R;
import app.rappla.calendar.RaplaEvent;
import app.rappla.ui.fragments.CalenderFragment;

public class RapplaGridElement
{
	protected static final int eventImage = R.drawable.event_glass;
	
	
	private Button eventButton;
	private RapplaGridElementLayout eventLayout;

	public RapplaGridElement(Context context, RaplaEvent raplaEvent, RapplaGridElementLayout layout)
	{
		eventButton = createEventButton(context, raplaEvent);
		eventLayout = layout;
	}

	public RapplaGridElement(Context context, RaplaEvent raplaEvent, int column, int offset, int length)
	{
		eventButton = createEventButton(context, raplaEvent);
		eventLayout = new RapplaGridElementLayout(column, offset, length);
	}

	public RapplaGridElement(Context context, RaplaEvent raplaEvent, int column)
	{
		eventButton = createEventButton(context, raplaEvent);
		eventLayout = createEventLayout(raplaEvent, column);
	}

	private Button createEventButton(Context context, RaplaEvent raplaEvent)
	{
		Button button = new Button(context);
		button.setOnClickListener(new EventClickListener(raplaEvent.getUniqueEventID(), context));
		
		button.setBackgroundResource(eventImage);
		
		String eventName = getEventName(raplaEvent);
		
		button.setText(eventName);
		button.setPadding(3, 0, 3, 0);
		button.setTextSize(12);
		
		return button;
	}

	private String getEventName(RaplaEvent raplaEvent)
	{
		String 	eventName 					= raplaEvent.getTitle();
		int 	separatorIndex				= eventName.indexOf("[");
		
		if(separatorIndex > 0)
			return eventName.substring(0, separatorIndex);
		else
			return eventName;
	}

	private RapplaGridElementLayout createEventLayout(RaplaEvent raplaEvent, int column)
	{
		int eventLength = (int) (raplaEvent.getDurationInMinutes() / CalenderFragment.timeInterval);
		int eventOffset = (int) (raplaEvent.getTimeDifferenceInMinutes(CalenderFragment.getEarliestStart(raplaEvent.getDate())) / CalenderFragment.timeInterval);

		// Sorgt dafür, dass kein Event länger als der Tag werden kann
		// TODO: Das geht noch schöner
		eventLength = (int) Math.min(CalenderFragment.getDayDuration() / CalenderFragment.timeInterval - eventOffset, eventLength);

		return new RapplaGridElementLayout(column, eventOffset, eventLength);
	}

	public Button getEventButton()
	{
		return eventButton;
	}

	public RapplaGridElementLayout getEventLayout()
	{
		return eventLayout;
	}
}
