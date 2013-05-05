package com.yangli907.validateinput;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	private EditText et1;
	private EditText et2;
	private EditText et3;
	private static final String DATABASE_NAME = "notepad.db";
    private static final int DATABASE_VERSION = 2;
    private static final String NOTES_TABLE_NAME = "notes";
    
    private static class DBHelper extends SQLiteOpenHelper{
    	DBHelper(Context context){
    		super(context,DATABASE_NAME,null, DATABASE_VERSION);
    	}

		@Override
		public void onCreate(SQLiteDatabase db) {
			 db.execSQL("CREATE TABLE " + NOTES_TABLE_NAME + " ("
	                    + "id" + " INTEGER PRIMARY KEY,"
	                    + "value1" + " TEXT,"
	                    + "value2" + " TEXT,"
	                    + "result" + " TEXT,"
	                    + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS "+ NOTES_TABLE_NAME);
			onCreate(db);
		}
    }
    
    private DBHelper mDbHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText)findViewById(R.id.editText1);
        et2 = (EditText)findViewById(R.id.editText2);
        et3 = (EditText)findViewById(R.id.editText3);
        mDbHelper = new DBHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
    }

    public void calculate(View view){
    	double value1 =Double.valueOf(et1.getText().toString());
    	double value2 =Double.valueOf(et2.getText().toString());
    	et3.setText(String.valueOf(value1+value2));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
