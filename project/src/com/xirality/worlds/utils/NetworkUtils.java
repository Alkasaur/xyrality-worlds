package com.xirality.worlds.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public final class NetworkUtils {
	
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
}
