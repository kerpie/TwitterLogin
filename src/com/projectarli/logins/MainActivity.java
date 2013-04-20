package com.projectarli.logins;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	public SharedPreferences preferences = null;
	public TextView text = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		preferences = getSharedPreferences("data", MODE_PRIVATE);
		text = (TextView) findViewById(R.id.text);
		
		text.setText(	"Name: " + preferences.getString("user_name", "No name") + "\n" +
						"Username: " + preferences.getString("user_username", "No username") + "\n" +
						"User ID: " + preferences.getLong("user_id", 0) + "\n" +
						"Token: " + preferences.getString("oauth_token", "No token") + "\n" +
						"Token Secret: " + preferences.getString("oauth_token_secret", "No token secret") + "\n" +
						"Sesion Iniciada: " + preferences.getBoolean("isSessionStarted", false));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
