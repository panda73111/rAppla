package app.rappla;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TrainFragment extends RapplaFragment 
{
	public TrainFragment()
	{
		setTitle("Bahn");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.trainlayout, container,
				false);
		return rootView;
	}

}