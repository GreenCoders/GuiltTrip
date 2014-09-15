package com.swachhand.android.guilttrip;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.TextView;
import android.widget.Toast;

public class TrackFragment extends Fragment {
	
	private Chronometer mWatch;
	private Button mStart_button;
	private Button mStop_button;
	// private Button mReset_button;
	private long elapsedMillis;
	private double elapsedSecs;
	private Track mTrack;
	
	private TextView mGpsCoordinates;
	private TextView mGpsAccuracy;
	private TextView mDist;
	
	private boolean mWatchStarted;
	private double mDistanceMoved;
		
	private GPSTracker mGPSTracker;
	
	private static final String TAG = "TrackFragment";
	public static final String EXTRA_TRACK_ID = "com.swachhand.android.guilttrip.track_id";
	private static final int UPDATE_TIME = 5;
	private static final float ACC_THRESH_MIN = 60;
	private static final float ACC_THRESH_MAX = 0;
	protected static final double LAT_THRESH = 0.0000005;
	protected static final double LONG_THRESH = 0.0000005;
	private static final String DIALOG_MODE = "mode";
	private static final int REQUEST_MODE = 0;
	
	
	private Coordinate mPrev;
	
	private boolean prev_set = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		elapsedMillis = 0;
		elapsedSecs = 0;
		mWatchStarted = false;
		mTrack = new Track();
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
				if ((int) elapsedSecs % UPDATE_TIME == 0 
						&& mWatchStarted 
						&& mGPSTracker.canGetLocation() 
						&& mGPSTracker.getAccuracy() > ACC_THRESH_MAX 
						&& mGPSTracker.getAccuracy() < ACC_THRESH_MIN) {
					
					mGPSTracker.getLocation();

					Coordinate c = new Coordinate();
					c.setLatitude(mGPSTracker.getLatitude());
					c.setLongitude(mGPSTracker.getLongitude());
					
					if (!prev_set) {
						mPrev.setLatitude(c.getLatitude());
						mPrev.setLongitude(c.getLongitude());
						prev_set = true;
					}
					 
					if (Math.abs(mPrev.getLatitude() - c.getLatitude()) > LAT_THRESH 
							&& Math.abs(mPrev.getLongitude() - c.getLongitude()) > LONG_THRESH)
						mDistanceMoved += GPSTracker.gps2m(c.getLatitude(), 
														   c.getLongitude(), 
														   mPrev.getLatitude(), 
														   mPrev.getLongitude());
					
					mGpsAccuracy.setText(("Accuracy: " + mGPSTracker.getAccuracy()).toString());
					mGpsCoordinates.setText(("GPS coordinates: " + mGPSTracker.getLatitude() + ", " + mGPSTracker.getLongitude()).toString());
					mDist.setText("Distance Moved: " + mDistanceMoved);
										
					mPrev.setLongitude(c.getLongitude());
					mPrev.setLatitude(c.getLatitude());
					
				}
			}
		});
		
		mStart_button = (Button) v.findViewById(R.id.start_button);
		mStart_button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mTrack = new Track();
				mWatch.setBase(SystemClock.elapsedRealtime());
				mTrack.setStartTime(new Date());
				mWatch.start();
				mWatchStarted = true;
				mStart_button.setEnabled(false);
				mStop_button.setEnabled(true);
			}			
		});
		
		mStop_button = (Button) v.findViewById(R.id.stop_button);
		mStop_button.setEnabled(false);
		mStop_button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mTrack.setStopTime(new Date());
				mTrack.setElapsedTime(elapsedSecs);
				mWatch.stop();
				mWatchStarted = false;
				prev_set = false;
				
				// Show the dialog for picking the mode
				FragmentManager fm = getActivity().getSupportFragmentManager();
				ModePickerFragment dialog = new ModePickerFragment();
				dialog.setTargetFragment(TrackFragment.this, REQUEST_MODE);
				dialog.show(fm, DIALOG_MODE);

				mWatch.setBase(SystemClock.elapsedRealtime());
				mStop_button.setEnabled(false);
			}
			
		});
		
		// mReset_button = (Button) v.findViewById(R.id.reset_button);
		/* mReset_button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mWatch.setBase(SystemClock.elapsedRealtime());
				
				if (mGPSTracker.canGetLocation()) {
					mGPSTracker.getLocation();
				}
				else {
					Log.d(TAG, "GPS Disabled");
				}
				
				prev_set = false;

			}
			
		});	*/
		
		mGpsAccuracy = (TextView) v.findViewById(R.id.accuracy_text_view);
		mGpsAccuracy.setText(("Accuracy: " + mGPSTracker.getAccuracy()).toString());
		
		mGpsCoordinates = (TextView) v.findViewById(R.id.gps_text_view);
		mGpsCoordinates.setText(("GPS coordinates: " + mGPSTracker.getLatitude() + ", " + mGPSTracker.getLongitude()).toString());

		mDist = (TextView) v.findViewById(R.id.distance_text_view);
		mDist.setText("Distance Moved: 0");
		
		return v;
		
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mGPSTracker.stopUsingGPS();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != REQUEST_MODE)
			return;
		if (resultCode != Activity.RESULT_OK) {
			Toast.makeText(getActivity(), "Track not recorded", Toast.LENGTH_SHORT).show();
			mStart_button.setEnabled(true);
			mStop_button.setEnabled(false);
			return;
		}
		int modeOfTransport = data.getIntExtra(ModePickerFragment.EXTRA_MODE_CHOICE, 0);
		mTrack.setModeOfTransport(modeOfTransport);
		mTrack.setDistanceTravelled(mDistanceMoved);
		mTrack.setCarbonFootprint(CO2_calculate.carbon_footprint_calculate(Track.getModeOfTransport(mTrack.getModeOfTransport()), mTrack.getDistanceTravelled()));
		Log.d(TAG, "1 Called");
		mTrack.setInsult(Insults.getInsult(mTrack.getModeOfTransport()));
		Log.d(TAG, mTrack.getCarbonFootprint() + "");
		TrackLab.get(getActivity()).addTrack(mTrack);
		TrackLab.get(getActivity()).saveTracks();
		Toast.makeText(getActivity(), "Track Recorded", Toast.LENGTH_SHORT).show();
		Toast.makeText(getActivity(), mTrack.getInsult(), Toast.LENGTH_LONG).show();
		Log.d(TAG, mTrack.toString());
		
		mStop_button.setEnabled(false);
		mStart_button.setEnabled(true);
	}
	
}
