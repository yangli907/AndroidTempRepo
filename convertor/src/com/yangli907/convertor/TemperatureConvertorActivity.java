package com.yangli907.convertor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class TemperatureConvertorActivity extends Activity {
    /** Called when the activity is first created. */
	private EditText text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text = (EditText)findViewById(R.id.editText1);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(Menu.NONE, Menu.FIRST + 1 , 2, "save...");
    	menu.add(Menu.NONE, Menu.FIRST + 2 , 5, "delete...");    	
    	//TODO Auto-generated method stub
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    		case Menu.FIRST + 1:
    			Toast.makeText(this, "You click to save...", Toast.LENGTH_SHORT);
    			break;
    		case Menu.FIRST + 2:
    			text.setText("");
    			break;
    	}
    			// TODO Auto-generated method stub
    	return super.onOptionsItemSelected(item);
    }
    
    public void myClickHandler(View view){
    	switch(view.getId()){
    	case R.id.button1:
    		RadioButton celsiusButton = (RadioButton) findViewById(R.id.radio0);
    		RadioButton fahrenheitRadioButton = (RadioButton) findViewById(R.id.radio1);
    		if(text.getText().length() == 0){
    			Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_LONG).show();
    			return;
    		}
    		float inputValue = Float.parseFloat(text.getText().toString());
			if (celsiusButton.isChecked()) {
				text.setText(String
						.valueOf(convertFahrenheitToCelsius(inputValue)));
				celsiusButton.setChecked(false);
				fahrenheitRadioButton.setChecked(true);
			} else {
				text.setText(String
						.valueOf(convertCelsiusToFahrenheit(inputValue)));
				fahrenheitRadioButton.setChecked(false);
				celsiusButton.setChecked(true);
			}
			break;
		}
	}

    public void onClickSound(){
    	if(text.getText().length() ==0)
    	{
    		Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_LONG).show();
    	}
    		
    }
    
	// Converts to celsius
	private float convertFahrenheitToCelsius(float fahrenheit) {
		return ((fahrenheit - 32) * 5 / 9);
	}

	// Converts to fahrenheit
	private float convertCelsiusToFahrenheit(float celsius) {
		return ((celsius * 9) / 5) + 32;
	}
}