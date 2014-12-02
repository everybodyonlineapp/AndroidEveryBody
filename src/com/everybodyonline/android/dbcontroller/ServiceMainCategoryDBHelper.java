package com.everybodyonline.android.dbcontroller;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import com.everybodyonline.android.model.City;
import com.everybodyonline.android.model.Profile;
import com.everybodyonline.android.model.ServiceMainCategory;
import com.everybodyonline.android.util.CityConstants;
import com.everybodyonline.android.util.Constants;
import com.everybodyonline.android.util.DateAndTime;
import com.parse.ParseObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class ServiceMainCategoryDBHelper extends SQLiteHelperClass {

	private static final String TAG = ServiceMainCategoryDBHelper.class.getName();
	public static final String DB_NAME = Constants.DB_NAME;
	public static final int DATABASE_VERSION = 2;
	public static final String TABLE_NAME = "ServiceMainCategory";
	public static final String ID = "_id";
	public static final String CATEGORY_ID = "city_id";
	public static final String NAME = "name";
	public static final String UPDATED_AT = "updatedAt";
	public static final String STATUS = "status";

	/*
	 * public static final String TRIP_COUNT = "trip_count"; public static final
	 * String COMPLETED_TRIP_COUNT = "completed_trip_count"; public static final
	 * String BUDDY_COUNT = "buddy_count"; public static final String CLUB_COUNT
	 * = "club_count"; public static final String ON_THE_ROAD_FOR =
	 * "on_the_road_for"; public static final String COVER_PICTURE_ID =
	 * "cover_picture_id"; public static final String BADGE_RIDER =
	 * "badge_rider"; public static final String BADGE_SHARER = "badge_sharer";
	 * public static final String KEY_SEARCH = "Searchfield";
	 */
	/*
	 * public static final String SCRIPT_CREATE_TABLE = "CREATE VIRTUAL TABLE "
	 * 
	 * + TABLE_NAME + " USING fts3" + "(" + ID + " TEXT PRIMARY KEY, " + NAME +
	 * " TEXT, " + CATEGORY_ID + " TEXT, " + UPDATED_AT + " INTEGER, " + STATUS +
	 * " TEXT" + " UNIQUE (" + NAME + "," + CATEGORY_ID +
	 * ") ON CONFLICT REPLACE );";
	 */

	public static final String SCRIPT_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME
			+ " ("
			+ ID
			+ " TEXT PRIMARY KEY,"
			+ NAME
			+ " TEXT,"
			+ CATEGORY_ID
			+ " TEXT,"
			+ UPDATED_AT
			+ " INTEGER,"
			+ STATUS
			+ " INTEGER"
			+ ")";

	public ServiceMainCategoryDBHelper(Context context) {
		super(context, DB_NAME, null, Constants.DATABASE_VERSION_2);
	}

	/*@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(SCRIPT_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}*/

	public long insertOrUpdate(ParseObject city) {
		// deleteProfile(profile);

		ContentValues values = new ContentValues();
		values.put(CATEGORY_ID, city.getObjectId());
		values.put(NAME, city.getString(CityConstants.Name));
		values.put(STATUS, (city.getBoolean(CityConstants.Status))?1:0);
		
		values.put(UPDATED_AT, DateAndTime.getLongFromDate(city.getUpdatedAt()));

		long rowId = 0;
		boolean ifcityexist = checkCityExist(city.getObjectId());

		SQLiteDatabase database = getWritableDatabase();
		if (!ifcityexist)
			rowId = database.insert(TABLE_NAME, null, values);
		else {
			String selection = CATEGORY_ID + "='" + city.getObjectId() + "'";
			database.update(TABLE_NAME, values, selection, null);
		}
		database.close();
		return rowId;
	}

	public long deleteProfile(ParseObject city) {
		SQLiteDatabase database = getWritableDatabase();
		String selection = CATEGORY_ID + "='" + city.getObjectId() + "'";
		long rowId = 0;
		rowId = database.delete(TABLE_NAME, selection, null);
		database.close();
		return rowId;
	}

	public Date getMaxUpdatedTime() {
		// deleteProfile(profile);

		SQLiteDatabase database = getReadableDatabase();
		long maxData = 0;
		String selection = "SELECT MAX (" + UPDATED_AT + ") FROM " + TABLE_NAME;
		final SQLiteStatement stmt = database.compileStatement(selection);
		maxData = (long) stmt.simpleQueryForLong();
		database.close();
		Date returndate = DateAndTime.getDateFromLong(maxData);
		// Log.d(TAG, "getMaxColumnData maxData value : " + maxData);
		return returndate;
	}

	/*
	 * boolean isProfileExist (SQLiteDatabase database,String profileId) {
	 * String selection = CATEGORY_ID + "='" + profileId + "'"; Cursor cursor =
	 * database.query(TABLE_NAME, null, selection, null, null, null, null);
	 * if(cursor.getCount()>0) { cursor.close(); return true; }
	 * 
	 * return false; }
	 */
	public int deleteAll() {
		SQLiteDatabase database = getWritableDatabase();
		int deletedRowCount = database.delete(TABLE_NAME, null, null);
		database.close();
		return deletedRowCount;
	}

	public ServiceMainCategory getCategory(String cityId) {

		ServiceMainCategory serviceMainCategory = null;
		String selection = CATEGORY_ID + "='" + cityId + "'";
		SQLiteDatabase database = getReadableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, selection, null, null,
				null, null);
		if (cursor != null && cursor.moveToFirst()) {
			serviceMainCategory = new ServiceMainCategory();

			serviceMainCategory.setUpdatedAt(cursor.getInt(cursor
					.getColumnIndexOrThrow(UPDATED_AT)));

			serviceMainCategory.setCategoryId(cursor.getString(cursor
					.getColumnIndexOrThrow(CATEGORY_ID)));

			serviceMainCategory.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));

			serviceMainCategory.setStatus(cursor.getInt(cursor
					.getColumnIndexOrThrow(STATUS)));

			cursor.close();
		}
		database.close();
		return serviceMainCategory;
	}

	public boolean checkCityExist(String cityId) {

		boolean ifexist = false;
		String selection = CATEGORY_ID + "='" + cityId + "'";
		SQLiteDatabase database = getReadableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, selection, null, null,
				null, null);
		if (cursor != null && cursor.moveToFirst()) {
			if (cursor.getCount() > 0)
				ifexist = true;

			cursor.close();
		}
		database.close();
		return ifexist;
	}

	/*
	 * public int delete(String profileId) { SQLiteDatabase database =
	 * getWritableDatabase(); String selection = ID + "='" + profileId + "'";
	 * return database.delete(TABLE_NAME, selection, null); }
	 */

	public ArrayList<ServiceMainCategory> getCategories() {

		ArrayList<ServiceMainCategory> mainCaregoryList = new ArrayList<ServiceMainCategory>();
		ServiceMainCategory serviceMainCategory;
		// String selection = CATEGORY_ID + "='" + profileId + "'";
		SQLiteDatabase database = getReadableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, null, null, null,
				null, null);
		if (cursor != null && cursor.moveToFirst()) {

			for (int i = 0; i < cursor.getCount(); i++) {
				cursor.moveToPosition(i);
				serviceMainCategory = new ServiceMainCategory();

				serviceMainCategory.setCategoryId(cursor.getString(cursor
						.getColumnIndexOrThrow(CATEGORY_ID)));

				serviceMainCategory.setName(cursor.getString(cursor
						.getColumnIndexOrThrow(NAME)));

				serviceMainCategory.setUpdatedAt(cursor.getInt(cursor
						.getColumnIndexOrThrow(UPDATED_AT)));
				serviceMainCategory.setStatus(cursor.getInt(cursor
						.getColumnIndexOrThrow(STATUS)));
				mainCaregoryList.add(serviceMainCategory);
			}

			cursor.close();
		}
		database.close();
		return mainCaregoryList;
	}

	public long deleteProfileByProfileId(String profileId) {
		SQLiteDatabase database = getWritableDatabase();
		String selection = CATEGORY_ID + "='" + profileId + "'";
		long rowId = 0;
		rowId = database.delete(TABLE_NAME, selection, null);
		database.close();
		return rowId;
	}
}
