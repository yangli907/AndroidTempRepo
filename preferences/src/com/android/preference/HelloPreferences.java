package com.android.preference;

import java.util.prefs.Preferences;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HelloPreferences extends Activity {
    /** Called when the activity is first created. */
	SharedPreferences preferences;
	@Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button button = (Button) findViewById(R.id.button1);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username = preferences.getString("username", "n/a");
				String password = preferences.getString("password","n/a");
				Toast.makeText(HelloPreferences.this,username+" "+password,Toast.LENGTH_LONG).show();
			}
		});
        
		Button buttonChangePreferences = (Button) findViewById(R.id.button2);
		buttonChangePreferences.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Editor edit = preferences.edit();
				String username = preferences.getString("username", "n/a");
				// We will just revert the current user name and save again
				StringBuffer buffer = new StringBuffer();
				for (int i = username.length() - 1; i >= 0; i--) {
					buffer.append(username.charAt(i));
				}
				edit.putString("username", buffer.toString());
				edit.commit();
				// A toast is a view containing a quick little message for the
				// user. We give a little feedback
				Toast.makeText(HelloPreferences.this,
						"Reverted string sequence of user name.",
						Toast.LENGTH_LONG).show();
			}
		});
    }
    
    public boolean onCreateOptionsMenu(Menu menu){
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    
	// This method is called once the menu is selected
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// We have only one menu option
		case R.id.preferences:
			// Launch Preference activity
			Intent i = new Intent(HelloPreferences.this, Preference.class);
			startActivity(i);
			break;

		}
		return true;
	}

}