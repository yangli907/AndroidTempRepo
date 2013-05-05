package com.yangli907.convertor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper {
	private static String DB_PATH = "/data/data/com.temperature/databases/";
	private static String DB_Name = "checkin.db";
	private SQLiteDatabase myDataBase;
	private final Context myContext;
	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.myContext = context;
		// TODO Auto-generated constructor stub
	}

	public void createDataBase() throws IOException{
		if(checkDataBase())
		{}
		else 
			copyDataBase();
	}
	private void copyDataBase() throws IOException {
		InputStream data = myContext.getAssets().open(DB_Name);
		String outFileName = DB_PATH+DB_Name;
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = data.read(buffer))>0){
			myOutput.write(buffer,0,length);
		}		
    	myOutput.flush();
    	myOutput.close();
    	data.close();
	}
	
	private boolean checkDataBase(){
		SQLiteDatabase checkDB = null;
		try{
			checkDB = SQLiteDatabase.openDatabase(DB_PATH+DB_Name, null, SQLiteDatabase.OPEN_READWRITE);
		}
		catch(SQLException e){
		}
		if(checkDB!=null)
			checkDB.close();
		return checkDB == null? false : true;
	}
	public void openDataBase(){
		String mypath = DB_PATH+DB_Name;
		myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
	}
	
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
