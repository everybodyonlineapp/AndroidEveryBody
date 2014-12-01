package com.everybodyonline.android.activity;

import java.util.List;

import com.everybodyonline.android.R;
import com.everybodyonline.android.dbcontroller.ProfileDBHelper;
import com.everybodyonline.android.model.Profile;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

public class SplashScreen extends Activity {
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);
		context=this;
		openNextScreen();
	}

	private void openNextScreen() {
		// TODO Auto-generated method stub
		ProfileDBHelper profileDBHelper = new ProfileDBHelper(context);
		List<Profile> profileList=profileDBHelper.getProfile();
		
		if(profileList.size()<1)
		{
			//Go to EditProfile Screen
			startActivity(new Intent(context,EditProfile.class));
			finish();
		}
		else
		{
			//Go To Landing page
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
