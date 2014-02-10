package com.example.uniresthummingbird;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;

public class LoginActivity extends Activity {
	
	private EditText username;
	private EditText password;
	private HttpRequestWithBody request;
	private String authToken;
	private SharedPreferences loginCredentials;
	public static final String PREF_NAME = "authenticationPreferenceFile"; //SharePreference file name
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        request = Unirest.post("https://hummingbirdv1.p.mashape.com/users/authenticate")
  			  .header("X-Mashape-Authorization", "teZUDs9Pu1SIUs0yiUsAIvqo41mTinxt");
        
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        loginCredentials = getSharedPreferences(PREF_NAME, 0);

    }

    
    
    public void authenticate(View view){
    	
    	new AsyncTask<Void, Void, String>(){

			@Override
			protected String doInBackground(Void... params) {
		    	
				System.out.println(username.getText().toString());
				System.out.println(password.getText().toString());

			
				try {
					authToken = request
							.field("username", username.getText().toString())
							.field("password", password.getText().toString())
							.asString().getBody().toString().replaceAll("\"", "");
				} catch (UnirestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}
    		
			protected void onPostExecute(String result){
				if(authToken.contains("error")){
					System.out.println("Error");
					Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
				}
				else{
					SharedPreferences.Editor editor = loginCredentials.edit();
					editor.putString("authToken", authToken);
					editor.commit();
					Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
				}
				
				System.out.println(loginCredentials.getString("authToken", "Error"));
			}
    	}.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
}
