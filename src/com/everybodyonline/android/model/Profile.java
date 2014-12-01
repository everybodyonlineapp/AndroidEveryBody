package com.everybodyonline.android.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Profile {

	@SerializedName(value = "profile_id")
	private String profile_id;

	@SerializedName(value = "profile_name")
	private String name;
	@SerializedName(value = "shop_name")
	private String shop_name;
	@SerializedName(value = "location")
	private String location;
	@SerializedName(value = "city")
	private String city;
	@SerializedName(value = "area")
	private String area;
	@SerializedName(value = "phone_number")
	private String phone_number;
	@SerializedName(value = "about_us")
	private String about_us;
	@SerializedName(value = "service_types")
	private String service_types;
	@SerializedName(value = "service_locations")
	private String service_locations;

	public String getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(String profile_id) {
		this.profile_id = profile_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getAbout_us() {
		return about_us;
	}

	public void setAbout_us(String about_us) {
		this.about_us = about_us;
	}

	public String getService_types() {
		return service_types;
	}

	public void setService_types(String service_types) {
		this.service_types = service_types;
	}

	public String getService_locations() {
		return service_locations;
	}

	public void setService_locations(String service_locations) {
		this.service_locations = service_locations;
	}

}
