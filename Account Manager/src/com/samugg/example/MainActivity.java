package com.samugg.example;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	@SuppressWarnings("unused")
	private static final String TAG = "MainActivity";
	
	private static final int REQ_SIGNUP = 1;
	
	private AccountManager mAccountManager;
	private AuthPreferences mAuthPreferences;
	private String authToken;
	
	private TextView text1;
	private TextView text2;
	private TextView text3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		text1 = (TextView) findViewById(android.R.id.text1);
		text2 = (TextView) findViewById(android.R.id.text2);
		text3 = (TextView) findViewById(R.id.text3);

		authToken = null;
		mAuthPreferences = new AuthPreferences(this);
		mAccountManager = AccountManager.get(this);
		
		// Ask for an auth token
		mAccountManager.getAuthTokenByFeatures(AccountUtils.ACCOUNT_TYPE, AccountUtils.AUTH_TOKEN_TYPE, null, this, null, null, new GetAuthTokenCallback(), null);
	}

	private class GetAuthTokenCallback implements AccountManagerCallback<Bundle> {

		@Override
		public void run(AccountManagerFuture<Bundle> result) {
			Bundle bundle;
			
			try {
				bundle = result.getResult();

				final Intent intent = (Intent) bundle.get(AccountManager.KEY_INTENT);
				if (null != intent) {
					startActivityForResult(intent, REQ_SIGNUP);
				} else {
					authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);
					final String accountName = bundle.getString(AccountManager.KEY_ACCOUNT_NAME);
					
					// Save session username & auth token
					mAuthPreferences.setAuthToken(authToken);
					mAuthPreferences.setUsername(accountName);
					
					text1.setText("Retrieved auth token: " + authToken);
					text2.setText("Saved account name: " + mAuthPreferences.getAccountName());
					text3.setText("Saved auth token: " + mAuthPreferences.getAuthToken());
					
					// If the logged account didn't exist, we need to create it on the device
					Account account = AccountUtils.getAccount(MainActivity.this, accountName);
					if (null == account) {
						account = new Account(accountName, AccountUtils.ACCOUNT_TYPE);
						mAccountManager.addAccountExplicitly(account, bundle.getString(LoginActivity.PARAM_USER_PASSWORD), null);
						mAccountManager.setAuthToken(account, AccountUtils.AUTH_TOKEN_TYPE, authToken);
					}
				}
			} catch(OperationCanceledException e) {
				// If signup was cancelled, force activity termination
				finish();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_close_session:
				
				// Clear session and ask for new auth token
				mAccountManager.invalidateAuthToken(AccountUtils.ACCOUNT_TYPE, authToken);
				mAuthPreferences.setAuthToken(null);
				mAuthPreferences.setUsername(null);
				mAccountManager.getAuthTokenByFeatures(AccountUtils.ACCOUNT_TYPE, AccountUtils.AUTH_TOKEN_TYPE, null, this, null, null, new GetAuthTokenCallback(), null);

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
}
