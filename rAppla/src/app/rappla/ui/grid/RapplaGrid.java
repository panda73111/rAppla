package app.rappla.ui.grid;

import java.util.HashMap;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import app.rappla.R;

public class RapplaGrid extends ViewGroup
{
	public final static String elementPrefix = "EP#";
	public final static String coordinateSeperator = "#";

	private int columnCount, rowCount;
	private float columnWidth, rowHeight;

	
	View[][] elementMatrix;

	public RapplaGrid(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RapplaGrid, 0, 0);

		try
		{
			columnCount = a.getInteger(R.styleable.RapplaGrid_columnCount, 1);
			rowCount = a.getInteger(R.styleable.RapplaGrid_rowCount, 1);
		} finally
		{
			a.recycle();
		}

		elementMatrix = new View[columnCount][rowCount];

		Log.d("RapplaGrid", "new: " + rowCount + " rows, " + columnCount + " columns");
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
		Log.d("RapplaGrid", "refreshGridSize: " + rowCount + " rows, " + columnCount + " columns, " + rowHeight + "px x" + columnWidth + "px");
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		refreshGridSize(w, h);
	}

	public View getElementAt(int col, int row)
	{
		return elementMatrix[col][row];
	}

	public boolean addElement(RapplaGridElement element)
	{
		RapplaGridElementLayout layout = element.getEventLayout();
		return addElementAt(element.getEventButton(), layout.column, layout.offset, layout.rowSpan);
	}

	public boolean addElementAt(View element, int col, int row)
	{
		return addElementAt(element, col, row, 0);
	}

	public boolean addElementAt(View element, int col, int row, int rowSpan)
	{
		Log.d("RapplaGrid", "adding element at column " + col + ", row " + row);
		elementMatrix[col][row] = element;

		if (rowSpan < 1)
			rowSpan = 1; // Rowspan auf Minimum 1 setzen
							// TODO: auf Maximum achten

		addView(element);
		return true;
	}

	private void applyLayout(View view, int col, int row, int rowSpan)
	{
		

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

		Log.d("RapplaGrid", "applied layout to " + view.getTag() + ": " + col * newWidth + "|" + row * newHeight + ", " + newWidth + "px x "
				+ newHeight + "px");
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		for (int row = 0; row < rowCount; row++)
			for (int col = 0; col < columnCount; col++)
			{
				View element = getElementAt(col, row);
				if (element == null)
					continue;
				
				int elemWidth = (int) columnWidth;
				int elemHeight = (int) (rowHeight * rowSpan);
				
				Log.d("RapplaGrid", "placing element of column " + col + ", row " + row + " at " + element.getLeft() + "|" + element.getTop() + "|"
						+ element.getRight() + "|" + element.getBottom());
				element.layout(element.getLeft(), element.getTop(), element.getRight(), element.getBottom());
			}
	}
}
