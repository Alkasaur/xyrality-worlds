package com.xirality.worlds.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xirality.worlds.R;
import com.xirality.worlds.model.UserInfo;
import com.xirality.worlds.task.BaseTask.TaskFailureListener;
import com.xirality.worlds.task.BaseTask.TaskSuccessListener;
import com.xirality.worlds.task.LoginAndSearchTask;
import com.xirality.worlds.utils.NetworkUtils;


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

		final String login = editLogin.getText().toString();
		final String password = editPassword.getText().toString();
		if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {
			UserInfo info = new UserInfo(login, password);
			info.setDeviceId(NetworkUtils.getMacAddress(this));

			LoginAndSearchTask task = new LoginAndSearchTask(this, info, new TaskSuccessListener<Boolean>() {
				@Override
				public void onTaskSuccess(Boolean result) {
					WorldListActivity.start(MainActivity.this);
				}
			}, new TaskFailureListener() {

				@Override
				public void onTaskFailure(Throwable e) {
					handleError(e);
				}
			});
			task.execute();
		}
	}

	private void handleError(Throwable e) {
		Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
	}
}
