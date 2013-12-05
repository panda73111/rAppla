package app.rappla.ui.grid;

public class RapplaGridElementLayout
{
	int xCoordinate;
	int yCoordinate;
	int rowSpan;

	public RapplaGridElementLayout(int column, int offset, int length)
	{
		this.xCoordinate 	= column;
		this.yCoordinate 	= offset;
		this.rowSpan 		= length;
	}
}
