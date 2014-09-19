package com.swachhand.android.guilttrip;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Track {
	
	private static final String JSON_ID = "id";
	private static final String JSON_START_TIME = "startTime";
	private static final String JSON_STOP_TIME = "stopTime";
	private static final String JSON_ELAPSED_TIME = "elapsedTime";
	private static final String JSON_MODE = "modeOfTransport";
	private static final String JSON_DIST = "distanceTravelled";
	private static final String JSON_INSULT = "insult";
	private static final String JSON_CARBON_FOOTPRINT = "carbonFootprint";

	private Date mStartTime;
	private UUID mId;
	private Date mStopTime;
	private double mElapsedTime;
	private int mModeOfTransport;
	private double mDistanceTravelled;
	private double mCarbonFootprint;
	private String mInsult;

	public Track() {
		mId = UUID.randomUUID();
		mElapsedTime = 0;
		mStartTime = new Date();
		mStopTime = new Date();
		mModeOfTransport = 0;
	}
	
	public Track(JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSON_ID));
		mStartTime = new Date(json.getLong(JSON_START_TIME));
		mStopTime = new Date(json.getLong(JSON_STOP_TIME));
		mElapsedTime = json.getDouble(JSON_ELAPSED_TIME);
		mDistanceTravelled = json.getDouble(JSON_DIST);
		mModeOfTransport = json.getInt(JSON_MODE);
		mInsult = json.getString(JSON_INSULT);
		mCarbonFootprint = json.getDouble(JSON_CARBON_FOOTPRINT);
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_START_TIME, mStartTime.getTime());
		json.put(JSON_STOP_TIME, mStopTime.getTime());
		json.put(JSON_ELAPSED_TIME, mElapsedTime);
		json.put(JSON_DIST, mDistanceTravelled);
		json.put(JSON_MODE, mModeOfTransport);
		json.put(JSON_INSULT, mInsult);
		json.put(JSON_CARBON_FOOTPRINT, mCarbonFootprint);
		Log.d(TrackLab.TAG, "TO JSON CALLED:!");
		return json;
	}

	public Date getStartTime() {
		return mStartTime;
	}

	public void setStartTime(Date startTime) {
		mStartTime = startTime;
	}

	public Date getStopTime() {
		return mStopTime;
	}

	public void setStopTime(Date stopTime) {
		mStopTime = stopTime;
	}

	public double getElapsedTime() {
		return mElapsedTime;
	}

	public void setElapsedTime(double elapsedTime) {
		mElapsedTime = elapsedTime;
	}

	public UUID getId() {
		return mId;
	}

	public int getModeOfTransport() {
		return mModeOfTransport;
	}

	public void setModeOfTransport(int modeOfTransport) {
		mModeOfTransport = modeOfTransport;
	}

	public double getDistanceTravelled() {
		return mDistanceTravelled;
	}

	public void setDistanceTravelled(double distanceTravelled) {
		mDistanceTravelled = distanceTravelled;
	}

	public double getCarbonFootprint() {
		return mCarbonFootprint;
	}

	public void setCarbonFootprint(double carbonFootprint) {
		mCarbonFootprint = carbonFootprint;
	}

	public String getInsult() {
		return mInsult;
	}

	public void setInsult(String insult) {
		mInsult = insult;
	}

	
	
	public static String getModeOfTransport(int mode) {
		switch(mode) {
		case 0:
			return "Walking";
		case 1:
			return "Train";
		case 2:
			return "Bus";
		case 3:
			return "Diesel Car";
		case 4:
			return "Petrol Car";
		case 5:
			return "Taxi";
		default:
			return "";
		}
	}
	
}
