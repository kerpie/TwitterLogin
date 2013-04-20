package com.projectarli.logins;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	public String CONSUMER_KEY = "YNZSWwtHNcEuB8hewgGX0A";
	public String CONSUMER_SECRET = "soWiJPA6kU9x9AZIGtrEFfRGMvD6YoJf8ReFAmTBWU";
	public String ACCESS_TOKEN = "156151238-5WKrNlutRhKCqfNXFb9uuwTs4WpZ1QlbjgHqkvi7";
	public String TOKEN_SECRET = "s2r7MwQD1bISqzL5Ib44AbnWuIxbkyhEQBBbsUM8dhA";
	public String TWITTER_CALLBACK_URL = "kunfoodtesting://kerpie.com";
	public Twitter twitter = null;
	public RequestToken requestToken = null;
	
	public SharedPreferences preferences = null;
	public Button twitterLogin = null;
	
	String verifier = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		twitterLogin = (Button) findViewById(R.id.twitterButton);
		preferences = getSharedPreferences("data", MODE_PRIVATE);
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(CONSUMER_KEY);
        builder.setOAuthConsumerSecret(CONSUMER_SECRET);
        Configuration configuration = builder.build();

        TwitterFactory factory = new TwitterFactory(configuration);
        twitter = factory.getInstance();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		
		Uri uri = intent.getData();
		if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
			verifier = uri.getQueryParameter("oauth_verifier");
       	 	new TwitterNextStep().execute();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		twitterLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new TwitterLogin().execute();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	public class TwitterLogin extends AsyncTask<Void, Integer, Void>{

		@Override
		protected Void doInBackground(Void... params) {
            try {
                requestToken = twitter.getOAuthRequestToken(TWITTER_CALLBACK_URL);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL()));
            startActivity(intent);
		}
	}
	
	public class TwitterNextStep extends AsyncTask<Void, Integer, Void>{
		@Override
		protected Void doInBackground(Void... params) {
			try{
				AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
				Editor e = preferences.edit();
	            e.putString("oauth_token", accessToken.getToken());
	            e.putString("oauth_token_secret", accessToken.getTokenSecret());
	            e.putBoolean("isSessionStarted", true);
	            long userID = accessToken.getUserId();
	            User user = twitter.showUser(userID);
	            String user_name = user.getName();
	            String user_username = user.getScreenName();
	            
	            e.putString("user_name", user_name);
	            e.putString("user_username", user_username);
	            e.putLong("user_id", userID);
	            e.commit();
			} catch (Exception e) {
				Log.i("Twitter Error", e.toString());
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mIntent);
			super.onPostExecute(result);
		}
	}
}
