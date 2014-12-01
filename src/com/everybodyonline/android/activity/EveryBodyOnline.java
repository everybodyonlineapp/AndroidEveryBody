package com.everybodyonline.android.activity;

import com.parse.Parse;

import android.app.Application;

public class EveryBodyOnline extends Application{
@Override
public void onCreate() {
	// TODO Auto-generated method stub
	super.onCreate();
	
	Parse.initialize(this, "9F3USq23em1ecf7xvZqgUcBriAufiq0Zwa8aHqtq", "AvBZFZzj7uyduiXZ0XEkWxlAeXzynbTGy8VmsKoq");
	
}
}
