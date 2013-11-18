package app.rappla;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DayFragment extends RapplaFragment 
{
	public DayFragment()
	{
		super();
		setTitle("Tag");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.daylayout, container,
				false);
		return rootView;
	}

}