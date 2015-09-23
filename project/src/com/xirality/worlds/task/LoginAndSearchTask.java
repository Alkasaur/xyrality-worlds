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

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.xirality.worlds.model.UserInfo;
import com.xirality.worlds.utils.IOUtils;

public class LoginAndSearchTask extends BaseTask<Boolean> {
	
	private static final int STATUS_OK = 200;
	
	private static final String WORLDS_REQUEST_URL = "http://backend1.lordsandknights.com/XYRALITY/WebObjects/BKLoginServer.woa/wa/worlds";
	
	private UserInfo userInfo;
	
	public LoginAndSearchTask(
			Context context,
			UserInfo userInfo,
			TaskSuccessListener<Boolean> successListener,
			TaskFailureListener failureListener) {
		super(context, "Please wait...", true, successListener, failureListener, null);
		this.userInfo = userInfo;
	}
	
	@Override
	protected TaskResult<Boolean> process(Object... params) throws Exception {
		return new TaskResult<Boolean>(search());
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
}
