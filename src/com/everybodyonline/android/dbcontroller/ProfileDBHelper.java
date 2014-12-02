package com.everybodyonline.android.dbcontroller;

import java.util.ArrayList;

import java.util.List;

import com.everybodyonline.android.model.Profile;
import com.everybodyonline.android.util.Constants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfileDBHelper extends SQLiteHelperClass {

	private static final String TAG = ProfileDBHelper.class.getName();
	public static final String DB_NAME = Constants.DB_NAME;
	public static final int DATABASE_VERSION = 2;
	public static final String TABLE_NAME = "Profile";
	public static final String ID = "_id";
	public static final String PROFILE_ID = "Profile_id";
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String SHOPNAME = "shopname";
	public static final String LOCATION = "location";
	public static final String CITY = "city";
	public static final String AREA = "area";
	public static final String PHONE_NUMBER = "phone_number";
	public static final String ABOUT_US = "about_us";
	public static final String SERVICE_TYPES = "service_types";
	public static final String SERVICE_LOCATIONS = "service_locations";
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
	public static final String SCRIPT_CREATE_TABLE = "CREATE VIRTUAL TABLE "

	+ TABLE_NAME + " USING fts3" + "(" + ID + " TEXT PRIMARY KEY, " + NAME
			+ " TEXT, " + PROFILE_ID + " TEXT, " + TYPE + " TEXT, " + SHOPNAME
			+ " TEXT," + LOCATION + " TEXT," + CITY + " TEXT, " + AREA
			+ " TEXT, " + PHONE_NUMBER + " TEXT, " + ABOUT_US + " TEXT, "
			+ SERVICE_TYPES + " TEXT, " + SERVICE_LOCATIONS + " TEXT"
			+ " UNIQUE (" + NAME + "," + PROFILE_ID
			+ ") ON CONFLICT REPLACE );";

	public ProfileDBHelper(Context context) {
		super(context, DB_NAME, null, Constants.DATABASE_VERSION_2);
	}

	/*@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(SCRIPT_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}*/

	public long insertOrUpdate(Profile profile) {
		// deleteProfile(profile);

		ContentValues values = new ContentValues();
		values.put(PROFILE_ID, profile.getProfile_id());
		values.put(NAME, profile.getName());
		values.put(SHOPNAME, profile.getShop_name());
		values.put(LOCATION, profile.getLocation());
		values.put(CITY, profile.getCity());
		values.put(AREA, profile.getArea());
		values.put(PHONE_NUMBER, profile.getPhone_number());
		values.put(ABOUT_US, profile.getAbout_us());
		values.put(SERVICE_TYPES, profile.getService_types());
		values.put(SERVICE_LOCATIONS, profile.getService_locations());

		long rowId = 0;
		Profile availableProfileInDB = getProfile(profile.getProfile_id());

		SQLiteDatabase database = getWritableDatabase();
		if (availableProfileInDB == null)
			rowId = database.insert(TABLE_NAME, null, values);
		else {
			String selection = PROFILE_ID + "='" + profile.getProfile_id()
					+ "'";
			database.update(TABLE_NAME, values, selection, null);
		}
		database.close();
		return rowId;
	}

	public long deleteProfile(Profile profile) {
		SQLiteDatabase database = getWritableDatabase();
		String selection = PROFILE_ID + "='" + profile.getProfile_id() + "'";
		long rowId = 0;
		rowId = database.delete(TABLE_NAME, selection, null);
		database.close();
		return rowId;
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

	public Profile getProfile(String profileId) {

		Profile profile = null;
		String selection = PROFILE_ID + "='" + profileId + "'";
		SQLiteDatabase database = getReadableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, selection, null, null,
				null, null);
		if (cursor != null && cursor.moveToFirst()) {
			profile = new Profile();

			profile.setArea(cursor.getString(cursor.getColumnIndexOrThrow(AREA)));
			profile.setPhone_number(cursor.getString(cursor
					.getColumnIndexOrThrow(PHONE_NUMBER)));
			profile.setAbout_us(cursor.getString(cursor
					.getColumnIndexOrThrow(ABOUT_US)));

			profile.setService_types(cursor.getString(cursor
					.getColumnIndexOrThrow(SERVICE_TYPES)));

			profile.setCity(cursor.getString(cursor.getColumnIndexOrThrow(CITY)));
			profile.setProfile_id(cursor.getString(cursor
					.getColumnIndexOrThrow(PROFILE_ID)));

			profile.setLocation(cursor.getString(cursor
					.getColumnIndexOrThrow(LOCATION)));
			profile.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));

			profile.setService_locations(cursor.getString(cursor
					.getColumnIndexOrThrow(SERVICE_LOCATIONS)));
			profile.setShop_name(cursor.getString(cursor
					.getColumnIndexOrThrow(SHOPNAME)));

			cursor.close();
		}
		database.close();
		return profile;
	}

	/*
	 * public int delete(String profileId) { SQLiteDatabase database =
	 * getWritableDatabase(); String selection = ID + "='" + profileId + "'";
	 * return database.delete(TABLE_NAME, selection, null); }
	 */

	public List<Profile> getProfile() {

		ArrayList<Profile> profilelist = new ArrayList<Profile>();
		Profile profile;
		// String selection = CATEGORY_ID + "='" + profileId + "'";
		SQLiteDatabase database = getReadableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, null, null, null,
				null, null);
		if (cursor != null && cursor.moveToFirst()) {

			for (int i = 0; i < cursor.getCount(); i++) {
				cursor.moveToPosition(i);
				profile = new Profile();

				profile.setArea(cursor.getString(cursor
						.getColumnIndexOrThrow(AREA)));
				profile.setPhone_number(cursor.getString(cursor
						.getColumnIndexOrThrow(PHONE_NUMBER)));
				profile.setAbout_us(cursor.getString(cursor
						.getColumnIndexOrThrow(ABOUT_US)));

				profile.setService_types(cursor.getString(cursor
						.getColumnIndexOrThrow(SERVICE_TYPES)));

				profile.setCity(cursor.getString(cursor
						.getColumnIndexOrThrow(CITY)));
				profile.setProfile_id(cursor.getString(cursor
						.getColumnIndexOrThrow(PROFILE_ID)));

				profile.setLocation(cursor.getString(cursor
						.getColumnIndexOrThrow(LOCATION)));
				profile.setName(cursor.getString(cursor
						.getColumnIndexOrThrow(NAME)));

				profile.setService_locations(cursor.getString(cursor
						.getColumnIndexOrThrow(SERVICE_LOCATIONS)));
				profile.setShop_name(cursor.getString(cursor
						.getColumnIndexOrThrow(SHOPNAME)));
				profilelist.add(profile);
			}

			cursor.close();
		}
		database.close();
		return profilelist;
	}

	public long deleteProfileByProfileId(String profileId) {
		SQLiteDatabase database = getWritableDatabase();
		String selection = PROFILE_ID + "='" + profileId + "'";
		long rowId = 0;
		rowId = database.delete(TABLE_NAME, selection, null);
		database.close();
		return rowId;
	}
}
