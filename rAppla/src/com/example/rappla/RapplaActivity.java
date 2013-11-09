package com.example.rappla;

import kernel.Calculator;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;

public class RapplaActivity extends Activity implements OnClickListener{
    Calculator calculator;
	
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
        
        // Add button Listeners
        Button button = (Button)findViewById(R.id.button1);
        /*button.setOnClickListener(this);*/
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
	@Override
	public void onClick(View arg0) 
	{
		Button button = (Button)arg0;
		button.setBackgroundColor(Color.RED);
	}
}