package com.example.uniresthummingbird;

import android.app.Activity;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;

public class LoginActivity extends Activity {
	
	EditText username;
	EditText password;
	HttpRequestWithBody request;
	String token;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        request = Unirest.post("https://hummingbirdv1.p.mashape.com/users/authenticate")
  			  .header("X-Mashape-Authorization", "teZUDs9Pu1SIUs0yiUsAIvqo41mTinxt");
        
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);

    }

    
    
    public void authenticate(View view){
    	
    	new AsyncTask<Void, Void, String>(){

			@Override
			protected String doInBackground(Void... params) {
		    	
				
				System.out.println(password.getText().toString());
				System.out.println(username.getText().toString());
				
				try {
					token = request
							.field("username", username.getText().toString())
							.field("password", password.getText().toString())
							.asString().getBody().toString();
				} catch (UnirestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}
    		
			protected void onPostExecute(String result){
				System.out.println(token);
				if(token.contains("error"))
					System.out.println("Error");
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
