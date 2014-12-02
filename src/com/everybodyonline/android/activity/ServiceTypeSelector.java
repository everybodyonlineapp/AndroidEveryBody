package com.everybodyonline.android.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.everybodyonline.android.R;
import com.everybodyonline.android.R.layout;
import com.everybodyonline.android.R.menu;
import com.everybodyonline.android.dbcontroller.ServiceMainCategoryDBHelper;
import com.everybodyonline.android.model.Area;
import com.everybodyonline.android.model.ServiceMainCategory;
import com.everybodyonline.android.model.ServiceSubCategory;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ServiceTypeSelector extends Activity {

	Spinner mainCategorySpinner, subCategorySpinner;
	Context context;
	ArrayList<ServiceMainCategory> mainCategoryList;
	ArrayList<ServiceSubCategory> subCategoryList;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_type_selector);
		context=this;
		mainCategorySpinner = (Spinner) findViewById(R.id.SelectMainCategory);
		subCategorySpinner = (Spinner) findViewById(R.id.SelectSubCategory);
		intializeMainCategory();
	}

	private void intializeMainCategory() {
		// TODO Auto-generated method stub
		ServiceMainCategoryDBHelper serviceMainCategoryDBHelper = new ServiceMainCategoryDBHelper(context);
	 mainCategoryList=serviceMainCategoryDBHelper.getCategories();
	
	ArrayAdapter<ServiceMainCategory> dataAdapter = new ArrayAdapter<ServiceMainCategory>(this,
			android.R.layout.simple_spinner_item, mainCategoryList);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mainCategorySpinner.setAdapter(dataAdapter);
	mainCategorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "Waw", Toast.LENGTH_SHORT).show();
			String categoryId= mainCategoryList.get(arg2).getCategoryId();
			intializeSubCategory(categoryId);
		}

		

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	});
	}

	
	private void intializeSubCategory(String  categoryId) {
		// TODO Auto-generated method stub
		final ProgressDialog pd = new ProgressDialog(context);
		pd.setCancelable(false);
		pd.show();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ServiceSubCategory");

		query.whereEqualTo("ServiceCategoryId", categoryId);
		
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> objects, ParseException e) {
				if (pd != null && pd.isShowing())
					pd.dismiss();
				if (e == null) {
					if (objects.size() > 0) {

						ParseObject parseObject;
						ListIterator<ParseObject> listItem = objects
								.listIterator();
						subCategoryList = new ArrayList<ServiceSubCategory>();

						while (listItem.hasNext()) {
							parseObject = listItem.next();

							subCategoryList.add(ServiceSubCategory.getServiceSubCategory(parseObject));
							

						}
					}
					
					setSubCategorySpinner();

				} else {
					Log.i("Error", e.getMessage());
				}

			}

			

		});
	}
	
	
	private void setSubCategorySpinner() {
		// TODO Auto-generated method stub
		ArrayAdapter<ServiceSubCategory> dataAdapter = new ArrayAdapter<ServiceSubCategory>(this,
				android.R.layout.simple_spinner_item, subCategoryList);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			subCategorySpinner.setAdapter(dataAdapter);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.service_type_selector, menu);
		return true;
	}

}
