package com.swachhand.android.guilttrip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PhotosFragment extends Fragment {
	
	public PhotosFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
        
        TextView contri = (TextView) rootView.findViewById(R.id.txtLabel);
        contri.setText(TrackLab.get(getActivity()).getTotalContribution() + " Kg");
        
        return rootView;
    }
	
}
