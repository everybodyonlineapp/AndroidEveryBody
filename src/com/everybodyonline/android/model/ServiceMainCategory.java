package com.everybodyonline.android.model;

import android.R.bool;

public class ServiceMainCategory {
	public String CategoryId;
	public String Name;
	public long updatedAt;
	public int Status;

	public String getCategoryId() {
		return CategoryId;
	}

	public void setCategoryId(String cityid) {
		CategoryId = cityid;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
	
	
}
