package app.rappla.ui.grid;

public class RapplaGridElementLayout
{
	int column;
	int offset;
	int rowSpan;

	public RapplaGridElementLayout(int column, int offset, int rowSpan)
	{
		this.column = column;
		this.offset = offset;
		this.rowSpan = rowSpan;
	}
}
