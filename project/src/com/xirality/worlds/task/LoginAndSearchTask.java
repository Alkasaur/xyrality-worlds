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

import com.xirality.worlds.R;
import com.xirality.worlds.model.UserInfo;
import com.xirality.worlds.utils.IOUtils;

public class LoginAndSearchTask extends BaseTask<Boolean> {
	private static final String KEY_DEVICE_ID = "deviceId";
	private static final String KEY_DEVICE_TYPE = "deviceType";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_LOGIN = "login";
	
	private static final int STATUS_OK = 200;
	
	private static final String WORLDS_REQUEST_URL = "http://backend1.lordsandknights.com/XYRALITY/WebObjects/BKLoginServer.woa/wa/worlds";
	
	private UserInfo userInfo;
	
	public LoginAndSearchTask(
			Context context,
			UserInfo userInfo,
			TaskSuccessListener<Boolean> successListener,
			TaskFailureListener failureListener) {
		super(context, context.getString(R.string.any_please_wait), true, successListener, failureListener, null);
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
		paramBuilder.appendQueryParameter(KEY_LOGIN, userInfo.getLogin());
		paramBuilder.appendQueryParameter(KEY_PASSWORD, userInfo.getPassword());
		paramBuilder.appendQueryParameter(KEY_DEVICE_TYPE, userInfo.getDeviceType());
		paramBuilder.appendQueryParameter(KEY_DEVICE_ID, userInfo.getDeviceId());
		
		return WORLDS_REQUEST_URL + paramBuilder.toString();
	}
	
	private void saveResponse(HttpResponse response) throws UnsupportedEncodingException, IllegalStateException, IOException, JSONException {
		String fileName = IOUtils.getWorldsFileName();
		File file = new File(fileName);
		IOUtils.saveToFile(file, response.getEntity().getContent());
	}
}
