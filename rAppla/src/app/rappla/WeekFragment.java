package app.rappla;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class WeekFragment extends RapplaFragment implements OnClickListener{

    Calculator calculator;
    EditText cFaktor1;
    EditText cFaktor2;
    Spinner cOperation;
    Button cCalcButton;
    TextView cAnsText;


	public WeekFragment()
	{
		setTitle("Woche");
	}
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weeklayout, container, false);
		return rootView;
	}
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		calculator = new Calculator();
	}
    public void onStart()
    {
    	super.onStart();
        cFaktor1	= (EditText)getView().findViewById(R.id.editText1);
        cFaktor2	= (EditText)getView().findViewById(R.id.editText2);
        cOperation	= (Spinner)getView().findViewById(R.id.spinner1);
        cCalcButton	= (Button)getView().findViewById(R.id.button1);
        cAnsText	= (TextView)getView().findViewById(R.id.textView1);
        cCalcButton.setOnClickListener(this);
    }
    public void onCalcButtonPressed(View view)
    {
    	String fak1 		= cFaktor1.getText().toString();
    	String fak2 		= cFaktor2.getText().toString();
    	String operation 	= cOperation.getSelectedItem().toString();

    	Operation op = null;
    	if(operation.equals("+"))
    		op = Operation.PLUS;
    	else if(operation.equals("-"))
    		op = Operation.MINUS;
    	else if(operation.equals("*"))
    		op = Operation.MAL;
    	else if(operation.equals("/"))
    		op = Operation.GETEILT;
    	
    	double result = calculator.getResult(Double.valueOf(fak1), Double.valueOf(fak2), op);
    	
    	cAnsText.setText("" + result);
    }
    public void onClick(View v)
    {
    	switch(v.getId())
    	{
    	case R.id.button1:
    		onCalcButtonPressed(v);
    		break;
    	default:	
    	}
    }

}