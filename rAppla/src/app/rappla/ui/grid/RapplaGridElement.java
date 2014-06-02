package app.rappla.ui.grid;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import app.rappla.R;
import app.rappla.calendar.RaplaEvent;
import app.rappla.ui.fragments.CalendarFragment;

public class RapplaGridElement extends ViewGroup
{
	private RapplaGridElementLayout eventLayout;
	protected static final int[] eventImages = { R.drawable.event, R.drawable.event_blue, R.drawable.event_green };

	private ImageView bgView;
	private static Typeface font = null;
	private TextView textView;

	public RapplaGridElement(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public RapplaGridElement(Context context, RaplaEvent raplaEvent, RapplaGridElementLayout layout)
	{
		super(context);
		init(context, raplaEvent);
		eventLayout = layout;
	}

	public RapplaGridElement(Context context, RaplaEvent raplaEvent, int column, int offset, int length)
	{
		super(context);
		init(context, raplaEvent);
		eventLayout = new RapplaGridElementLayout(column, offset, length);
	}

	public RapplaGridElement(Context context, RaplaEvent raplaEvent, int column)
	{
		super(context);
		init(context, raplaEvent);
		eventLayout = createEventLayout(raplaEvent, column);
	}

	private void init(Context context, RaplaEvent raplaEvent)
	{
		bgView = new ImageView(context);
		textView = new TextView(context);

		bgView.setImageResource(R.drawable.event);
		textView.setText(getEventName(raplaEvent));

		addView(bgView);
		addView(textView);
	}

	private String getEventName(RaplaEvent raplaEvent)
	{
		String eventName = raplaEvent.getTitle();
		int separatorIndex = eventName.indexOf("[");

		if (separatorIndex > 0)
			return eventName.substring(0, separatorIndex);
		else
			return eventName;
	}

	private RapplaGridElementLayout createEventLayout(RaplaEvent raplaEvent, int column)
	{
		int eventLength = (int) (raplaEvent.getDurationInMinutes() / CalendarFragment.timeInterval);
		int eventOffset = (int) (raplaEvent.getTimeDifferenceInMinutes(CalendarFragment.getEarliestStart(raplaEvent.getDate())) / CalendarFragment.timeInterval);

		// Sorgt dafür, dass kein Event länger als der Tag werden kann
		// TODO: Das geht noch schöner
		eventLength = (int) Math.min(CalendarFragment.getDayDuration() / CalendarFragment.timeInterval - eventOffset, eventLength);

		return new RapplaGridElementLayout(column, eventOffset, eventLength);
	}

	public RapplaGridElementLayout getEventLayout()
	{
		return eventLayout;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
	}

	public static Typeface getFont(Context context)
	{
		if (font == null)
			font = Typeface.createFromAsset(context.getAssets(), "fonts/Graziano.ttf");
		return font;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		bgView.layout(l, t, r, b);
		textView.layout(l, t, r, b);
	}
}
