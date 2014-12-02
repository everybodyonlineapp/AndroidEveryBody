package com.everybodyonline.android.dbcontroller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelperClass extends SQLiteOpenHelper {

	public SQLiteHelperClass(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(ProfileDBHelper.SCRIPT_CREATE_TABLE);//
		db.execSQL(CityDBHelper.SCRIPT_CREATE_TABLE);
		db.execSQL(ServiceMainCategoryDBHelper.SCRIPT_CREATE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// db.execSQL(CouponCodeDBHelper.SCRIPT_CREATE_TABLE);
	}
}