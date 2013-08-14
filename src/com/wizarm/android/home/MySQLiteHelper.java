package com.wizarm.android.home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class MySQLiteHelper extends SQLiteOpenHelper {

private static final String DATABASE_NAME = "wizarmtv.db";
private static final String TAG_FAVORITES = "favorites";
private static final String TAG = "__FDK__";
private static final int DATABASE_VERSION = 1;

private final Context mContext;

MySQLiteHelper(Context context){
super(context, DATABASE_NAME, null, DATABASE_VERSION);
mContext = context;

}


 @Override
 public void onCreate(SQLiteDatabase db) {
             Log.d(TAG, "creating new launcher database");

         //   mMaxId = 1;

            db.execSQL("CREATE TABLE WizButtons (" +
                    "_id INTEGER PRIMARY KEY," +
                    "title TEXT," +
                    "itemType INTEGER," +
                    "isShortcut INTEGER," +
                    "iconType INTEGER," +
                    "iconResource TEXT," +
                    "icon BLOB," +
                    "uri TEXT," +
                    "displayMode INTEGER" +
                    ");");

	}


@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// TODO Auto-generated method stub
	db.execSQL("DROP TABLE IF EXIST WizButtons");
	onCreate(db);
	
}









}

