package com.everybodyonline.android.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import com.everybodyonline.android.R;
import com.everybodyonline.android.R.layout;
import com.everybodyonline.android.R.menu;
import com.everybodyonline.android.dbcontroller.CityDBHelper;
import com.everybodyonline.android.model.Area;
import com.everybodyonline.android.model.City;
import com.everybodyonline.android.util.Constants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class EditProfile extends Activity implements OnItemSelectedListener,
		OnItemClickListener, OnClickListener {

	EditText etName, etPhone, etAdress;
	AutoCompleteTextView etCity, etArea;

	/*
	 * RadioGroup radioGroup; RadioButton Consumer, Provider;
	 */
	ArrayList<City> citylist;
	ArrayList<Area> arealist;
	Context context;
	static String areaId, cityId;
	Button save, cancel, edit;
	boolean isEditProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		context = this;
		etName = (EditText) findViewById(R.id.Name);
		// etShopName = (EditText) findViewById(R.id.ShopName);
		etPhone = (EditText) findViewById(R.id.PhoneNumber);
		etCity = (AutoCompleteTextView) findViewById(R.id.City);
		etArea = (AutoCompleteTextView) findViewById(R.id.Area);
		etAdress = (EditText) findViewById(R.id.Adress);
		// etServicetype = (EditText) findViewById(R.id.ServiceType);
		// etServicearea = (EditText) findViewById(R.id.ServiceArea);
		// radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		save = (Button) findViewById(R.id.btnSave);
		cancel = (Button) findViewById(R.id.btnCancel);
		edit = (Button) findViewById(R.id.btnEdit);
		// etServicetype.setOnClickListener(this);
		isEditProfile = getIntent().getBooleanExtra(
				Constants.CreateProfileType, false);
		init();
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);
		edit.setOnClickListener(this);

		CityDBHelper cityDBHelper = new CityDBHelper(this);
		citylist = cityDBHelper.getCities();

		ArrayAdapter<City> adapter = new ArrayAdapter<City>(this,
				android.R.layout.select_dialog_item, citylist);
		// Getting the instance of AutoCompleteTextView
		// AutoCompleteTextView actv=
		// (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
		etCity.setThreshold(1);// will start working from first character
		etCity.setAdapter(adapter);// setting the adapter data into the
									// AutoCompleteTextView
		etCity.setOnItemSelectedListener(this);
		etCity.setOnItemClickListener(this);

	}

	private void init() {
		// TODO Auto-generated method stub
		if (isEditProfile) {
			edit.setVisibility(View.GONE);
			save.setVisibility(View.VISIBLE);
			cancel.setVisibility(View.VISIBLE);
			etName.setEnabled(true);
			etPhone.setEnabled(true);
			etCity.setEnabled(true);
			etArea.setEnabled(true);
			etAdress.setEnabled(true);
		} else {
			edit.setVisibility(View.VISIBLE);
			save.setVisibility(View.GONE);
			cancel.setVisibility(View.GONE);
			etName.setEnabled(false);
			etPhone.setEnabled(false);
			etCity.setEnabled(false);
			etArea.setEnabled(false);
			etAdress.setEnabled(false);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_profile, menu);
		return true;
	}

	private void addareas() {
		// TODO Auto-generated method stub
		ArrayAdapter<Area> adapter = new ArrayAdapter<Area>(this,
				android.R.layout.select_dialog_item, arealist);

		etArea.setThreshold(1);// will start working from first character
		etArea.setAdapter(adapter);// setting the adapter data into the
									// AutoCompleteTextView
		etArea.setOnItemSelectedListener(this);
		etArea.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				areaId = arealist.get(arg2).getObjectid();
			}
		});
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		// String cityId =
		cityId = citylist.get(arg2).Cityid;
		final ProgressDialog pd = new ProgressDialog(context);
		pd.setCancelable(false);
		pd.show();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Area");

		query.whereEqualTo("CityId", cityId);
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> objects, ParseException e) {
				if (pd != null && pd.isShowing())
					pd.dismiss();
				if (e == null) {
					if (objects.size() > 0) {

						ParseObject cityObject;
						ListIterator<ParseObject> listItem = objects
								.listIterator();
						arealist = new ArrayList<Area>();

						while (listItem.hasNext()) {
							cityObject = listItem.next();

							arealist.add(Area.getArea(cityObject));
							addareas();

						}
					}

				} else {
					Log.i("Error", e.getMessage());
				}

			}

		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btnCancel:
			finish();
			break;
		case R.id.btnSave: {
			if (etName.getText().toString().length() < 1) {
				showToast("Name Can not be Empty");
			} else if (etPhone.getText().toString().length() < 10) {
				showToast("Please Enter Valid Phone Number");
			} else if (etCity.getText().toString().length() < 1) {
				showToast("City Can not be Empty");
			} else if (etAdress.getText().toString().length() < 1) {
				showToast("Address Can not be Empty");
			}

		}
			checkProfile();
			break;
		case R.id.btnEdit:
			isEditProfile = true;
			init();
		default:
			break;
		}
	}

	private void showToast(String Showtext) {
		// TODO Auto-generated method stub
		Toast.makeText(context, Showtext, Toast.LENGTH_SHORT).show();
	}

	private void checkProfile() {
		// TODO Auto-generated method stub
		final ProgressDialog pd = new ProgressDialog(context);
		pd.setCancelable(false);
		pd.show();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Profiles");

		query.whereEqualTo("Phone", etPhone.getText().toString());
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> objects, ParseException e) {
				if (pd != null && pd.isShowing())
					pd.dismiss();
				if (e == null) {
					if (objects.size() > 0) {

						ParseObject cityObject;
						ListIterator<ParseObject> listItem = objects
								.listIterator();

						while (listItem.hasNext()) {
							cityObject = listItem.next();

							{
								Toast.makeText(context,
										"Profile Already Exist",
										Toast.LENGTH_SHORT).show();
							}

						}
					} else {
						saveProfile();
					}

				} else {
					Log.i("Error", e.getMessage());
				}

			}

		});
	}

	private void saveProfile() {
		// TODO Auto-generated method stub
		final ProgressDialog pd = new ProgressDialog(context);
		pd.setCancelable(false);

		pd.show();
		ParseObject parseobject = new ParseObject("Profiles");

		parseobject.put("Name", etName.getText().toString());
		parseobject.put("Phone", etPhone.getText().toString());

		parseobject.put("City", cityId);
		parseobject.put("Area", areaId);
		parseobject.put("Adress", etAdress.getText().toString());
		// parseobject.put("AboutUs", eta.getText().toString());
		parseobject.put("Status", true);
		parseobject.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException arg0) {
				// TODO Auto-generated method stub
				pd.dismiss();
			}
		});

	}

}
