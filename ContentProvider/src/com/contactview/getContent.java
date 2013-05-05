package com.contactview;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

public class getContent extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView contactView = (TextView) findViewById(R.id.contactView);
        Cursor cursor = getContents();
        while(cursor.moveToNext()){
        	String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
        	contactView.append(displayName);
        }
    }
    
    public Cursor getContents() {
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
		String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + "='"+("1")+"'";
		return managedQuery(uri, projection, selection, null, null);
		// TODO Auto-generated constructor stub
	}
}