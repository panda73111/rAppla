package app.rappla.ui.grid;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import app.rappla.R;
import app.rappla.calendar.RaplaEvent;

public class RapplaGridElement
{
	protected static final int eventImage = R.drawable.event;
	protected static final int[] eventImages = {R.drawable.event, R.drawable.event_blue, R.drawable.event_green};
	
	private static Typeface font = null;
	private Button eventButton;
	private RapplaGridElementLayout eventLayout;

	public RapplaGridElement(Context context, RaplaEvent raplaEvent, RapplaGridElementLayout layout)
	{
		eventButton = createEventButton(context, raplaEvent);
		eventLayout = layout;
	}

	public RapplaGridElement(Context context, RaplaEvent raplaEvent, int column, int offset, int length)
	{
		this(context, raplaEvent, new RapplaGridElementLayout(column, offset, length));
	}

	public RapplaGridElement(Context context, RaplaEvent raplaEvent, int column)
	{
		this(context, raplaEvent, new RapplaGridElementLayout(column, raplaEvent));
	}

	private Button createEventButton(Context context, RaplaEvent raplaEvent)
	{
		Button button = new Button(context);
		button.setOnClickListener(new EventClickListener(raplaEvent.getUniqueEventID(), context));
		
		button.setBackgroundResource(eventImages[(int) (Math.random()*eventImages.length)]);
		button.setTypeface(getFont(context));
		
		String eventName = raplaEvent.getEventNameWithoutProfessor();
		
		button.setText(eventName);
		button.setPadding(10, 20, 10, 10);
		button.setTextSize(12);
		
		return button;
	}

	public Button getEventButton()
	{
		return eventButton;
	}

	public RapplaGridElementLayout getEventLayout()
	{
		return eventLayout;
	}
	public static Typeface getFont(Context context)
	{
		if(font==null)
			font = Typeface.createFromAsset(context.getAssets(), "fonts/Graziano.ttf");
		return font;
	}
}
