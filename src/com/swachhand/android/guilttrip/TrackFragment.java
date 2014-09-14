package com.swachhand.android.guilttrip;

import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.TextView;

public class TrackFragment extends Fragment {
	
	private Chronometer mWatch;
	private Button mStart_button;
	private Button mStop_button;
	private Button mReset_button;
	private long elapsedMillis;
	private double elapsedSecs;
	private Track mTrack;
	
	private TextView mGpsCoordinates;
	private TextView mGpsAccuracy;
	private TextView mDist;
	
	private boolean mWatchStarted;
	private long mWatchTicks;
	private double mDistanceMoved;
	
	private ArrayList<Coordinate> way_points;
	
	private GPSTracker mGPSTracker;
	
	private static final String TAG = "TrackFragment";
	public static final String EXTRA_TRACK_ID = "com.swachhand.android.guilttrip.track_id";
	private static final int UPDATE_TIME = 5;
	private static final float ACC_THRESH = 60;
	protected static final double LAT_THRESH = 0.000005;
	protected static final double LONG_THRESH = 0.000005;
	
	private Coordinate mPrev;
	
	private boolean prev_set = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		elapsedMillis = 0;
		elapsedSecs = 0;
		mWatchStarted = false;
		mWatchTicks = 0;
		mTrack = new Track();
		way_points = new ArrayList<Coordinate>();
		mDistanceMoved = 0;
		
		mGPSTracker = new GPSTracker(getActivity().getApplicationContext());
		mPrev = new Coordinate();
		// Need to retrieve extra from savedInstanceState for rotation
	}
	
	

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putSerializable(EXTRA_TRACK_ID, mTrack.getId());
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_track, container, false);
		
		mWatch = (Chronometer) v.findViewById(R.id.chronometer);
		mWatch.setOnChronometerTickListener(new OnChronometerTickListener() {
			
			@Override
			public void onChronometerTick(Chronometer chronometer) {
				
				elapsedMillis = SystemClock.elapsedRealtime() - mWatch.getBase();
				elapsedSecs = elapsedMillis / 1000.0;
				
				mGPSTracker = new GPSTracker(getActivity().getApplicationContext());
				if ((int) elapsedSecs % UPDATE_TIME == 0 && mWatchStarted && mGPSTracker.canGetLocation() && mGPSTracker.getAccuracy() > 0) {
					mGPSTracker.getLocation();

					Coordinate c = new Coordinate();
					c.setLatitude(mGPSTracker.getLatitude());
					c.setLongitude(mGPSTracker.getLongitude());
					
					if (!prev_set) {
						mPrev.setLatitude(c.getLatitude());
						mPrev.setLongitude(c.getLongitude());
						prev_set = true;
					}
					 
					if (Math.abs(mPrev.getLatitude() - c.getLatitude()) > LAT_THRESH && Math.abs(mPrev.getLongitude() - c.getLongitude()) > LONG_THRESH)
						mDistanceMoved += GPSTracker.gps2m(c.getLatitude(), c.getLongitude(), mPrev.getLatitude(), mPrev.getLongitude());

					// way_points.add(c);
					
					Log.d(TAG, "Waypoint: " + c.getLatitude() + ", " + c.getLongitude() + " added");
//					Toast.makeText(getActivity(), 
//						"Waypoint: " + c.getLatitude() + ", " + c.getLongitude() + " added", 
//						Toast.LENGTH_SHORT)
//						.show();
					// Toast.makeText(getActivity(), "Accuracy: " + mGPSTracker.getAccuracy(), Toast.LENGTH_SHORT).show();
					
					mGpsAccuracy.setText(("Accuracy: " + mGPSTracker.getAccuracy()).toString());
					mGpsCoordinates.setText(("GPS coordinates: " + mGPSTracker.getLatitude() + ", " + mGPSTracker.getLongitude()).toString());
					
					mDist.setText("Distance Moved: " + mDistanceMoved);
					
					mPrev.setLongitude(c.getLongitude());
					mPrev.setLatitude(c.getLatitude());}
				
				
			}
		});
		
		mStart_button = (Button) v.findViewById(R.id.start_button);
		mStart_button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				start_stop_watch(v);
			}
			
		});
		
		mStop_button = (Button) v.findViewById(R.id.stop_button);
		mStop_button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				start_stop_watch(v);
			}
			
		});
		
		mReset_button = (Button) v.findViewById(R.id.reset_button);
		mReset_button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				start_stop_watch(v);
			}
			
		});
		
		mGpsAccuracy = (TextView) v.findViewById(R.id.accuracy_text_view);
		mGpsCoordinates = (TextView) v.findViewById(R.id.gps_text_view);
		mDist = (TextView) v.findViewById(R.id.distance_text_view);
		mGpsAccuracy.setText(("Accuracy: " + mGPSTracker.getAccuracy()).toString());
		mGpsCoordinates.setText(("GPS coordinates: " + mGPSTracker.getLatitude() + ", " + mGPSTracker.getLongitude()).toString());
		mDist.setText("Distance Moved = 0");
		
		return v;
		
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mGPSTracker.stopUsingGPS();
	}
	
	private void start_stop_watch(View v) {
		switch(v.getId()) {
		case R.id.start_button:
			mWatch.setBase(SystemClock.elapsedRealtime());
			mTrack.setStartTime(new Date());
			mWatch.start();
			mWatchStarted = true;
			break;
		case R.id.stop_button:
			Log.d(TAG, "" + elapsedSecs);
			mTrack.setStopTime(new Date());
			mTrack.setElapsedTime(elapsedSecs);
			mWatch.stop();
			mWatchStarted = false;
			prev_set = false;
			break;
		case R.id.reset_button:
			mWatch.setBase(SystemClock.elapsedRealtime());
			if (mGPSTracker.canGetLocation()) {
				mGPSTracker.getLocation();
				Log.d(TAG, "GPS coordinates: " + mGPSTracker.getLatitude() + ", " + mGPSTracker.getLongitude());
				// Toast.makeText(getActivity(), "GPS coordinates: " + mGPSTracker.getLatitude() + ", " + mGPSTracker.getLongitude(), Toast.LENGTH_SHORT).show();
			}
			else
			{
				// mGPSTracker.showSettingsAlert();
				Log.d(TAG, "GPS Disabled");
			}
			prev_set = false;
			break;
		}
	}
}
