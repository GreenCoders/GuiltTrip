package com.swachhand.android.guilttrip;

import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TrackListFragment extends ListFragment {
	private ArrayList<Track> mTracks;
	
	private class TrackAdapter extends ArrayAdapter<Track> {
		
		public TrackAdapter(ArrayList<Track> tracks) {
			super(getActivity(), 0, tracks);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_list_track, null);
			}
			
			Track track = getItem(position);
			TextView mMode = (TextView) convertView.findViewById(R.id.mode_label);
			// mMode.setText("Hello");
			String mode = Track.getModeOfTransport(track.getModeOfTransport());
			mMode.setText(mode);
			
			TextView mFrom = (TextView) convertView.findViewById(R.id.time_start_stop_label);
			mFrom.setText(track.getStartTime().toString());
			// mFrom.setText("From");
			
			TextView mTo = (TextView) convertView.findViewById(R.id.stop_label);
			mTo.setText(track.getStopTime().toString());
			
			TextView mElapsedTime = (TextView) convertView.findViewById(R.id.elapsed_label);
			mElapsedTime.setText(track.getElapsedTime() + " secs");
			
			TextView mDist = (TextView) convertView.findViewById(R.id.distance_label);
			double dist = track.getDistanceTravelled(); 
			mDist.setText(dist + " Mtrs");
			
			TextView mFootprint = (TextView) convertView.findViewById(R.id.footprint_label);
			mFootprint.setText(CO2_calculate.carbon_footprint_calculate(mode, dist) + " Kg");
			
			TextView mInsults = (TextView) convertView.findViewById(R.id.advice_label);
			mInsults.setText(track.getInsult());
			
//			TextView mDistance = (TextView) convertView.findViewById(R.id.distance_label);
//			mDistance.setText(track.getDistanceTravelled() + " Mtrs");
			
			
			// TextView mStop = (TextView) convertView.findViewById(R.id.stop_label);
			// mStop.setText("Stop");
			return convertView;
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTracks = new ArrayList<Track>();
		Track track = new Track();
		track.setStartTime(new Date());
		track.setStopTime(new Date());
		mTracks.add(track);
//		for(int i = 0; i < 5; i++) {
//			Track track = new Track();
//			track.setStartTime(new Date());
//			track.setStopTime(new Date());
//			mTracks.add(track);
//		}
		mTracks = TrackLab.get(getActivity()).getTracks();
		TrackAdapter adapter = new TrackAdapter(mTracks);
		setListAdapter(adapter);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		((TrackAdapter) getListAdapter()).notifyDataSetChanged();
	}

}
