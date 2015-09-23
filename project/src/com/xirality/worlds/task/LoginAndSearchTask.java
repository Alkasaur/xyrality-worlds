package com.xirality.worlds.task;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.xirality.worlds.model.UserInfo;
import com.xirality.worlds.utils.IOUtils;

public class LoginAndSearchTask extends AsyncTask<Void, Void, Boolean> {
	private static final String TAG = "Search task";

	private static final int STATUS_OK = 200;
	
	private static final String WORLDS_REQUEST_URL = "http://backend1.lordsandknights.com/XYRALITY/WebObjects/BKLoginServer.woa/wa/worlds";
	
	private UserInfo userInfo;
	
	public LoginAndSearchTask(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			return search();
		} catch (Exception e) {
			handleError(e);
			return false;
		}
	}
	
	private Boolean search() throws ClientProtocolException, IOException, IllegalStateException, JSONException {

		HttpPost post = new HttpPost(getUrlWithParams());
		
		HttpClient client = new DefaultHttpClient();
        try {
            HttpResponse httpResponse = client.execute(post);
            
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == STATUS_OK) {
            	saveResponse(httpResponse);
            	return true;
            } else {
            	return false;
            }
        } finally {
        	client.getConnectionManager().shutdown();
        	client = null;
        }
	}
	
	private String getUrlWithParams() {
		Uri.Builder paramBuilder = new Uri.Builder();
		paramBuilder.appendQueryParameter("login", userInfo.getLogin());
		paramBuilder.appendQueryParameter("password", userInfo.getPassword());
		paramBuilder.appendQueryParameter("deviceType", userInfo.getDeviceType());
		paramBuilder.appendQueryParameter("deviceId", userInfo.getDeviceId());
		
		return WORLDS_REQUEST_URL + paramBuilder.toString();
	}
	
	private void saveResponse(HttpResponse response) throws UnsupportedEncodingException, IllegalStateException, IOException {
		String fileName = getFileName();
		File file = new File(fileName);
		IOUtils.saveToFile(file, response.getEntity().getContent());
	}

	private String getFileName() {
		return Environment.getExternalStorageDirectory() + File.separator + "worlds.dat";
	}
	
	private void handleError(Exception e) {
		Log.e(TAG, e.toString());
	} 
}
