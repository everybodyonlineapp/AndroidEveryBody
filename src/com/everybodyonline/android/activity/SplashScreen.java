package com.everybodyonline.android.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.everybodyonline.android.R;
import com.everybodyonline.android.dbcontroller.CityDBHelper;
import com.everybodyonline.android.dbcontroller.ProfileDBHelper;
import com.everybodyonline.android.model.Profile;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SplashScreen extends Activity {
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);
		context = this;
		updateCityTable();
		// 
	}

	private void updateCityTable() {
		// TODO Auto-generated method stub
		ParseQuery<ParseObject> query = ParseQuery.getQuery("City");
		
		final CityDBHelper cityDb = new CityDBHelper(context);
		Date maxDate=cityDb.getMaxUpdatedTime();
		query.whereGreaterThan("updatedAt", maxDate);
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					if (objects.size()>0) {
						
					
					// BetDB betdb = new BetDB(context.getApplicationContext());
					ParseObject cityObject;
					ListIterator<ParseObject> listItem = objects.listIterator();
                   
					while (listItem.hasNext()) {
						cityObject = listItem.next();
						cityDb.insertOrUpdate(cityObject);
						cityObject.getUpdatedAt();
						// betdb.addUserPoint(betObject);
						String cityName = cityObject.getString("Name");
						Log.i("Tag", cityName);
					}
					}

				} else {
					Log.i("Error", e.getMessage());
				}
				
				openNextScreen();
			}

		});
	}
	
	//Try
	
	

	private void openNextScreen() {
		// TODO Auto-generated method stub
		ProfileDBHelper profileDBHelper = new ProfileDBHelper(context);
		List<Profile> profileList = profileDBHelper.getProfile();

		if (profileList.size() < 1) {
			// Go to EditProfile Screen
			startActivity(new Intent(context, SelectTypeOfProfile.class));
			finish();
		} else {
			// Go To Landing page
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
