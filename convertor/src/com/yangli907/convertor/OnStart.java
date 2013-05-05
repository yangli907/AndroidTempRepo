package com.yangli907.convertor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OnStart extends Activity{
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.index);
	 }
	 
	 public void TempConv(View view){
		 Intent intent = new Intent(OnStart.this,TemperatureConvertorActivity.class);
		 startActivity(intent);
	 }
	 
	 public void OnTipsCalc(View view){
		 Intent intent = new Intent(OnStart.this,TipsCalc.class);
		 startActivity(intent);
	 }
	 
	 public void OnDrawWord(View view){
		 Intent intent = new Intent(OnStart.this,DrawWord.class);
		 startActivity(intent);
	 }
}
