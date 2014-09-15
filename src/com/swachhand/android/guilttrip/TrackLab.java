package com.swachhand.android.guilttrip;

import java.util.ArrayList;
import java.util.UUID;
import android.content.Context;
import android.util.Log;

public class TrackLab {

	private static ArrayList<Track> mTracks;
	private static final String FILENAME = "Tracks.json";

	public static final String TAG = "TrackLab";
	private GuiltTripJSONSerializer mSerializer;

	private static TrackLab sTrackLab;
	private Context mAppContext;
	

	private TrackLab(Context appContext) {
		mAppContext = appContext;
		mSerializer = new GuiltTripJSONSerializer(mAppContext, FILENAME);
		try {
			mTracks = mSerializer.loadTracks();
		}catch(Exception e) {
			mTracks = new ArrayList<Track>();
			Log.e(TAG, "Error loading tracks: " + e);
		}
	}
	
	public double getTotalContribution() {
		double totalContribution = 0;
		for (int i = 0; i < mTracks.size(); ++i) {
			totalContribution += mTracks.get(i).getCarbonFootprint();
			Log.d(TAG, "ADD: " + totalContribution + ", " + mTracks.get(i).getCarbonFootprint());
		}
		return totalContribution;
	}
	
	public static TrackLab get(Context c) {
		if (sTrackLab == null)
			return new TrackLab(c.getApplicationContext());
		return sTrackLab;
	}
	
	public Track getTrack(UUID id) {
        for (Track c : mTracks) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }
    
    public ArrayList<Track> getTracks() {
        return mTracks;
    }

    public void deleteTrack(Track c) {
        mTracks.remove(c);
    }
		
	public void addTrack(Track track) {
		mTracks.add(track);
		saveTracks();
		Log.d(TAG, "Tracks added");
	}
	
	public boolean saveTracks() {
		try {
			mSerializer.saveTracks(mTracks);
			Log.d(TAG, "Tracks saved to file");
			return true;
		}catch(Exception e) {
			Log.e(TAG, "Error saving files: " + e);
			return false;
		}
	}
}