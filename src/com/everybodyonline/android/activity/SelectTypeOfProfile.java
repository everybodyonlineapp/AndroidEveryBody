package com.everybodyonline.android.activity;

import com.everybodyonline.android.R;
import com.everybodyonline.android.R.id;
import com.everybodyonline.android.R.layout;
import com.everybodyonline.android.R.menu;
import com.everybodyonline.android.util.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SelectTypeOfProfile extends Activity {
	private RadioGroup radioGroup;
	private RadioButton radioButton;
	private Button btnNext;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_type_of_profile);
		context=this;
		addListenerOnButton();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_type_of_profile, menu);
		return true;
	}

	public void addListenerOnButton() {

		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		btnNext = (Button) findViewById(R.id.btnNext);
	

		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// get selected radio button from radioGroup
				int selectedId = radioGroup.getCheckedRadioButtonId();

				// find the radiobutton by returned id
				Intent CreateProfileIntent = new Intent(context,EditProfile.class);
				CreateProfileIntent.putExtra(Constants.CreateProfileType, selectedId);
				context.startActivity(CreateProfileIntent);
				

				

			}

		});

	}
}
