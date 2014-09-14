package com.swachhand.android.guilttrip;

import java.util.Date;
import java.util.UUID;

public class Track {

	private Date mStartTime;
	private UUID mId;
	private Date mStopTime;
	private double mElapsedTime;

	public Track() {
		mId = UUID.randomUUID();
		mElapsedTime = 0;
		mStartTime = new Date();
		mStopTime = new Date();
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
	
}
