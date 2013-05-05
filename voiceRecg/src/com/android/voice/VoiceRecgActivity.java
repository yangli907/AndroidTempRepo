package com.android.voice;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VoiceRecgActivity extends Activity {
    private static final int REQUEST_CODE = 1234;
    private ListView lv = null;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        lv = (ListView)findViewById(R.id.wordList);
    }
    
    public void startTalking(View view){
    	Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    	intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "You told me...");
    	startActivityForResult(intent, REQUEST_CODE);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
    	{
    		ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
    		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,matches));
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
}