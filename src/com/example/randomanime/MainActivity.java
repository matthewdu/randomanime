package com.example.randomanime;

import java.io.IOException;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	String temp;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ClientResource resource = new ClientResource("http://myanimelist.net/api/anime/search.xml?q=bleach");
		ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
		ChallengeResponse authentication = new ChallengeResponse(scheme,"tabrislance", "racecar");  
		
		resource.setChallengeResponse(authentication);
		
		new AsyncTask<Void, Void, String>() {

	        @Override
	        protected String doInBackground(Void...unused) {
	    		ClientResource resource = new ClientResource("http://myanimelist.net/api/anime/search.xml?q=bleach");
	    		ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
	    		ChallengeResponse authentication = new ChallengeResponse(scheme,"tabrislance", "racecar");  
	    		
	    		resource.setChallengeResponse(authentication);
	    		
	    		try {
					return resource.get(MediaType.TEXT_XML).getText().toString();
				} catch (ResourceException e) {
					e.printStackTrace();
					return "failed";
				} catch (IOException e) {
					e.printStackTrace();
					return "failed";
				}
	        }
	            

	        @Override
	        protected void onPostExecute(String result) {
	        	temp = result;
	    		TextView text = (TextView) findViewById(R.id.hello_world);
	    		text.setText(temp);
	    		System.out.println(temp);
	    		System.out.println(result);
	        }

	    }.execute();
		

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
