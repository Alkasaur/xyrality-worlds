package com.xirality.worlds;

import android.app.Activity;
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
		showNotImplementedYet();
	}

	private void showNotImplementedYet() {
		Toast.makeText(this, "Not implemented yet", Toast.LENGTH_LONG).show();
	}
}
