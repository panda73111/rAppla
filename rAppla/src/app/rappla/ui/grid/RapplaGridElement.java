package app.rappla.ui.grid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import app.rappla.R;
import app.rappla.calendar.RaplaEvent;

public class RapplaGridElement extends View
{
	private static final int[] bgIds = { R.drawable.event, R.drawable.event_blue, R.drawable.event_green };
	private static Bitmap[] bgArr = null;
	private static Bitmap pinBmp = null;
	private static int pinWidth, pinHeight;

	private static Typeface font = null;
	private String eventName;
	private Bitmap bgBmp;
	private Matrix bgTransfMatrix;
	private Matrix pinTransfMatrix;
	private int bgWidth, bgHeight;
	private TextPaint textPaint;
	private RapplaGridElementLayout eventLayout;

	public RapplaGridElement(Context context)
	{
		super(context);

		init(context, null);
	}

	public RapplaGridElement(Context context, RaplaEvent raplaEvent, RapplaGridElementLayout layout)
	{
		super(context);

		eventLayout = layout;

		init(context, raplaEvent);
	}

	public RapplaGridElement(Context context, RaplaEvent raplaEvent, int column, int offset, int length)
	{
		this(context, raplaEvent, new RapplaGridElementLayout(column, offset, length));
	}

	public RapplaGridElement(Context context, RaplaEvent raplaEvent, int column)
	{
		this(context, raplaEvent, new RapplaGridElementLayout(column, raplaEvent));
	}

	public void init(Context context, RaplaEvent raplaEvent)
	{
		if (raplaEvent != null)
		{
			eventName = raplaEvent.getEventNameWithoutProfessor();
		}
		// Log.d("event " + eventName, "init");

		if (font == null)
			font = Typeface.createFromAsset(context.getAssets(), "fonts/Graziano.ttf");

		if (pinBmp == null)
		{
			pinBmp = BitmapFactory.decodeResource(getResources(), R.drawable.pin);
			pinWidth = pinBmp.getWidth();
			pinHeight = pinBmp.getHeight();
		}

		if (bgArr == null)
		{
			bgArr = new Bitmap[bgIds.length];
			for (int i = 0; i < bgIds.length; i++)
			{
				bgArr[i] = BitmapFactory.decodeResource(getResources(), bgIds[i]);
			}
		}

		bgBmp = bgArr[(int) (Math.random() * bgArr.length)];
		bgWidth = bgBmp.getWidth();
		bgHeight = bgBmp.getHeight();
		bgTransfMatrix = new Matrix();
		pinTransfMatrix = new Matrix();
		textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(scaleTextForDisplay(context, 14));
		textPaint.setTypeface(font);

		if (raplaEvent != null)
			setOnClickListener(new EventClickListener(raplaEvent.getUniqueEventID(), context));
	}

	private float scaleTextForDisplay(Context context, float rawSize)
	{
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, rawSize, context.getResources().getDisplayMetrics());
	}

	public RapplaGridElementLayout getEventLayout()
	{
		return eventLayout;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);

		// Log.d("event " + eventName, "onSizeChanged");

		bgTransfMatrix.reset();
		// scale the background to the event's size
		bgTransfMatrix.setScale((float) w / bgWidth, (float) h / bgHeight);

		pinTransfMatrix.reset();
		// scale the pin to 0.2 times the event's width
		int maxPinSize = Math.min(w, h);
		float pinWidthScale = 0.2f * maxPinSize / pinWidth;
		float pinHeightScale = 0.2f * maxPinSize / pinHeight;
		pinTransfMatrix.setScale(pinWidthScale, pinHeightScale);
		// center the (scaled) pin
		pinTransfMatrix.postTranslate(w / 2.0f - (pinWidthScale * (pinWidth / 2.0f)), 2.0f);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// Log.d("event " + eventName, "onDraw");

		canvas.drawBitmap(bgBmp, bgTransfMatrix, null);
		drawStringCentered(canvas, eventName, 10, 10, getWidth() - 20, getHeight() - 20);
		canvas.drawBitmap(pinBmp, pinTransfMatrix, null);
	}

	private void drawStringCentered(Canvas canvas, String str, int x, int y, int w, int h)
	{
		StaticLayout mTextLayout = new StaticLayout(str, textPaint, (int) w, Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
		canvas.save();
		canvas.translate(x, y + h / 2 - mTextLayout.getHeight() / 2);
		mTextLayout.draw(canvas);
		canvas.restore();
	}
}
