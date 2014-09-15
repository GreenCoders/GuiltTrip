package com.swachhand.android.guilttrip;

import android.support.v4.app.Fragment;

public class TrackListActivity extends SingleFragmentActivity {

	@Override
	public Fragment createFragment() {
		return new TrackListFragment();
	}

}
