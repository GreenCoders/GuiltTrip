package com.swachhand.android.guilttrip;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

public class GuiltTripJSONSerializer {

	private Context mContext;
	private String mFilename;
	
	public GuiltTripJSONSerializer(Context c, String f) {
		mContext = c;
		mFilename = f;
	}
	
	public void saveTracks(ArrayList<Track> tracks)
		throws JSONException, IOException {
		
		JSONArray array = new JSONArray();
		for (Track t : tracks) 
			array.put(t.toJSON());
		
		Writer writer = null;
		try {
			OutputStream out = mContext
					.openFileOutput(mFilename, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} finally {
			if (writer != null)
				writer.close();
		}
		
	}
	
	public ArrayList<Track> loadTracks() throws IOException, JSONException {
		ArrayList<Track> tracks = new ArrayList<Track>();
		BufferedReader reader = null;
		try {
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				// Line breaks are omitted and irrelevant
				jsonString.append(line);
			}
				// Parse the JSON using JSONTokener
			Log.d(TrackLab.TAG, "Loading tracks");
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
				.nextValue();
			for (int i = 0; i < array.length(); i++) {
				tracks.add(new Track(array.getJSONObject(i)));
			}
		} catch (FileNotFoundException e) {
			
		} finally {
			if (reader != null)
				reader.close();
		}
		return tracks;
	}
	
}
