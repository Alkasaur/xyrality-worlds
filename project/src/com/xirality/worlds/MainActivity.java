package com.xirality.worlds;

import java.util.ArrayList;
import java.util.List;

import com.xirality.worlds.model.GameWorld;
import com.xirality.worlds.model.UserInfo;
import com.xirality.worlds.task.LoginAndSearchTask;
import com.xirality.worlds.utils.NetworkUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

	private EditText editLogin;
	private EditText editPassword;
	private Button btnLogin;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initUI();
    }

	private void initUI() {
		editLogin = (EditText) findViewById(R.id.login);
        editPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loginAndSearch();
			}
		});
	}

	private void loginAndSearch() {
		//TODO: validation
		final String login = "android.test@xyrality.com"; //editLogin.getText().toString();
		final String password = "password"; // editPassword.getText().toString();
		
		UserInfo info = new UserInfo(login, password);
		info.setDeviceId(NetworkUtils.getMacAddress(this));
		
		LoginAndSearchTask task = new LoginAndSearchTask(info);
		task.execute();
	}

	private void showNotImplementedYet() {
		Toast.makeText(this, "Not implemented yet", Toast.LENGTH_LONG).show();
	}
}
