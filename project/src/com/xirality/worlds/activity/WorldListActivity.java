package com.xirality.worlds.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xirality.worlds.R;
import com.xirality.worlds.utils.IOUtils;
import com.xirality.worlds.utils.MalformedJSONConverter;

public class WorldListActivity extends ListActivity {

	private static final String KEY_NAME = "name";
	private static final String KEY_AVAILABLE_WORLDS = "allAvailableWorlds";
	
	WorldListAdapter adapter;

	public static void start(Context ctx) {
		Intent i = new Intent(ctx, WorldListActivity.class);
		ctx.startActivity(i);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_world_list);
		
		adapter = new WorldListAdapter(this);
		getListView().setAdapter(adapter);
		try {
			read();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void read() throws IOException, JSONException {
		File file = new File(IOUtils.getWorldsFileName());
		FileInputStream fin = null;
		ByteArrayOutputStream out = null;
		InputStream tempInputStream = null;
		try {
			fin = new FileInputStream(file);
			//seems our server returns malformed JSON, so need to fix it
			out = (ByteArrayOutputStream) MalformedJSONConverter.fixAndConvert(fin);

			tempInputStream = new ByteArrayInputStream(out.toByteArray());
			
			JSONObject json = IOUtils.getJSONObject(tempInputStream);
			if (json.has(KEY_AVAILABLE_WORLDS)) {
				JSONArray worldsJson = json.getJSONArray(KEY_AVAILABLE_WORLDS);
				for (int i = 0; i < worldsJson.length(); i++) {
					JSONObject item = worldsJson.getJSONObject(i);
					adapter.add(item.getString(KEY_NAME));
				}
				adapter.notifyDataSetChanged();
			}
		} finally {
			IOUtils.safelyCloseClosable(fin);
			IOUtils.safelyCloseClosable(out);
			IOUtils.safelyCloseClosable(tempInputStream);
		}
	}
}
