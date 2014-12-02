package com.everybodyonline.android.model;

import com.parse.ParseObject;

public class Area {

	String Objectid;
	String Cityid;
	String Name;
	boolean Status;

	public static Area getArea(ParseObject parseObject) {
		// TODO Auto-generated constructor stub
		Area a = new Area();
		a.Objectid = parseObject.getObjectId();
		a.Cityid = parseObject.getString("CityId");
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
		return Cityid;
	}

	public void setCityid(String cityid) {
		Cityid = cityid;
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
