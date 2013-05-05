package com.yangli907.convertor;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TipsCalc extends Activity {
	private EditText amountField;
	private EditText taxField;
	private Spinner tipsField;
	private EditText beforetaxField;
	private EditText totalamountField;
	private ArrayList<String> list = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private double tipsRate = 0.0;
	private static double DEFAULT_TIPS = 0.15;
	private String[] entries;
	DataBaseHelper myDbHelper = null;
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tipscalc);
/*        myDbHelper = new DataBaseHelper(this, "checkin.db", null, 2);
        try{
        	myDbHelper.createDataBase();
        }
        catch(IOException ioe){
        	throw new Error("cannot create database");
        }*/
        taxField = (EditText)findViewById(R.id.EditText02);
        amountField = (EditText)findViewById(R.id.editText2);
        tipsField = (Spinner)findViewById(R.id.spinner1);
        beforetaxField = (EditText)findViewById(R.id.editText3);
        totalamountField = (EditText)findViewById(R.id.EditText01);
        entries = getResources().getStringArray(R.array.action);
        for(int i=0; i<entries.length; i++){
        	list.add(entries[i]);
        }
       adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       tipsField.setAdapter(adapter);
		tipsField.setOnItemSelectedListener(
	    		   new OnItemSelectedListener() { 

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						tipsRate = DEFAULT_TIPS;
						setValue();  
					}

					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						tipsRate = Double.valueOf(entries[arg2].substring(0, 2))/100.0;
						setValue();
						// TODO Auto-generated method stub
						
					}
	    		}
	    		   
	       );
	
    }
	public void onCalc(View view){
		setValue();
	}
	
	public void setValue(){
		double taxRate = 0.0;
		double afterTaxAmount = 0.0;
		try{
			taxRate = Double.valueOf(taxField.getText().toString())/100.0;
			afterTaxAmount = Double.valueOf(amountField.getText().toString());
		}
		catch(NumberFormatException nfe)
		{
			Toast.makeText(this, "the input is invalid, plase input again.",Toast.LENGTH_SHORT);
			taxField.setText("6");
			amountField.setText("0.0");
			taxField.requestFocus();
		}
		double beforeTaxAmount = afterTaxAmount/(1.0+taxRate);
		double tipsAmount = beforeTaxAmount*tipsRate;
		double totalAmount = afterTaxAmount+tipsAmount;
		BigDecimal bg_tips = new BigDecimal(tipsAmount);
		beforetaxField.setText(String.valueOf(String.valueOf(bg_tips.setScale(2, java.math.BigDecimal.ROUND_HALF_UP))));
		BigDecimal bg_total = new BigDecimal(totalAmount);
		totalamountField.setText(String.valueOf(bg_total.setScale(2, java.math.BigDecimal.ROUND_HALF_UP)));
		/*		try
        {
        	if(totalamountField.getText().length()>0)
        		myDbHelper.openDataBase();
        	SQLiteDatabase db =myDbHelper.getReadableDatabase();
        	String query = "insert into tips values(?,?,?);";
        	DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT, Locale.US);
        	String datevalue = df.format(new java.util.Date());
        	db.execSQL(query, new Object[]{null,totalamountField.getText(),datevalue});       	
        	
        	ContentValues initialValues = new ContentValues(); 
        	initialValues.put("locale", tv.getText().toString());
        	long rowId = db.insert("checkin","",initialValues);
        	long copy = rowId;
        }
		catch(Exception e){
			String s = e.getMessage();
			s.charAt(0);
		}
		*/
	}
	public void setZero(View view){
		amountField.setText("");
	}
}
