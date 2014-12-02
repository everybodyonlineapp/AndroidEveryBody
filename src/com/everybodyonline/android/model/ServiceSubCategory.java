package com.everybodyonline.android.model;

import com.parse.ParseObject;

public class ServiceSubCategory {

	String Objectid;
	String CategoryId;
	String Name;
	boolean Status;

	public static ServiceSubCategory getServiceSubCategory(ParseObject parseObject) {
		// TODO Auto-generated constructor stub
		ServiceSubCategory a = new ServiceSubCategory();
		a.Objectid = parseObject.getObjectId();
		a.CategoryId = parseObject.getString("CityId");
		a.Name = parseObject.getString("Name");
		a.Status = parseObject.getBoolean("Status");
		return a;

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}

	public String getObjectid() {
		return Objectid;
	}

	public void setObjectid(String objectid) {
		Objectid = objectid;
	}

	public String getCityid() {
		return CategoryId;
	}

	public void setCityid(String cityid) {
		CategoryId = cityid;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public boolean getStatus() {
		return Status;
	}

	public void setStatus(boolean status) {
		Status = status;
	}

}
