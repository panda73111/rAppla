package app.rappla.ui.grid;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import app.rappla.R;

public class RapplaGrid extends ViewGroup
{
	public final static String elementPrefix = "EP#";
	public final static String coordinateSeperator = "#";

	private int columnCount, rowCount;
	private float columnWidth, rowHeight;

	private HashMap<View, RapplaGridElementLayout> allElementLayouts;
	private ArrayList<View> nonSpaceViews;

	public RapplaGrid(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		allElementLayouts = new HashMap<View, RapplaGridElementLayout>();
		nonSpaceViews = new ArrayList<View>();

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RapplaGrid, 0, 0);

		try
		{
			columnCount = a.getInteger(R.styleable.RapplaGrid_numOfColumns, 1);
			rowCount = a.getInteger(R.styleable.RapplaGrid_numOfRows, 1);
		} finally
		{
			a.recycle();
		}

	}

	public int getColumnCount()
	{
		return columnCount;
	}

	public int getRowCount()
	{
		return rowCount;
	}

	private void refreshGridSize(int newWidth, int newHeight)
	{
		columnWidth = newWidth / columnCount;
		rowHeight = newHeight / rowCount;
		Log.d("RapplaGrid", "refreshGridSize: " + columnWidth + "|" + rowHeight);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);

		Log.d("Grid", "Size changed: width " + oldw + " -> " + w + "; height " + oldh + " -> " + h);

		refreshGridSize(w, h);

		for (int i = 0; i < getChildCount(); i++)
		{
			View view = getChildAt(i);
			applyLayout(view, allElementLayouts.get(view));
		}
	}

	public View getElementAt(int col, int row)
	{
		return findViewWithTag(elementPrefix + col + coordinateSeperator + row);
	}

	public boolean addElement(RapplaGridElement element)
	{
		RapplaGridElementLayout layout = element.getEventLayout();
		return addElementAt(element.getEventButton(), layout.xCoordinate, layout.yCoordinate, layout.rowSpan);
	}

	public boolean addElementAt(View element, int col, int row)
	{
		return addElementAt(element, col, row, 0);
	}

	public boolean addElementAt(View element, int col, int row, int rowSpan)
	{
		if (allElementLayouts.containsKey(element)) // Sollte das Element schon
			return false; // hinzugefügt worden sein

		if (rowSpan < 1)
			rowSpan = 1; // Rowspan auf Minimum 1 setzen
							// TODO: auf Maximum achten

		if (!(element instanceof Space))
			nonSpaceViews.add(element);

		RapplaGridElementLayout rapplaLayout = new RapplaGridElementLayout(col, row, rowSpan);
		allElementLayouts.put(element, rapplaLayout);
		applyLayout(element, rapplaLayout);

		addView(element);
		return true;
	}

	private void applyLayout(View view, RapplaGridElementLayout layout)
	{
		if (layout == null)
			return;
		applyLayout(view, layout.xCoordinate, layout.yCoordinate, layout.rowSpan);
	}

	private void applyLayout(View view, int col, int row, int rowSpan)
	{
		int newWidth = (int) columnWidth;
		int newHeight = (int) (rowHeight * rowSpan);

		// Try to get old Layout
		ViewGroup.LayoutParams gParams = (LayoutParams) view.getLayoutParams();
		// If failed, create a new one
		if (gParams == null)
			gParams = new ViewGroup.LayoutParams(newWidth, newHeight);
		else
		{
			gParams.width = newWidth;
			gParams.height = newHeight;
		}

		view.setLayoutParams(gParams);
		view.setX(col * newWidth);
		view.setY(row * newHeight);
	}

	private void refreshLayout(View view)
	{
		RapplaGridElementLayout layout = allElementLayouts.get(view);
		applyLayout(view, layout);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		refreshGridSize(getWidth(), getHeight());

		for (int row = 0; row < rowCount; row++)
			for (int col = 0; col < columnCount; col++)
			{
				Log.d("RapplaGrid", "onLayout: " + col + "|" + row);
				View element = getElementAt(col, row);
				if (element != null)
					refreshLayout(element);
			}
	}
}
