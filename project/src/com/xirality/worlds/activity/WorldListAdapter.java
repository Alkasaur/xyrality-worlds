package com.xirality.worlds.activity;

import java.util.ArrayList;
import java.util.List;

import com.xirality.worlds.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WorldListAdapter extends ArrayAdapter<String> {
	private List<String> items;
	private LayoutInflater inflater;
	
	public WorldListAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);
		items = new ArrayList<String>();
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void add(String levelName) {
		items.add(levelName);
	}

	@Override
	public String getItem(int position) {
		return items.get(position);
	}
	
	@Override
	public int getCount() {
		return items.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.world_list_item, null);
		}
		TextView text = (TextView) convertView.findViewById(R.id.name);
		text.setText(getItem(position));
		return convertView;
	}
}
