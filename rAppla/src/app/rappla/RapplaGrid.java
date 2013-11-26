package app.rappla;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class RapplaGrid extends LinearLayout
{
	public final static String colPrefix = "CP#";
	public final static String elementPrefix = "EP#";
	
	public RapplaGrid(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.RapplaGrid);
		
		String colCountString = a.getString(R.styleable.RapplaGrid_numOfColumns);
		String rowCountString = a.getString(R.styleable.RapplaGrid_numOfRows);	
		
		a.recycle();
		
		if(colCountString==null)
			colCountString="1";
		if(rowCountString==null)
			rowCountString="1";

		int colCount = Math.max(Integer.valueOf(colCountString), 1);
		int rowCount = Math.max(Integer.valueOf(rowCountString), 1);
		addGrid(context, colCount, rowCount);
	}
	private void addGrid(Context context, int colCount, int rowCount)
	{
		LinearLayout.LayoutParams dayParams = new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		
		for (int i = 0; i < colCount; i++)
		{
			GridLayout dayGrid = new GridLayout(context);

			dayGrid.setLayoutParams(dayParams);

			dayGrid.setColumnCount(1);
			dayGrid.setRowCount(rowCount);
			dayGrid.setUseDefaultMargins(false);
			dayGrid.setClipToPadding(true);
			dayGrid.setTag(colPrefix + i);
			this.addView(dayGrid, i);
		}
	}
	
	public GridLayout getColumn(int column)
	{
		return (GridLayout)findViewWithTag(colPrefix + column);
	}
	public View getElementAt(int x, int y)
	{
		GridLayout column = getColumn(x);
		
		if(column==null)
			return null;
		
		return column.findViewWithTag(elementPrefix + y);
	}
	
	public boolean addElementAt(View element, int x, int y, int rowSpan)
	{
		GridLayout column = getColumn(x);

		if(column==null)
			return false;
		
		// Apply rowSpan
		if(rowSpan>1)
		{
			GridLayout.LayoutParams gParams = new GridLayout.LayoutParams(element.getLayoutParams());
			gParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, rowSpan);
			gParams.setGravity(Gravity.FILL_VERTICAL);
			gParams.
			element.setLayoutParams(gParams);
		}
		
		column.addView(element, y);
		return true;
	}
	
}
