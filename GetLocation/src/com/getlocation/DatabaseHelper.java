package com.getlocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private SQLiteDatabase db;
	private String createStmt = "";
	private String name = "table1";
	private String id = "id";
	private String field1 = "field1";
	private String field2 = "field2";
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, null, version);

		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		this.createStmt = "Create table";
		this.createStmt += name + "(";
		this.createStmt += id+ ", integer primary key autoincrement not null,";
		this.createStmt +=field1+"text,"+field2 +" text);";
		db.execSQL(this.createStmt);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public void addRow(String rowString1, String rowString2){
		ContentValues values = new ContentValues();
		values.put(field1, rowString1);
		values.put(field2, rowString2);
		try{
			db.insert(name, null, values);
		}
		catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
	}
}
