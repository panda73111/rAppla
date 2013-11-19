package app.rappla.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.rappla.R;
import app.rappla.StaticContext;

public class TrainFragment extends RapplaFragment 
{
	public TrainFragment()
	{
		setTitle(StaticContext.getContext().getResources().getString(R.string.train));
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.trainlayout, container,
				false);
		return rootView;
	}

}