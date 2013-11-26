package app.rappla;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

public class RapplaGrid extends GridLayout
{
	public final static String elementPrefix = "EP#";
	public final static String coordinateSeperator = "#";

	public RapplaGrid(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		int childcount = getChildCount();
		ViewGroup.LayoutParams lParam;
		for (int i = 0; i < childcount; i++)
		{
			View v = getChildAt(i);
			lParam = v.getLayoutParams();
			lParam.width = w / getColumnCount();
			lParam.height = w / getRowCount();
			v.setLayoutParams(lParam);
		}
	}

	public View getElementAt(int x, int y)
	{
		return findViewWithTag(elementPrefix + x + coordinateSeperator + y);
	}

	public boolean addElementAt(View element, int x, int y, int rowSpan)
	{

		// Apply rowSpan

		GridLayout.LayoutParams gParams = new GridLayout.LayoutParams();
		gParams.setGravity(Gravity.FILL_VERTICAL);

		gParams.width = getWidth() / getColumnCount();
		gParams.height = getHeight() / getRowCount();
		gParams.rowSpec = GridLayout.spec(y, rowSpan + 1);
		gParams.columnSpec = GridLayout.spec(x, 1);

		element.setLayoutParams(gParams);

		addView(element);
		return true;
	}
}
