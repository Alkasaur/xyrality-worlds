package com.xirality.worlds.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public final class NetworkUtils {
	
	private static final String UTF_8 = "UTF-8";
	
	public static String getMacAddress(Context context) {
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		return info.getMacAddress();
	}
	
	public static String getResponseString(final HttpResponse response) {
		String result = null;
		final HttpEntity entity = response.getEntity();
		if (entity != null) {
			try {
				result = EntityUtils.toString(entity, HTTP.UTF_8);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return SafeValueUtils.safeString(result);
    }
	
	public static JSONObject getJSONObject(InputStream contentIS) throws JSONException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(contentIS, UTF_8));
        StringBuilder sb = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null;) {
            sb.append(line).append('\n');
        }
        JSONTokener tokener = new JSONTokener(sb.toString());
        JSONObject json = new JSONObject(tokener);
        return json;
    }
	
}
