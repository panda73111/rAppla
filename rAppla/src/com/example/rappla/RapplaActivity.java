package com.example.rappla;

import kernel.Calculator;
import kernel.Operation;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;

public class RapplaActivity extends Activity {
    Calculator calculator;

    EditText cFaktor1;
    EditText cFaktor2;
    Spinner cOperation;
    Button cCalcButton;
    TextView cAnsText;
    
	Tab tab1;
    Tab tab2;
    Tab tab3;
    
    Fragment fragmentTab1 = new FragmentTab1();
    Fragment fragmentTab2 = new FragmentTab2();
    Fragment fragmentTab3 = new FragmentTab3();
 
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculator = new Calculator();
        
        setContentView(R.layout.activity_rappla);
 
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        // Das ist ein zurück-Button. Ist in den einzelansichten nützlich
        actionBar.setDisplayHomeAsUpEnabled(false);
        //actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        tab1 = actionBar.newTab().setText(FragmentTab1.title);
        tab2 = actionBar.newTab().setText(FragmentTab2.title);
        tab3 = actionBar.newTab().setText(FragmentTab3.title);
        
 
        // Set Tab Listeners
        tab1.setTabListener(new TabListener(fragmentTab1));
        tab2.setTabListener(new TabListener(fragmentTab2));
        tab3.setTabListener(new TabListener(fragmentTab3));
 
        // Add tabs to actionbar
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
        
        
    }
    
    protected void onStart()
    {
    	super.onStart();
        cFaktor1	= (EditText)findViewById(R.id.editText1);
        cFaktor2	= (EditText)findViewById(R.id.editText2);
        cOperation	= (Spinner)findViewById(R.id.spinner1);
        cCalcButton	= (Button)findViewById(R.id.button1);
        cAnsText	= (TextView)findViewById(R.id.textView1);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rappla_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId())
    	{
    	case R.id.action_settings:
    		return true;
    	default:
        	return super.onOptionsItemSelected(item);
    	}
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
}