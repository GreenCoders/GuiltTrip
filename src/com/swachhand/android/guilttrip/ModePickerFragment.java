package com.swachhand.android.guilttrip;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.RadioButton;

public class ModePickerFragment extends DialogFragment {
	
	private RadioButton mWalkingRadio;
	private RadioButton mTrainRadio;
	private RadioButton mBusRadio;
	private RadioButton mPetrolCarRadio;
	private RadioButton mDieselCarRadio;
	private RadioButton mTaxiRadio;
	
	public static final String EXTRA_MODE_CHOICE = "com.swachhand.android.guilttrip.mode_choice";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_mode, null);

		mWalkingRadio = (RadioButton) v.findViewById(R.id.radio_walking);
		mTrainRadio = (RadioButton) v.findViewById(R.id.radio_train);
		mBusRadio = (RadioButton) v.findViewById(R.id.radio_bus);
		mPetrolCarRadio = (RadioButton) v.findViewById(R.id.radio_petrol_car);
		mDieselCarRadio = (RadioButton) v.findViewById(R.id.radio_diesel_car);
		mTaxiRadio = (RadioButton) v.findViewById(R.id.radio_taxi);
		
		
		return new AlertDialog.Builder(getActivity())
			.setView(v)
			.setTitle(R.string.mode_picker_title)
			.setPositiveButton(android.R.string.ok, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sendResult(Activity.RESULT_OK);
				}
			})
			.setNegativeButton(android.R.string.cancel, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sendResult(Activity.RESULT_CANCELED);
				}
			})
			.create();
	}
	
	private int getChoice() {
		if (mWalkingRadio.isChecked())
			return 0;
		if (mTrainRadio.isChecked())
			return 1;
		if (mBusRadio.isChecked())
			return 2;
		if (mDieselCarRadio.isChecked())
			return 3;
		if (mPetrolCarRadio.isChecked())
			return 4;
		return 5;
	}
	
	private void sendResult(int resultCode) {
		if (getTargetFragment() == null)
			return;
		
		Intent i = new Intent();
		i.putExtra(EXTRA_MODE_CHOICE, getChoice());
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
}
