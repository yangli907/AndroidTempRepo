package com.yangli907.dataProvider.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.yangli907.dataProvider.DataProviderActivity;
import com.yangli907.dataProvider.R;

public class DataProviderValidation extends ActivityInstrumentationTestCase2<DataProviderActivity> {
	private EditText et;
	public DataProviderValidation(String name) {
		
		super("com.yangli907.dataProvider",DataProviderActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		DataProviderActivity dataProviderActivity = getActivity();
		et = (EditText)dataProviderActivity.findViewById(R.id.editText1);
	}
	
	public void testInput(){
		sendKeys("2 4 ENTER");
		assertEquals("24", et.getText().toString());
	}

}
